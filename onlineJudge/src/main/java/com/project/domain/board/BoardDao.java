package com.project.domain.board;

import com.project.domain.board.answer.AnswerUpdateData;
import com.project.domain.board.answer.AnswerWriteData;
import com.project.domain.board.common.BoardListPageDto;
import com.project.domain.board.question.BoardWriteData;
import com.project.domain.board.question.QuestionUpdateData;
import com.project.common.Paging;
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
                    rs.getInt("member_id"),
                    rs.getInt("board_id"),
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

    public Integer selectLastBoardId(){
        return jdbcTemplate.queryForObject("SELECT last_insert_id()", Integer.class);
    }

    public BoardListPageDto selectByBoardId(int board_id) {
        List<BoardListPageDto> ret = jdbcTemplate.query("select member.member_id, board.board_id, board.problem_id, board.content, board.title, member.name as nickName, board.date  from board inner join member on member.member_id = board.member_id where board_id = ?", boardListPageDtoRowMapper, board_id);
        return ret.get(0);
    }

    public List<BoardListPageDto> selectByQuestionId(int question_id) throws Exception{
        return jdbcTemplate.query("select member.member_id, board.board_id, board.problem_id, board.content, board.title, member.name as nickName, board.date  from board inner join member on member.member_id = board.member_id where question_id = ? order by question_id asc", boardListPageDtoRowMapper, question_id);
    }

    public int selectCount(){
        return jdbcTemplate.queryForObject("select count(*) from board", Integer.class);
    }

    public int selectCountByQuestionId(int question_id){
        return jdbcTemplate.queryForObject("select count(*) from board where question_id = ?", Integer.class, question_id);
    }

    public List<BoardListPageDto> selectByConditionAndPaging(String conditionSql, Paging paging){
        int offset = (paging.getNowPage()-1) * paging.getPerPage();
        int limit = paging.getPerPage();
        String query = "select member.member_id, board.board_id, board.problem_id, board.content, board.title, member.name as nickName, board.date  from " +
                "board inner join member on member.member_id = board.member_id ";
        query += conditionSql + " question = true ";
        query += " order by board.date desc";
        query += " limit ?, ?";
        return jdbcTemplate.query(query, boardListPageDtoRowMapper, offset, limit);
    }

    public void updateAnswer(AnswerUpdateData answerUpdateData) throws Exception{
        jdbcTemplate.update("update board set content=? where board_id = ?", answerUpdateData.getContent(), answerUpdateData.getBoard_id());
    }

    public void updateQuestion(QuestionUpdateData questionUpdateData) throws Exception{
        jdbcTemplate.update("update board set problem_id = ?, content= ?, title= ? where board_id = ?",
                questionUpdateData.getProblem_id(),
                questionUpdateData.getContent(),
                questionUpdateData.getTitle(),
                questionUpdateData.getBoard_id());
    }

    public void insertQuestion(BoardWriteData boardWriteData){
        jdbcTemplate.update("insert into board (problem_id, question, content, date, member_id, title) " +
                "values (?, ?, ?, ?, ?, ?)", boardWriteData.getProblem_id(),
                                         boardWriteData.getQuestion(),
                                         boardWriteData.getContent(),
                                         LocalDateTime.now(), boardWriteData.getMember_id(), boardWriteData.getTitle());
    }

    public void insertAnswer(AnswerWriteData answerWriteData){
        jdbcTemplate.update("insert into board (question_id, question, content, date, member_id) " +
                        "values (?, ?, ?, ?, ?)",
                                                  answerWriteData.getQuestion_id(),
                                                  answerWriteData.isQuestion(),
                                                  answerWriteData.getContent(),
                                                  LocalDateTime.now(), answerWriteData.getMember_id());
    }

    public void deleteById(int board_id){
        jdbcTemplate.update("delete from board where board_id = ?", board_id);
    }
}
