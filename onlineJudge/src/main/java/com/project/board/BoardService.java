package com.project.board;

import com.project.dao.BoardDao;
import com.project.util.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardDao boardDao;

    public String getSqlConditionByKeyWord(String keyword){
        String sql = "";
        if(keyword != null) {
            sql += " board.keyword like %" + keyword + "%";
        }
        if(!sql.equals("")) sql = "where " + sql;
        return sql;
    }

    public Integer getLastBoardId() { return boardDao.selectLastBoardId(); }

    public void deleteBoardById(int board_id) { boardDao.deleteById(board_id); }

    public void updateAnswer(AnswerUpdateData answerUpdateData)  throws Exception { boardDao.updateAnswer(answerUpdateData);}

    public void updateQuestion(QuestionUpdateData questionUpdateData) throws Exception { boardDao.updateQuestion(questionUpdateData); }

    public BoardListPageDto getBoardByBoardId(int board_id) throws Exception { return boardDao.selectByBoardId(board_id);}

    public List<BoardListPageDto> getBoardsByQuestionId(int question_id) throws Exception { return boardDao.selectByQuestionId(question_id);}

    public void addQuestion(BoardWriteData boardWriteInfoData){
        boardDao.insertQuestion(boardWriteInfoData);
    }

    public void addAnswer(AnswerWriteData answerWriteData){
       boardDao.insertAnswer(answerWriteData);
    }

    public List<BoardListPageDto> getBoardListPageDtoByConditionSqlAndPaging(String sql, Paging paging){
        return boardDao.selectByConditionAndPaging(sql, paging);
    }

    public int getTotalBoard(){
        return boardDao.selectByCount();
    }
}
