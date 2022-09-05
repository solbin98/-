package com.project.member;

import com.project.dao.MemberDao;
import com.project.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Service
public class MemberService {
    @Autowired
    protected MemberDao memberDao;

    public Integer getMemberIdByNickname(String nickname){
        MemberDto memberDto = memberDao.selectByUserNickName(nickname);
        if(memberDto == null) return null;
        return memberDto.getMember_id();
    }
}
