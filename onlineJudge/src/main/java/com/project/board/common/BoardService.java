package com.project.board.common;

import com.project.board.answer.AnswerUpdateData;
import com.project.board.answer.AnswerWriteData;
import com.project.board.question.BoardWriteData;
import com.project.board.question.QuestionUpdateData;
import com.project.dao.BoardDao;
import com.project.dao.LikeDBDao;
import com.project.util.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardDao boardDao;
    @Autowired
    LikeDBDao  likeDBDao;

    public String getSqlConditionByKeyWord(String keyword, String type){
        String sql = " where ";
        if(keyword != null) sql += " board." + type + " like '%" + keyword + "%' and ";
        return sql;
    }

    public Integer getLastBoardId() { return boardDao.selectLastBoardId(); }

    public void deleteBoardById(int board_id) { boardDao.deleteById(board_id); }

    public void updateAnswer(AnswerUpdateData answerUpdateData)  throws Exception { boardDao.updateAnswer(answerUpdateData);}

    public void updateQuestion(QuestionUpdateData questionUpdateData) throws Exception { boardDao.updateQuestion(questionUpdateData); }

    public BoardListPageDto getBoardByBoardId(int board_id) throws Exception {
        BoardListPageDto boardListPageDto = boardDao.selectByBoardId(board_id);
        boardListPageDto.setLikeCount(likeDBDao.selectCountByBoardId(boardListPageDto.getBoard_id()));
        return boardListPageDto;
    }

    public List<BoardListPageDto> getBoardsByQuestionId(int question_id) throws Exception {
        return boardDao.selectByQuestionId(question_id);
    }

    public void addQuestion(BoardWriteData boardWriteInfoData){
        boardDao.insertQuestion(boardWriteInfoData);
    }

    public void addAnswer(AnswerWriteData answerWriteData){
       boardDao.insertAnswer(answerWriteData);
    }

    public List<BoardListPageDto> getBoardListPageDtoByConditionSqlAndPaging(String sql, Paging paging){
        List<BoardListPageDto> list = boardDao.selectByConditionAndPaging(sql, paging);
        for(int i=0;i<list.size();i++){
            list.get(i).setLikeCount(likeDBDao.selectCountByBoardId(list.get(i).getBoard_id()));
            list.get(i).setAnswerCount(boardDao.selectCountByQuestionId(list.get(i).getBoard_id()));
        }
        return list;
    }


    public int getTotalBoard(){
        return boardDao.selectCount();
    }
}
