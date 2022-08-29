package com.project.dao;

import com.project.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentDao {

    private RowMapper<CommentDto> CommentDtoMapper = new RowMapper<CommentDto>() {
        @Override
        public CommentDto mapRow(ResultSet rs, int i) throws SQLException {
            CommentDto commentDto = new CommentDto(
                    rs.getInt("comment_id"),
                    rs.getInt("parent"),
                    rs.getInt("board_id"),
                    rs.getString("content"),
                    rs.getString("date"),
                    rs.getString("member_id")
            );
            return commentDto;
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;
}
