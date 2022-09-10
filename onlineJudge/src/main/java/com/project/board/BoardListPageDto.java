package com.project.board;

import java.time.LocalDateTime;

public class BoardListPageDto{
    int problem_id;
    String content;
    String title;
    String nickName;
    LocalDateTime date;
    int likeCount;
    int answerCount;

    public BoardListPageDto(int problem_id, String content, String title, String nickName, LocalDateTime date) {
        this.problem_id = problem_id;
        this.content = content;
        this.title = title;
        this.nickName = nickName;
        this.date = date;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    @Override
    public String toString() {
        return "BoardListPageDto{" +
                "problem_id=" + problem_id +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", nickName='" + nickName + '\'' +
                ", date=" + date +
                ", likeCount=" + likeCount +
                ", answerCount=" + answerCount +
                '}';
    }
}
