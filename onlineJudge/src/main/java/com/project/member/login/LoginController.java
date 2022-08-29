package com.project.member.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String test(Model model){
        model.addAttribute("data","abcd");
        return "login/loginPage";
    }
}
