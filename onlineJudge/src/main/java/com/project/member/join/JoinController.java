package com.project.member.join;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class JoinController {
    @Autowired
    MailService mailService;

    @GetMapping("/join")
    public String joinPage(){
        return "join/joinPage";
    }

    @PostMapping("/email")
    @ResponseBody
    public String sendEmail(@RequestParam String email){
        System.out.println(email + " 도착함.");
        String authKey = mailService.sendAuthMail(email);
        return authKey;
    }

    @PostMapping(value ="/emailCertification")
    @ResponseBody
    public Map<String,Object> certificateEmail(@RequestParam("email") String email,
                                               @RequestParam("code") String code){
        boolean res = mailService.checkAuthCode(email, code);
        String message = "";
        boolean result = true;

        if(res == true) message="인증에 성공하였습니다.";
        else {
            message="인증에 실패하였습니다.";
            result = false;
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", message);
        resultMap.put("result", result);
        return resultMap;
    }

    @GetMapping("/emailCertificationPage*")
    public String getEmailCertificatePage(@RequestParam("email") String email, Model model){
        model.addAttribute("email", email);
        return "join/emailCertificationPage";
    }

}
