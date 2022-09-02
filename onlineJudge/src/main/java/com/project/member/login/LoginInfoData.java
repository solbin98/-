package com.project.member.login;

import javax.validation.constraints.NotBlank;

public class LoginInfoData {
    @NotBlank
    String username;
    @NotBlank
    String password;

    public LoginInfoData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
