package com.project.domain.problem.dao;

import com.project.domain.problem.dto.ProblemTagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProblemTagDao {

    private RowMapper<ProblemTagDto> problemTagDtoRowMapper = new RowMapper<ProblemTagDto>() {
        @Override
        public ProblemTagDto mapRow(ResultSet rs, int i) throws SQLException {
            ProblemTagDto problemTagDto = new ProblemTagDto(
                    rs.getInt("problem_id"),
                    rs.getInt("tag_id")
            );
            return problemTagDto;
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ProblemTagDto> selectByProblemId(int problem_id){
        return jdbcTemplate.query("select * from problem_tag where problem_id = ?", problemTagDtoRowMapper, problem_id);
    }

    public void insert(ProblemTagDto problemTagDto){
        jdbcTemplate.update("insert into problem_tag(problem_id, tag_id) values(?, ?)",
                problemTagDto.getProblem_id(), problemTagDto.getTag_id());
    }
}
