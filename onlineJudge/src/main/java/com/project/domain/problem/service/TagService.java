package com.project.domain.problem.service;

import com.project.domain.problem.dto.ProblemTagDto;
import com.project.domain.problem.dto.TagDto;
import com.project.domain.problem.dao.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    TagDao tagDao;

    public List<TagDto> getTagDtoListByTagNames(List<String> tagNames) throws Exception{
        List<TagDto> ret = new ArrayList<TagDto>();
        for(int i=0;i<tagNames.size();i++) ret.add(tagDao.selectByName(tagNames.get(i)));
        return ret;
    }

    public void addTagByTagNameList(List<String> tagNameList) throws Exception {
        // 1. 이미 존재하는 태그인지를 검사하고,
        // 2. 존재하지 않는 태그인 경우에는 insert 해줌.
        for(int i=0;i<tagNameList.size();i++){
            String tagName = tagNameList.get(i);
            TagDto tagDto = tagDao.selectByName(tagName);
            if(tagDto == null) tagDao.insert(new TagDto(0, tagName));
        }
    }
}
