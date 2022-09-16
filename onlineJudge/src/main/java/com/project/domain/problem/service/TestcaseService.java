package com.project.domain.problem.service;

import com.project.domain.problem.dto.TestcaseDto;
import com.project.domain.problem.dao.TestcaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestcaseService {
    @Autowired
    TestcaseDao testcaseDao;

    public void addTestCase(TestcaseDto testcaseDto) throws Exception {
        testcaseDao.insert(testcaseDto);
    }

    public List<TestcaseDto> getTestCase(int problem_id) throws Exception {
        return testcaseDao.selectByProblemId(problem_id);
    }
}
