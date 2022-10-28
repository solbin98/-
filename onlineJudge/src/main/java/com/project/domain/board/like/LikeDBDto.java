package com.project.domain.board.like;

public class LikeDBDto {
    int board_id;
    int member_id;

    public LikeDBDto(int board_id, int member_id) {
        this.board_id = board_id;
        this.member_id = member_id;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }
}
