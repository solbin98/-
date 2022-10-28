package com.project.domain.problem.service;

import com.project.domain.problem.dto.*;
import com.project.domain.problem.dao.ProblemDao;
import com.project.common.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProblemService {
    @Autowired
    ProblemDao problemDao;

    @Transactional
    public int addNewProblem(ProblemDto problemDto) throws Exception{
        problemDao.insert(problemDto);
        return problemDao.selectLastId();
    }

    public int getTotal(){
        return problemDao.selectTotal();
    }

    public List<ProblemDto> getProblemsByPaging(Paging paging) throws Exception{
        return problemDao.selectByPaging(paging);
    }

    public ProblemDto getProblemById(int problem_id) throws Exception {
        return problemDao.selectById(problem_id);
    }

    public ProblemInfoData convertProblemDtoToProblemInfoData(ProblemDto problemDto,
                                                              Integer submissionNumber,
                                                              Integer acSubmissionNumber,
                                                              List<ProblemTagJoinDto> tags){
        return new ProblemInfoData(
                problemDto.getProblem_id(),
                problemDto.getTitle(),
                problemDto.getTime_limit(),
                problemDto.getMemory_limit(),
                problemDto.getContent(),
                problemDto.getInput_condition(),
                problemDto.getOutput_condition(),
                problemDto.getDifficulty(),
                submissionNumber,
                acSubmissionNumber,
                tags);
    }
}
