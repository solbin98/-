package com.project.dao;

import com.project.dto.BoardFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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

}
