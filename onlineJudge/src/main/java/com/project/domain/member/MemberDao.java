package com.project.domain.member;

import com.project.domain.member.login.LoginInfoData;
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

    public Integer selectLastId(){
        return jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
    }

    public List<MemberDto> select(){
        return jdbcTemplate.query("select * from member", memberDtoMapper);
    }

    public String selectUserNameByMemberId(int member_id){
        String ret = jdbcTemplate.queryForObject("select username from member where member_id = ?", String.class, member_id);
        return ret;
    }

    public String selectIntroductionByMemberId(int member_id){
        String ret = jdbcTemplate.queryForObject("select introduction from member where member_id = ?", String.class, member_id);
        return ret;
    }

    public MemberDto selectByUserUserId(String username){
        List<MemberDto> ret = jdbcTemplate.query("select * from member where userid = ?", memberDtoMapper, username);
        if(ret.size() == 0) return null;
        return ret.get(0);
    }

    public MemberDto selectByUserNickName(String nickname){
        List<MemberDto> ret = jdbcTemplate.query("select * from member where name = ?", memberDtoMapper, nickname);
        if(ret.size() == 0) return null;
        return ret.get(0);
    }

    public int countById(String id){
        try{
            Integer integer = jdbcTemplate.queryForObject("select count(*) from member where userid = ?", Integer.class, id);
            return integer;
        }
        catch (Exception e){
            return 0;
        }
    }

    public int countByNickName(String nickName){
        try{
            Integer integer = jdbcTemplate.queryForObject("select count(*) from member where nickname = ?", Integer.class, nickName);
            return integer;
        }
        catch (Exception e){
            return 0;
        }
    }

    public int countByEmail(String email){
        try{
            System.out.println("email : " + email);
            Integer integer = jdbcTemplate.queryForObject("select count(*) from member where email = ?", Integer.class, email);
            System.out.println("결과 : " + integer.toString());
            return integer;
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

    public void updateIntroductionById(String introduction, int member_id){
        jdbcTemplate.update("update member set introduction = ? where member_id = ?", introduction, member_id);
    }
}
