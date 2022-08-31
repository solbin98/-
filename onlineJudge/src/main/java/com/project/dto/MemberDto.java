package com.project.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberDto {
    int member_id;
    String userid;
    String password;
    String name;
    String email;
    String introduction;
    LocalDateTime date;

    public MemberDto(int member_id, String userid, String password, String name, String email, String introduction, LocalDateTime date) {
        this.member_id = member_id;
        this.userid = userid;
        this.password = password;
        this.name = name;
        this.email = email;
        this.introduction = introduction;
        this.date = date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
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
