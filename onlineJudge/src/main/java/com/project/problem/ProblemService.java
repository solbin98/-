package com.project.problem;

import com.project.dao.ProblemDao;
import com.project.dto.ProblemDto;
import com.project.util.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
