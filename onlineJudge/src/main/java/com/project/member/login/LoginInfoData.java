package com.project.member.login;

import javax.validation.constraints.NotBlank;

public class LoginInfoData {
    @NotBlank
    String id;
    @NotBlank
    String password;

    public LoginInfoData(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
