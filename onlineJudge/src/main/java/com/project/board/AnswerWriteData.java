package com.project.board;

public class AnswerWriteData {
    int member_id;
    int question_id;
    boolean question;
    String content;

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public boolean isQuestion() {
        return question;
    }

    public void setQuestion(boolean question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AnswerWriteData(int question_id, boolean question, String content) {
        this.question_id = question_id;
        this.question = question;
        this.content = content;
    }
}
