package com.project.dao;

import com.project.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagDao {

    private RowMapper<TagDto> tagDtoRowMapper = new RowMapper<TagDto>() {
        @Override
        public TagDto mapRow(ResultSet rs, int i) throws SQLException {
            TagDto tagDto = new TagDto(
                    rs.getInt("tag_id"),
                    rs.getString("name")
            );
            return tagDto;
        }
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;
}
