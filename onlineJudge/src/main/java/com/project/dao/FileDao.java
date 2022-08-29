package com.project.dao;

import com.project.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FileDao {
    private RowMapper<CategoryDto> FileDtoMapper = new RowMapper<CategoryDto>() {
        @Override
        public CategoryDto mapRow(ResultSet rs, int i) throws SQLException {
            CategoryDto categoryDto = new CategoryDto(
                    rs.getInt("category_id"),
                    rs.getString("name")
            );
            return categoryDto;
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;
}
