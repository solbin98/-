package com.project.domain.submission.service;

import com.project.domain.member.MemberDao;
import com.project.domain.submission.dao.SubmissionDao;
import com.project.domain.submission.dto.SubmissionDto;
import com.project.domain.submission.dto.SubmissionJoinDto;
import com.project.common.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {
    @Autowired
    SubmissionDao submissionDao;
    @Autowired
    MemberDao memberDao;

    public List<SubmissionJoinDto> getSubmissionJoinDtoByPaging(Paging paging){
        return submissionDao.joinSelectByPaging(paging);
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

    public int getTotal(){
        return submissionDao.selectTotal();
    }

    public List<SubmissionDto> getSubmissionByQueryAndPaging(String sqlCondition, Paging paging){
        return submissionDao.selectByQueryAndPaging(sqlCondition, paging);
    }
}
