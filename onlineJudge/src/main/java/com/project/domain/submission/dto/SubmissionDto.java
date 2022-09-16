package com.project.domain.submission.dto;

import java.time.LocalDateTime;

public class SubmissionDto {
    int submission_id;
    int problem_id;
    int language_id;

    String code;
    String state;
    String memory;
    String time;
    int code_length;
    LocalDateTime date;
    int member_id;
    String username;

    public SubmissionDto() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SubmissionDto(int submission_id, int problem_id, int language_id, String code, String state, String memory, String time, int code_length, LocalDateTime date, int member_id) {
        this.code = code;
        this.submission_id = submission_id;
        this.problem_id = problem_id;
        this.language_id = language_id;
        this.state = state;
        this.memory = memory;
        this.time = time;
        this.code_length = code_length;
        this.date = date;
        this.member_id = member_id;
    }

    public int getSubmission_id() {
        return submission_id;
    }

    public void setSubmission_id(int submission_id) {
        this.submission_id = submission_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCode_length() {
        return code_length;
    }

    public void setCode_length(int code_length) {
        this.code_length = code_length;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public int getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username;}
}
