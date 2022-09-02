package com.project.member.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @GetMapping("/login")
    public String test(Model model){
        model.addAttribute("data","abcd");
        return "login/loginPage";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("LoginInfoData") @Validated LoginInfoData loginInfoData, BindingResult bindingResult, Model model){
        if(!loginService.validateLoginInfo(loginInfoData) && loginInfoData.getUsername() != null && !loginInfoData.getUsername().equals("")){
            bindingResult.rejectValue("username","loginFail");
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("username", loginInfoData.getUsername());
            model.addAttribute("password", loginInfoData.getPassword());
            return "login/loginPage";
        }
        return "main/mainPage";
    }
}
