package com.project.member.join;

import com.project.dao.MemberDao;
import com.project.dto.MemberDto;
import com.project.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JoinService extends MemberService {
    public void joinMember(MemberDto memberDto){
        memberDao.insert(memberDto);
    }

    public boolean checkUserIdDuplication(String id) {
        int ret = memberDao.countById(id);
        if(ret > 0) return false;
        return true;
    }

    public boolean checkNickNameDuplication(String nickName){
        int ret = memberDao.countByNickName(nickName);
        if(ret > 0) return false;
        return true;
    }

    public boolean checkEmailDuplication(String email) {
        int ret = memberDao.countByEmail(email);
        if(ret > 0) return false;
        return true;
    }

}
