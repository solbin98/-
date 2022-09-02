package com.project.member.join;
import com.project.member.join.JoinFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class JoinValidator implements Validator {
    @Autowired
    JoinService joinService;
    @Autowired
    MailService mailService;

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinFormData.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        JoinFormData joinFormData = (JoinFormData) target;

        // 비밀번호와 비밀번호 확인이 일치하는지 검사
        if(!joinFormData.getPassword().equals(joinFormData.getPasswordCheck())){
            errors.rejectValue("passwordCheck", "inconsistency.password.passwordCheck");
        }

        // 이메일 인증 받았는지 검사
        if(!mailService.getAuthByEmail(joinFormData.getEmail())){
            errors.rejectValue("email", "emailAuthFail");
        }

        // 아이디 중복 여부 검사
        if(joinService.checkUserIdDuplication(joinFormData.getUsername())){
            errors.rejectValue("id","idDuplication");
        }

        //이메일 중복 여부 검사
        if(joinService.checkEmailDuplication(joinFormData.getEmail())){
            errors.rejectValue("email","emailDuplication");
        }
    }
}