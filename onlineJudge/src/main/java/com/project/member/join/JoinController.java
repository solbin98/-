package com.project.member.join;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {
    @GetMapping("/join")
    public String joinPage(){
        return "join/joinPage";
    }

    @PostMapping("/join")
    public String join(){
        return "main/mainPage";
    }
}
