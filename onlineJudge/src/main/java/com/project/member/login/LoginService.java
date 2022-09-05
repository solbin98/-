package com.project.member.login;

import com.project.dao.MemberDao;
import com.project.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService extends MemberService {

    public Boolean validateLoginInfo(LoginInfoData loginInfoData){
        return memberDao.validateLoginInfo(loginInfoData);
    }
}
