package com.project.dao;

import com.project.board.AnswerWriteData;
import com.project.board.BoardWriteData;
import com.project.dto.BoardDto;
import com.project.board.BoardListPageDto;
import com.project.util.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class BoardDao {
    private RowMapper<BoardDto> boardDtoRowMapper = new RowMapper<BoardDto>() {
        @Override
        public BoardDto mapRow(ResultSet rs, int i) throws SQLException {
            BoardDto boardDto = new BoardDto(
                    rs.getInt("board_id"),
                    rs.getInt("question_id"),
                    rs.getInt("problem_id"),
                    rs.getBoolean("question"),
                    rs.getString("content"),
                    rs.getString("date"),
                    rs.getInt("member_id")
                    );
            return boardDto;
        }
    };

    private RowMapper<BoardListPageDto> boardListPageDtoRowMapper = new RowMapper<BoardListPageDto>() {
        @Override
        public BoardListPageDto mapRow(ResultSet rs, int i) throws SQLException {
            BoardListPageDto boardListPageDto = new BoardListPageDto(
                    rs.getInt("problem_id"),
                    rs.getString("content"),
                    rs.getString("title"),
                    rs.getString("nickName"),
                    rs.getTimestamp("date").toLocalDateTime()
            );
            return boardListPageDto;
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertQuestion(BoardWriteData boardWriteData){
        jdbcTemplate.update("insert into board (problem_id, question, content, date, member_id, title) " +
                "values (?, ?, ?, ?, ?, ?)", boardWriteData.getProblem_id(),
                                         boardWriteData.getQuestion(),
                                         boardWriteData.getContent(),
                                         LocalDateTime.now(), boardWriteData.getMember_id(), boardWriteData.getTitle());
    }

    public void insetAnswer(AnswerWriteData answerWriteData){
        jdbcTemplate.update("insert into board (problem_id, question_id, question, content, date, member_id, title) " +
                        "values (?, ?, ?, ?, ?, ?)", answerWriteData.getProblem_id(),
                                                  answerWriteData.getQuestion_id(),
                                                  answerWriteData.getQuestion(),
                                                  answerWriteData.getContent(),
                                                  LocalDateTime.now(), answerWriteData.getMember_id(), answerWriteData.getTitle());
    }
    public int selectByCount(){
        return jdbcTemplate.queryForObject("select count(*) from board", Integer.class);
    }

    public List<BoardListPageDto> selectByConditionAndPaging(String conditionSql, Paging paging){
        int offset = (paging.getNowPage()-1) * paging.getPerPage();
        int limit = paging.getPerPage();
        String query = "select board.problem_id, board.content, board.title, member.name as nickName, board.date  from " +
                       "board inner join member on member.member_id = board.member_id ";
        query += conditionSql + " and question = true ";
        query += " order by board.date desc";
        query += " limit ?, ?";

        return jdbcTemplate.query(query, boardListPageDtoRowMapper, offset, limit);
    }
}
