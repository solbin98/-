package com.project.domain.member.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @GetMapping("/login")
    public String getLoginPage(Model model){
        return "login/loginPage";
    }

    @GetMapping("/loginFail*")
    public String loginFail(String username, Model model){
         model.addAttribute("username", username);
         model.addAttribute("error", "아이디나 비밀번호가 잘못되었습니다.");
        return "login/loginPage";
    }
}
