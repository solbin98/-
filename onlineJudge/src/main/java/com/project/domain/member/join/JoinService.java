package com.project.domain.member.join;

import com.project.domain.member.MemberService;
import com.project.domain.member.MemberDto;
import org.springframework.stereotype.Service;

@Service
public class JoinService extends MemberService {
    public void joinMember(MemberDto memberDto){
        memberDao.insert(memberDto);
    }

    public Integer getLastMemberId(){
        return memberDao.selectLastId();
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
