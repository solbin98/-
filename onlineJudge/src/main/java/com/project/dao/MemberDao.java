package com.project.dao;

import com.project.dto.MemberDto;
import com.project.member.login.LoginInfoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberDao {

    private RowMapper<MemberDto> memberDtoMapper = new RowMapper<MemberDto>() {
        @Override
        public MemberDto mapRow(ResultSet rs, int i) throws SQLException {
            MemberDto memberDto = new MemberDto(
                    rs.getInt("member_id"),
                    rs.getString("userid"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("introduction"),
                    rs.getString("role"),
                    rs.getTimestamp("date").toLocalDateTime()
            );
            return memberDto;
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Boolean validateLoginInfo(LoginInfoData loginInfoData){
        try{
            List<MemberDto> ret = jdbcTemplate.query("select * from member where userid = ? and password = ?",
                    memberDtoMapper,
                    loginInfoData.getUsername(),
                    loginInfoData.getPassword());

            if(ret.size() == 0) return false;
            return true;
        }
        catch (Exception e){
            throw e;
        }
    }

    public MemberDto selectByUserName(String username){
        List<MemberDto> ret = jdbcTemplate.query("select * from member where userid = ?", memberDtoMapper, username);
        if(ret.size() == 0) return null;
        return ret.get(0);
    }

    public int countById(String id){
        try{
            Integer integer = jdbcTemplate.queryForObject("select count(*) from member where userid = ?", Integer.class, id);
            return 1;
        }
        catch (Exception e){
            return 0;
        }
    }

    public int countByEmail(String email){
        try{
            Integer integer = jdbcTemplate.queryForObject("select count(*) from member where email = ?", Integer.class, email);
            return 1;
        }
        catch (Exception e){
            return 0;
        }
    }

    public void insert(MemberDto memberDto){
        try{
            jdbcTemplate.update(
                    "insert into member(userid, password, name, email, introduction, role, date) values(?, ?, ?, ?, ?, ?, ? )",
                    memberDto.getUserid(),
                    memberDto.getPassword(),
                    memberDto.getName(),
                    memberDto.getEmail(),
                    memberDto.getIntroduction(),
                    memberDto.getRole(),
                    memberDto.getDate());
        }
        catch (Exception e){
            throw e;
        }
    }
}
