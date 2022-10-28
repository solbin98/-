package com.project.domain.board.common.dao;

import com.project.domain.board.common.dto.BoardFileDto;
import com.project.domain.board.common.dto.BoardFileQuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BoardFileDao {

    private RowMapper<BoardFileDto> boardFileDtoRowMapper = new RowMapper<BoardFileDto>() {
        @Override
        public BoardFileDto mapRow(ResultSet rs, int i) throws SQLException {
            BoardFileDto boardFileDto = new BoardFileDto(
                    rs.getInt("board_id"),
                    rs.getInt("file_id")
            );
            return boardFileDto;
        }
    };

    private RowMapper<BoardFileQuestionDto> boardFileJoinDtoRowMapper = new RowMapper<BoardFileQuestionDto>() {
        @Override
        public BoardFileQuestionDto mapRow(ResultSet rs, int i) throws SQLException {
            BoardFileQuestionDto boardFileQuestionDto = new BoardFileQuestionDto(
                    rs.getInt("board_id"),
                    rs.getInt("file_id"),
                    rs.getInt("question_id")
            );
            return boardFileQuestionDto;
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BoardFileDto> selectByBoardId(int board_id){
        return jdbcTemplate.query("select * from board_file where board_id = ?", boardFileDtoRowMapper, board_id);
    }

    public void insert(BoardFileDto boardFileDto){
        jdbcTemplate.update("insert into board_file (board_id, file_id) values (?, ?)", boardFileDto.getBoard_id(), boardFileDto.getFile_id());
    }

    public List<BoardFileQuestionDto> selectByQuestionIdWithJoin(int question_id){
        String query = "select board_file.board_id, board_file.file_id, board.question_id from board_file inner join board on board_file.board_id = board.board_id where board.question_id = ?";
        return jdbcTemplate.query(query, boardFileJoinDtoRowMapper, question_id);
    }

}
