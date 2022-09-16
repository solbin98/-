package com.project.domain.member.join;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmailAuthDto {
    String email;
    String code;
    LocalDateTime expire_date;
    boolean auth;

    public EmailAuthDto(String email, String code, LocalDateTime expire_date, boolean auth) {
        this.email = email;
        this.code = code;
        this.expire_date = expire_date;
        this.auth = auth;
    }

    public boolean getAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(LocalDateTime expire_date) {
        this.expire_date = expire_date;
    }
}
