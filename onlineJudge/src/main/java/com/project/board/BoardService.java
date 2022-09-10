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

    public void addQuestion(BoardWriteData boardWriteInfoData){
        boardDao.insertQuestion(boardWriteInfoData);
    }

    public void addAnswer(AnswerWriteData answerWriteData){
       boardDao.insetAnswer(answerWriteData);
    }

    public List<BoardListPageDto> getBoardListPageDtoByConditionSqlAndPaging(String sql, Paging paging){
        return boardDao.selectByConditionAndPaging(sql, paging);
    }

    public int getTotalBoard(){
        return boardDao.selectByCount();
    }
}
