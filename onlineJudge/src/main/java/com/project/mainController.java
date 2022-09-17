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

    @GetMapping("/main")
    public String getMainPage(){
        return "main/mainPage";
    }

    @GetMapping("/")
    public String getDefaultPage(){
        return "main/mainPage";
    }

}
