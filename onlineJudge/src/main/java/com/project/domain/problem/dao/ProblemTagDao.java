package com.project.domain.problem.dao;

import com.project.domain.problem.dto.ProblemTagDto;
import com.project.domain.problem.dto.ProblemTagJoinDto;
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

    private RowMapper<ProblemTagJoinDto> problemTagJoinDtoRowMapper = new RowMapper<ProblemTagJoinDto>() {
        @Override
        public ProblemTagJoinDto mapRow(ResultSet rs, int i) throws SQLException {
            ProblemTagJoinDto problemTagJoinDto = new ProblemTagJoinDto(
                    rs.getInt("problem_id"),
                    rs.getInt("tag_id"),
                    rs.getString("name")
            );
            return problemTagJoinDto;
        }
    };


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ProblemTagDto> selectByProblemId(int problem_id){
        return jdbcTemplate.query("select * from problem_tag where problem_id = ?", problemTagDtoRowMapper, problem_id);
    }

    public List<ProblemTagJoinDto> selectByProblemIdWithJoin(int problem_id){
        String query = "select problem_tag.tag_id, problem_tag.problem_id, tag.name from problem_tag inner join tag on tag.tag_id = problem_tag.tag_id where problem_id = ?";
        return jdbcTemplate.query(query, problemTagJoinDtoRowMapper, problem_id);
    }

    public void insert(ProblemTagDto problemTagDto){
        jdbcTemplate.update("insert into problem_tag(problem_id, tag_id) values(?, ?)",
                problemTagDto.getProblem_id(), problemTagDto.getTag_id());
    }
}
