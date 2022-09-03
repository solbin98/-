package com.project.problem;

import com.project.dao.SubmissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService {
    @Autowired
    SubmissionDao submissionDao;

    public int getSubmissionCountByProblemId(int problem_id){
        return submissionDao.selectByProblemId(problem_id);
    }

    public int getAcSubmissionCountByProblemId(int problem_id){
        return submissionDao.selectByProblemIdAndAc(problem_id);
    }
}
