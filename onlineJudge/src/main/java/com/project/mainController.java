package com.project;

import com.project.security.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
public class mainController {
    @Autowired
    MessageSource messageSource;

    @GetMapping("/submit")
    public String test(Authentication authentication, HttpSession session){
        String abc = (String)session.getAttribute("username");
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        return "main/mainPage";
    }

    @GetMapping("/main")
    public String getMainPage(){
        return "main/mainPage";
    }

    @GetMapping("/test")
    public String getTest(){
        return "main/mainPage";
    }

    @GetMapping("/access-denied")
    public String getAccessDeniedPage(){
        System.out.println();
        return "login/loginPage";
    }
}
