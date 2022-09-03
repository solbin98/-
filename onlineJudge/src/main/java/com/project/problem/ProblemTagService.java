package com.project.problem;

import com.project.dao.ProblemTagDao;
import com.project.dto.ProblemDto;
import com.project.dto.ProblemTagDto;
import com.project.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemTagService {
    @Autowired
    ProblemTagDao problemTagDao;

    public void addProblemTagDtos(List<TagDto> problemTagDtoList, int problem_id) throws Exception{
        for(int i=0;i<problemTagDtoList.size();i++){
            problemTagDao.insert(new ProblemTagDto(problem_id, problemTagDtoList.get(i).getTag_id()));
        }
    }

    public List<ProblemTagDto> getProblemTagByProblemId(int problem_id){
        return problemTagDao.selectByProblemId(problem_id);
    }
}
