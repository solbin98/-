package com.project.domain.submission.dto;

public class LanguageDto {
    int language_id;
    String name;

    public LanguageDto(int language_id, String name) {
        this.language_id = language_id;
        this.name = name;
    }

    public int getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
