package com.project.domain.submission.dao;

import com.project.domain.member.profile.SolvedProblemData;
import com.project.domain.submission.dto.SubmissionDto;
import com.project.domain.submission.dto.SubmissionJoinDto;
import com.project.common.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SubmissionDao {

    private RowMapper<SubmissionDto> submissionDtoRowMapper = new RowMapper<SubmissionDto>() {
        @Override
        public SubmissionDto mapRow(ResultSet rs, int i) throws SQLException {
            SubmissionDto submissionDto = new SubmissionDto(
                    rs.getInt("submission_id"),
                    rs.getInt("problem_id"),
                    rs.getInt("language_id"),
                    rs.getString("code"),
                    rs.getString("state"),
                    rs.getString("memory"),
                    rs.getString("time"),
                    rs.getInt("code_length"),
                    rs.getTimestamp("date").toLocalDateTime(),
                    rs.getInt("member_id")
            );
            return submissionDto;
        }
    };

    private RowMapper<SubmissionJoinDto> submissionJoinDtoRowMapper = new RowMapper<SubmissionJoinDto>() {
        @Override
        public SubmissionJoinDto mapRow(ResultSet rs, int i) throws SQLException {
            SubmissionJoinDto submissionJoinDto = new SubmissionJoinDto(
                    rs.getInt("submission_id"),
                    rs.getInt("problem_id"),
                    rs.getInt("language_id"),
                    rs.getString("code"),
                    rs.getString("state"),
                    rs.getString("memory"),
                    rs.getString("time"),
                    rs.getInt("code_length"),
                    rs.getTimestamp("date").toLocalDateTime(),
                    rs.getInt("member_id"),
                    rs.getString("nickName"),
                    rs.getString("languageName"),
                    rs.getString("problemName"),
                    rs.getInt("testcase_num")
            );
            return submissionJoinDto;
        }
    };

    private RowMapper<SolvedProblemData> solvedProblemDataRowMapper = new RowMapper<SolvedProblemData>() {
        @Override
        public SolvedProblemData mapRow(ResultSet rs, int i) throws SQLException {
            return new SolvedProblemData(rs.getInt("problem_id"), rs.getString("name"));
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String selectCodeBySubmissionId(int submission_id){
        return jdbcTemplate.queryForObject("select code from submission where submission_id = ?", String.class, submission_id);
    }

    public Integer selectByProblemId(int problem_id) {
        Integer ret = jdbcTemplate.queryForObject("select count(*) from submission where problem_id = ?",
                Integer.class, problem_id);
        return ret;
    }

    public Integer selectByProblemIdAndAc(int problem_id) {
        String state = "AC";
        Integer ret = jdbcTemplate.queryForObject("select count(*) from submission where problem_id = ? and state = ?",
                Integer.class, problem_id, state);
        return ret;
    }

    public Integer selectTotalByQuery(String conditionSql){
        Integer ret = jdbcTemplate.queryForObject("select count(*) from submission " + conditionSql, Integer.class);
        return ret;
    }

    public List<SubmissionDto> selectByQueryAndPaging(String conditionSql, Paging paging){
        int offset = (paging.getNowPage()-1) * paging.getPerPage();
        int limits = paging.getPerPage();
        String query = "select * from submission " + conditionSql + " limit ?, ?";
        return jdbcTemplate.query(query, submissionDtoRowMapper, offset, limits);
    }

    public List<SubmissionJoinDto> joinSelectByQueryAndPaging(String conditionSql, Paging paging){
        int offset = (paging.getNowPage()-1) * paging.getPerPage();
        int limits = paging.getPerPage();

        String query = "select submission_id, code, state, memory, time, code_length, submission.date, submission.member_id, submission.problem_id, " +
                "submission.language_id, member.name as nickName, problem.title as problemName, " +
                "language.name as languageName, problem.testcase_num from submission inner join member on " +
                "submission.member_id = member.member_id inner join problem on submission.problem_id = problem.problem_id " +
                "inner join language on submission.language_id = language.language_id " + conditionSql + " order by submission_id desc limit ?, ? ";

        return jdbcTemplate.query(query, submissionJoinDtoRowMapper, offset, limits);
    }

    public void insert(SubmissionDto submissionDto) throws Exception{
        jdbcTemplate.update("insert into submission(problem_id, language_id, code, state, memory, time, code_length, date, member_id) values(?,?,?,?,?,?,?,?,?)",
                submissionDto.getProblem_id(),
                submissionDto.getLanguage_id(),
                submissionDto.getCode(),
                submissionDto.getState(),
                submissionDto.getMemory(),
                submissionDto.getTime(),
                submissionDto.getCode_length(),
                submissionDto.getDate(),
                submissionDto.getMember_id());
    }

    public List<SolvedProblemData> selectSolvedProblemIdListByMemberId(int member_id) {
        List<SolvedProblemData> ret = jdbcTemplate.query("select submission.problem_id, problem.title as name from submission inner join problem on problem.problem_id = submission.problem_id where submission.member_id = ? and submission.state='AC' group by submission.problem_id", solvedProblemDataRowMapper, member_id);
        return ret;
    }

    public Integer selectCountSolvedProblemByMemberId(int member_id) {
        return jdbcTemplate.queryForObject("select count(pid) from (select submission.problem_id as pid from submission inner join problem on problem.problem_id = submission.problem_id where submission.member_id = ? and submission.state='AC' group by submission.problem_id) as p;", Integer.class, member_id);
    }

    public Integer selectCountSubmittedProblemByMemberId(int member_id) {
        return jdbcTemplate.queryForObject("select count(pid) from (select submission.problem_id as pid from submission inner join problem on problem.problem_id = submission.problem_id where submission.member_id = ? group by submission.problem_id) as p;", Integer.class, member_id);
    }
}

