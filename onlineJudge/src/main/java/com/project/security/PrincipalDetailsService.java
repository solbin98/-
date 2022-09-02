package com.project.security;

import com.project.dao.MemberDao;
import com.project.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login");
// login 요청이 들어오면 자동으로 User
@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername 실행됨, username : " + username);
        MemberDto memberDto = memberDao.selectByUserName(username);
        if(memberDto != null){
            User user = new User(memberDto.getMember_id(), memberDto.getUserid(), memberDto.getPassword(), "ROLE_USER");
            return new PrincipalDetails(user);
        }

        return new PrincipalDetails(null);
    }
}
