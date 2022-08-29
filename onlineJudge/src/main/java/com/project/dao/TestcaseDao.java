package com.project.dao;

import com.project.dto.TestcaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestcaseDao {
    private RowMapper<TestcaseDto> testcaseDtoRowMapper = new RowMapper<TestcaseDto>() {
        @Override
        public TestcaseDto mapRow(ResultSet rs, int i) throws SQLException {
            TestcaseDto testcaseDto = new TestcaseDto(
                    rs.getInt("testcase_id"),
                    rs.getInt("problem_id"),
                    rs.getString("input"),
                    rs.getString("output")
            );
            return testcaseDto;
        }
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;
}
