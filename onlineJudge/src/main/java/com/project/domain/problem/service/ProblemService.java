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

    public List<ProblemInfoData> getProblemsInfoDataListByProblemDtoList(
            List<ProblemDto> problemDtoList,
            Map<Integer, List<ProblemTagJoinDto>> tagsList,
            Map<Integer, String> submissionNumberMap,
            Map<Integer, String> acSubmissionNumberMap
                                                                ){
        List<ProblemInfoData> ret = new ArrayList<>();
        for(int i=0;i<problemDtoList.size();i++){
            ProblemDto problemDto = problemDtoList.get(i);
            int pid = problemDto.getProblem_id();
            String submissionNumber = submissionNumberMap.get(pid);
            String acSubmissionNumber = acSubmissionNumberMap.get(pid);
            List<ProblemTagJoinDto> tags = tagsList.get(pid);
            ret.add(convertProblemDtoToProblemInfoData(problemDto, submissionNumber, acSubmissionNumber, tags));
        }
        return ret;
    }

    public ProblemInfoData convertProblemDtoToProblemInfoData(ProblemDto problemDto,
                                                              String submissionNumber,
                                                              String acSubmissionNumber,
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
