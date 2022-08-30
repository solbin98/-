package com.project.dto;

public class ProfileFileDto {
    int member_id;
    int file_id;

    public ProfileFileDto(int member_id, int file_id) {
        this.member_id = member_id;
        this.file_id = file_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }
}
