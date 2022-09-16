package com.project.domain.member.login;

import com.project.domain.member.MemberService;
import org.springframework.stereotype.Service;

@Service
public class LoginService extends MemberService {

    public Boolean validateLoginInfo(LoginInfoData loginInfoData){
        return memberDao.validateLoginInfo(loginInfoData);
    }
}
