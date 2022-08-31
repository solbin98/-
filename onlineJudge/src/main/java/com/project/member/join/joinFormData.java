package com.project.member.join;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

public class JoinFormData {
    @NotBlank
    String id;
    @NotBlank
    String password;
    @NotBlank
    String passwordCheck;
    @NotBlank
    String nickName;
    @NotBlank
    @Email(message = "잘못된 이메일 형식입니다.")
    String email;
    String introduction;
    MultipartFile image;

    public JoinFormData(String id, String password, String passwordCheck, String nickName, String email, String introduction, MultipartFile image) {
        this.id = id;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.nickName = nickName;
        this.email = email;
        this.introduction = introduction;
        this.image = image;
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

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "JoinFormData{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", passwordCheck='" + passwordCheck + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", introduction='" + introduction + '\'' +
                ", image=" + image +
                '}';
    }
}
