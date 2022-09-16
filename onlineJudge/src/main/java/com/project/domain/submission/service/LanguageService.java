package com.project.domain.submission.service;

import com.project.domain.submission.dao.LanguageDao;
import com.project.domain.submission.dto.LanguageDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LanguageService {
    @Autowired
    LanguageDao languageDao;

    public List<LanguageDto> getLanguage(){
        return languageDao.select();
    }
}
