package com.project.domain.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BoardFileDto> selectByBoardId(int board_id){
        return jdbcTemplate.query("select * from board_file where board_id = ?", boardFileDtoRowMapper, board_id);
    }

    public void insert(BoardFileDto boardFileDto){
        jdbcTemplate.update("insert into board_file (board_id, file_id) values (?, ?)", boardFileDto.getBoard_id(), boardFileDto.getFile_id());
    }

}
