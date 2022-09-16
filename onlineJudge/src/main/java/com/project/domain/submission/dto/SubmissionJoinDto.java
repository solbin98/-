package com.project.domain.submission.dto;

import java.time.LocalDateTime;

public class SubmissionJoinDto extends SubmissionDto {

    //이하는 외래키들의 이름을 담는 String 변수 3개
    String nickName;
    String languageName;
    String problemName;
    int testcase_num;

    public SubmissionJoinDto(int submission_id, int problem_id, int language_id, String code, String state, String memory, String time, int code_length, LocalDateTime date, int member_id, String nickName, String languageName, String problemName, int testcase_num) {
        super();
        this.submission_id = submission_id;
        this.problem_id = problem_id;
        this.language_id = language_id;
        this.code = code;
        this.state = state;
        this.memory = memory;
        this.time = time;
        this.code_length = code_length;
        this.date = date;
        this.member_id = member_id;
        this.nickName = nickName;
        this.languageName = languageName;
        this.problemName = problemName;
        this.testcase_num = testcase_num;
    }

    public int getTestcase_num() {
        return testcase_num;
    }

    public void setTestcase_num(int testcase_num) {
        this.testcase_num = testcase_num;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

}
