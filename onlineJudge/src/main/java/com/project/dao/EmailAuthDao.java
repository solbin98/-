package com.project.dao;

import com.project.dto.CommentDto;
import com.project.dto.EmailAuthDto;
import org.aopalliance.intercept.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmailAuthDao {
    private RowMapper<EmailAuthDto> emailAuthDtoRowMapper = new RowMapper<EmailAuthDto>() {
        @Override
        public EmailAuthDto mapRow(ResultSet rs, int i) throws SQLException {
            EmailAuthDto emailAuthDto = new EmailAuthDto(
                    rs.getString("email"),
                    rs.getString("code"),
                    rs.getTimestamp("expire_date").toLocalDateTime(),
                    rs.getBoolean("auth")
            );
            return emailAuthDto;
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public EmailAuthDto selectByEmail(String email){
        List<EmailAuthDto> res = jdbcTemplate.query("select * from email_auth where email = ?", emailAuthDtoRowMapper, email);
        if(res.size() == 0) return null;
        return res.get(0);
    }

    public EmailAuthDto selectByEmailAndCode(String email, String code){
        List<EmailAuthDto> res = jdbcTemplate.query("select * from email_auth where email = ? and code = ?", emailAuthDtoRowMapper, email, code);
        if(res.size() == 0) return null;
        return res.get(0);
    }

    public boolean selectAuthByEmail(String email){
        try{
            boolean ret = jdbcTemplate.queryForObject("select auth from email_auth where email = ?", Boolean.class, email);
            return ret;
        }
        catch (Exception e){
            return false;
        }
    }

    public void insert(EmailAuthDto emailAuthDto) throws Exception{
        jdbcTemplate.update("insert into email_auth(email, code, expire_date, auth) values(?, ?, ?, ? )",
                emailAuthDto.getEmail(), emailAuthDto.getCode(), emailAuthDto.getExpire_date(), emailAuthDto.getAuth());
    }

    public void update(EmailAuthDto emailAuthDto) throws Exception{
        jdbcTemplate.update("update email_auth set code=?, expire_date=?, auth=? where email=?",
                    emailAuthDto.getCode(), emailAuthDto.getExpire_date(), emailAuthDto.getAuth(),emailAuthDto.getEmail());
    }

}
