package com.project.dto;

public class BoardDto {
    int board_id;
    int question_id;
    int difficulty;
    int problem_id;
    int category_id;
    boolean question;
    String content;
    String date;
    String member_id;

    public BoardDto(int board_id, int question_id, int difficulty, int problem_id, int category_id, boolean question, String content, String date, String member_id) {
        this.board_id = board_id;
        this.question_id = question_id;
        this.difficulty = difficulty;
        this.problem_id = problem_id;
        this.category_id = category_id;
        this.question = question;
        this.content = content;
        this.date = date;
        this.member_id = member_id;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    @Override
    public String toString() {
        return "boardDto{" +
                "board_id=" + board_id +
                ", question_id=" + question_id +
                ", difficulty=" + difficulty +
                ", problem_id=" + problem_id +
                ", category_id=" + category_id +
                ", question=" + question +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", member_id='" + member_id + '\'' +
                '}';
    }
}
