package com.project.dto;

public class MemberDto {
    String member_id;
    String password;
    String name;
    String email;
    String introduction;
    String date;

    public MemberDto(String member_id, String password, String name, String email, String introduction, String date) {
        this.member_id = member_id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.introduction = introduction;
        this.date = date;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
