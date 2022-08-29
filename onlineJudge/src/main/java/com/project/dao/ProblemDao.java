package com.project.dao;

import com.project.dto.ProblemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProblemDao {

    private RowMapper<ProblemDto> problemDtoRowMapper = new RowMapper<ProblemDto>() {
        @Override
        public ProblemDto mapRow(ResultSet rs, int i) throws SQLException {
            ProblemDto problemDto = new ProblemDto(
                    rs.getInt("problem_id"),
                    rs.getString("title"),
                    rs.getString("time_limit"),
                    rs.getString("memory_limit"),
                    rs.getString("content"),
                    rs.getString("input_condition"),
                    rs.getString("output_condition")
            );
            return problemDto;
        }
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;
}
