package com.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class mainController {
    @Autowired
    MessageSource messageSource;

    @GetMapping("/user")
    public String test(){
        return "main/mainPage";
    }

    @GetMapping("/test")
    public String getMainPage(){
        return "main/mainPage";
    }
}
