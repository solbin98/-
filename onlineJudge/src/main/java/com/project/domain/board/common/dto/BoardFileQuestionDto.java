package com.project.domain.board.common.dto;

public class BoardFileQuestionDto {
    int board_id;
    int file_id;
    int question_id;

    public BoardFileQuestionDto(int board_id, int file_id, int question_id) {
        this.board_id = board_id;
        this.file_id = file_id;
        this.question_id = question_id;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
}
