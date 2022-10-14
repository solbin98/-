package com.project.domain.board.common.dto;

public class BoardJoinDto extends BoardDto {
    String nickName;

    public BoardJoinDto(int board_id, int question_id, int problem_id, boolean question, String content, String date, int member_id, String nickName) {
        super(board_id, question_id, problem_id, question, content, date, member_id);
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
