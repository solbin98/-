package com.project.dao;

import com.project.dto.ProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileDao {
    private RowMapper<ProfileDto> profileDtoRowMapper = new RowMapper<ProfileDto>() {
        @Override
        public ProfileDto mapRow(ResultSet rs, int i) throws SQLException {
            ProfileDto profileDto = new ProfileDto(
                    rs.getInt("member_id"),
                    rs.getInt("submission_id"),
                    rs.getInt("solved_num")
            );
            return profileDto;
        }
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;
}
