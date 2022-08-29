package com.project.dto;

public class ProfileDto {
    String member_id;
    int submission_num;
    int solved_num;

    public ProfileDto(String member_id, int submission_num, int solved_num) {
        this.member_id = member_id;
        this.submission_num = submission_num;
        this.solved_num = solved_num;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public int getSubmission_num() {
        return submission_num;
    }

    public void setSubmission_num(int submission_num) {
        this.submission_num = submission_num;
    }

    public int getSolved_num() {
        return solved_num;
    }

    public void setSolved_num(int solved_num) {
        this.solved_num = solved_num;
    }
}
