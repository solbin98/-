package com.project.dto;

public class LikeDBDto {
    int board_id;
    String member_id;

    public LikeDBDto(int board_id, String member_id) {
        this.board_id = board_id;
        this.member_id = member_id;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
}
