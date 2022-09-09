package com.project.dao;

import com.project.dto.MemberDto;
import com.project.dto.ProblemDto;
import com.project.util.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                    rs.getString("output_condition"),
                    rs.getString("difficulty"),
                    rs.getInt("testcase_num")
            );
            return problemDto;
        }
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int selectLastId(){
        Integer ret = jdbcTemplate.queryForObject("select problem_id from problem ORDER BY problem_id DESC LIMIT 1", Integer.class);
        return ret;
    }

    public int selectTotal(){
        Integer ret = jdbcTemplate.queryForObject("select count(*) from problem", Integer.class);
        return ret;
    }

    public ProblemDto selectById(int problem_id) throws Exception{
        List<ProblemDto> ret = jdbcTemplate.query("select * from problem where problem_id = ?", problemDtoRowMapper, problem_id);
        if(ret.size() == 0) throw new Exception();
        else return ret.get(0);
    }

    public List<ProblemDto> selectByPaging(Paging paging) throws Exception{
        int offset = (paging.getNowPage()-1) * paging.getPerPage();
        int limits = paging.getPerPage();

        return jdbcTemplate.query("select * from problem order by problem_id limit ?, ?",
                problemDtoRowMapper,
                offset, limits);
    }

    public void insert(ProblemDto problemDto) throws Exception{
        jdbcTemplate.update("insert into problem(title, time_limit, memory_limit, content, input_condition, output_condition, difficulty)" +
                "values(?, ?, ?, ?, ?, ?, ?)",
                problemDto.getTitle(),
                problemDto.getTime_limit(),
                problemDto.getMemory_limit(),
                problemDto.getContent(),
                problemDto.getInput_condition(),
                problemDto.getOutput_condition(),
                problemDto.getDifficulty());
    }

}
