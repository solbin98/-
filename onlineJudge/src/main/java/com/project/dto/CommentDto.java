package com.project.dto;

public class CommentDto {
    int comment_id;
    int parent;
    int board_id;
    String content;
    String date;
    String member_id;

    public CommentDto(int comment_id, int parent, int board_id, String content, String date, String member_id) {
        this.comment_id = comment_id;
        this.parent = parent;
        this.board_id = board_id;
        this.content = content;
        this.date = date;
        this.member_id = member_id;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
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
}
