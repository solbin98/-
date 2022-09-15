package com.project.board.common;

public class BoardDeleteData {
    int member_id;
    int board_id;
    int question_id;

    @Override
    public String toString() {
        return "BoardDeleteData{" +
                "member_id=" + member_id +
                ", board_id=" + board_id +
                ", question_id=" + question_id +
                '}';
    }

    public BoardDeleteData(int member_id, int board_id, int question_id) {
        this.member_id = member_id;
        this.board_id = board_id;
        this.question_id = question_id;
    }

    public int getMember_id() {
        return member_id;
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

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
}
