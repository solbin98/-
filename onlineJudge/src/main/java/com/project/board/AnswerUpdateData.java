package com.project.board;

import java.util.List;

public class AnswerUpdateData {
    int question_id;
    int member_id;
    int board_id;
    String content;
    List<Integer> images;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public AnswerUpdateData(int question_id, int member_id, int board_id, String content, List<Integer> images) {
        this.question_id = question_id;
        this.member_id = member_id;
        this.board_id = board_id;
        this.content = content;
        this.images = images;
    }

    public int getMember_id() {
        return member_id;
    }

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AnswerUpdateData{" +
                "question_id=" + question_id +
                ", member_id=" + member_id +
                ", board_id=" + board_id +
                ", content='" + content + '\'' +
                ", images=" + images +
                '}';
    }
}
