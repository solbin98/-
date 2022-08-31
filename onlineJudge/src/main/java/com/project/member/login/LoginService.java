package com.project.member.login;

import com.project.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    MemberDao memberDao;

    public Boolean validateLoginInfo(LoginInfoData loginInfoData){
        return memberDao.validateLoginInfo(loginInfoData);
    }
}
