package com.project.submission;

import com.project.dao.MemberDao;
import com.project.dao.SubmissionDao;
import com.project.dto.SubmissionDto;
import com.project.dto.SubmissionJoinDto;
import com.project.util.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {
    @Autowired
    SubmissionDao submissionDao;
    @Autowired
    MemberDao memberDao;

    public String createSqlConditionForSubmissionSelectQuery(Integer problem_id, Integer member_id){
        String sql = "";
        if(problem_id != null) sql += "submission.problem_id = " + problem_id;
        if(member_id != null) {
            if(!sql.equals("")) sql += " and ";
            sql += "submission.member_id = " + member_id;
        }
        // 결과 예시
        // 1) problem_id 와 username이 null 이 아닌 경우
        //   -> "where problem_id = 2 and username = solbin" 출력
        if(!sql.equals("")) sql = "where " + sql;
        return sql;
    }

    public List<SubmissionJoinDto> getSubmissionJoinDtoByQueryAndPaging(String conditionSql, Paging paging){
        return submissionDao.joinSelectByQueryAndPaging(conditionSql, paging);
    }

    public void addSubmission(SubmissionDto submissionDto) throws Exception{
        submissionDao.insert(submissionDto);
    }
    public int getSubmissionCountByProblemId(int problem_id){
        return submissionDao.selectByProblemId(problem_id);
    }

    public int getAcSubmissionCountByProblemId(int problem_id){
        return submissionDao.selectByProblemIdAndAc(problem_id);
    }

    public String getCodeBySubmissionId(int submission_id){
        return submissionDao.selectCodeBySubmissionId(submission_id);
    }

    public void setSubmissionDtoListUsername(List<SubmissionDto> submissionDtoList) {
        for(int i=0;i<submissionDtoList.size();i++){
            String username = memberDao.selectUserNameByMemberId(submissionDtoList.get(i).getMember_id());
            submissionDtoList.get(i).setUsername(username);
        }
    }

    public int getTotalByQuery(String sqlCondition){
        return submissionDao.selectTotalByQuery(sqlCondition);
    }

    public List<SubmissionDto> getSubmissionByQueryAndPaging(String sqlCondition, Paging paging){
        return submissionDao.selectByQueryAndPaging(sqlCondition, paging);
    }
}
