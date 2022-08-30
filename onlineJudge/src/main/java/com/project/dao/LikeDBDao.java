package com.project.dao;

import com.project.dto.LikeDBDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeDBDao {
    private RowMapper<LikeDBDto> categoryDtoMapper = new RowMapper<LikeDBDto>() {
        @Override
        public LikeDBDto mapRow(ResultSet rs, int i) throws SQLException {
            LikeDBDto likeDBDto = new LikeDBDto(
                    rs.getInt("board_id"),
                    rs.getInt("member_id")
            );
            return likeDBDto;
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;
}
