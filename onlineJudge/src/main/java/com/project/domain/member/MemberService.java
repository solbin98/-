package com.project.domain.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    protected MemberDao memberDao;

    public List<MemberDto> getAllMember(){
        return memberDao.select();
    }
    public Integer getMemberIdByNickname(String nickname){
        MemberDto memberDto = memberDao.selectByUserNickName(nickname);
        if(memberDto == null) return null;
        return memberDto.getMember_id();
    }

    public void updateIntroductionById(String intro, int member_id){
        memberDao.updateIntroductionById(intro, member_id);
    }
    public String getIntroductionByMemberId(int member_id){
        return memberDao.selectIntroductionByMemberId(member_id);
    }
}
