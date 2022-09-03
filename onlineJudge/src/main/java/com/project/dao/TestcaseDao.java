package com.project.dao;

import com.project.dto.TestcaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public List<TestcaseDto> selectByProblemId(int problem_id) throws Exception{
        return jdbcTemplate.query("select * from testcase where problem_id = ?", testcaseDtoRowMapper, problem_id);
    }

    public void insert(TestcaseDto testcaseDto) throws Exception{
        jdbcTemplate.update("insert into testcase(problem_id, input, output) values (?, ?, ?)",
                testcaseDto.getProblem_id(),
                testcaseDto.getInput(),
                testcaseDto.getOutput()
                );
    }

}
