package com.project.dao;

import com.project.dto.SubmissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubmissionDao {

    private RowMapper<SubmissionDto> submissionDtoRowMapper = new RowMapper<SubmissionDto>() {
        @Override
        public SubmissionDto mapRow(ResultSet rs, int i) throws SQLException {
            SubmissionDto submissionDto = new SubmissionDto(
                    rs.getInt("submission_id"),
                    rs.getInt("problem_id"),
                    rs.getInt("language_id"),
                    rs.getString("state"),
                    rs.getString("memory"),
                    rs.getString("time"),
                    rs.getString("code_length"),
                    rs.getString("date"),
                    rs.getString("member_id")
            );
            return submissionDto;
        }
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;
}
