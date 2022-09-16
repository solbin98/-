package com.project.domain.problem.dao;

import com.project.domain.problem.dto.ProblemFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProblemFileDao {

    private RowMapper<ProblemFileDto> problemFileDtoRowMapper = new RowMapper<ProblemFileDto>() {
        @Override
        public ProblemFileDto mapRow(ResultSet rs, int i) throws SQLException {
            ProblemFileDto problemFileDto = new ProblemFileDto(
                    rs.getInt("problem_id"),
                    rs.getInt("file_id")
            );
            return problemFileDto;
        }
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(ProblemFileDto problemFileDto){
        jdbcTemplate.update("insert into problem_file (problem_id, file_id ) values (?, ?)",
                problemFileDto.getProblem_id(), problemFileDto.getFile_id());
    }
}
