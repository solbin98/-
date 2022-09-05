package com.project.member.join;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.project.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class JoinController {
    @Autowired
    JoinService joinService;
    @Autowired
    MailService mailService;
    @Autowired
    JoinValidator joinValidator;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/join")
    public String getJoinPage(){
        return "join/joinPage";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("JoinFormData") JoinFormData joinFormData, BindingResult bindingResult, Model model){
        // joinValidator 을 통한 회원 가입 폼 데이터 검증
        joinValidator.validate(joinFormData, bindingResult);

        if(bindingResult.hasErrors()) {
            setModelObjectFromJoinFormData(model, joinFormData);
            return "join/joinPage";
        }

        joinService.joinMember(convertJoinFormDataToMemberDto(joinFormData));
        return "login/loginPage";
    }

    @PostMapping("/email")
    @ResponseBody
    public Map<String, Object> sendEmail(@RequestParam String email){
        Map<String, Object> resultMap = mailService.sendAuthMail(email);
        return resultMap;
    }

    @PostMapping(value ="/emailCertification")
    @ResponseBody
    public Map<String,Object> certificateEmail(@RequestParam("email") String email,
                                               @RequestParam("code") String code) throws Exception {
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

    public MemberDto convertJoinFormDataToMemberDto(JoinFormData joinFormData){
        return new MemberDto(0,
                joinFormData.getUsername(),
                joinFormData.getPassword(),
                joinFormData.getNickName(),
                joinFormData.getEmail(),
                joinFormData.getIntroduction(),
                "ROLE_USER",
                LocalDateTime.now());
    }

    public void setModelObjectFromJoinFormData(Model model, JoinFormData joinFormData){
        model.addAttribute("username", joinFormData.getUsername());
        model.addAttribute("password", joinFormData.getPassword());
        model.addAttribute("passwordCheck", joinFormData.getPasswordCheck());
        model.addAttribute("nickName", joinFormData.getNickName());
        model.addAttribute("introduction", joinFormData.getIntroduction());
    }

}
