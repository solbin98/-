package com.project.domain.member.ranking;

public class RankingData {
    int submission_num;
    int solved_num;
    String nickname;
    String introduction;

    public RankingData(int solved_num, int submission_num, String nickname, String introduction) {
        this.solved_num = solved_num;
        this.submission_num = submission_num;
        this.nickname = nickname;
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "RankingData{" +
                ", acSubmissionNumber=" + solved_num +
                ", submissionNumber=" + submission_num +
                ", nickname='" + nickname + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }

    public int getAcSubmissionNumber() {
        return solved_num;
    }

    public void setAcSubmissionNumber(int acSubmissionNumber) {
        this.solved_num = acSubmissionNumber;
    }

    public int getSubmissionNumber() {
        return submission_num;
    }

    public void setSubmissionNumber(int submissionNumber) {
        this.submission_num = submissionNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
