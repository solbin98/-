package com.project.dao;

import com.project.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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
                    rs.getString("date")
            );
            return memberDto;
        }
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;
}
