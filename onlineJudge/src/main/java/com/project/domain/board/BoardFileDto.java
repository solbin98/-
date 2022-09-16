package com.project.domain.board;

public class BoardFileDto {
    int board_id;
    int file_id;

    public BoardFileDto(int board_id, int file_id) {
        this.board_id = board_id;
        this.file_id = file_id;
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

    @Override
    public String toString() {
        return "boardFileDto{" +
                "board_id=" + board_id +
                ", file_id=" + file_id +
                '}';
    }
}
