package com.project.submission;

import com.project.dao.LanguageDao;
import com.project.dto.LanguageDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LanguageService {
    @Autowired
    LanguageDao languageDao;

    public List<LanguageDto> getLanguage(){
        return languageDao.select();
    }
}
