package com.project.dao;

import com.project.dto.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardDao {
    private RowMapper<BoardDto> boardDtoRowMapper = new RowMapper<BoardDto>() {
        @Override
        public BoardDto mapRow(ResultSet rs, int i) throws SQLException {
            BoardDto boardDto = new BoardDto(
                    rs.getInt("board_id"),
                    rs.getInt("question_id"),
                    rs.getInt("difficulty"),
                    rs.getInt("problem_id"),
                    rs.getInt("category_id"),
                    rs.getBoolean("question"),
                    rs.getString("content"),
                    rs.getString("date"),
                    rs.getString("member_id")
                    );
            return boardDto;
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;


}
