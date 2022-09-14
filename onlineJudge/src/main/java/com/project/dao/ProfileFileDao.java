package com.project.dao;

import com.project.dto.ProblemFileDto;
import com.project.dto.ProfileDto;
import com.project.dto.ProfileFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileFileDao {
    private RowMapper<ProfileFileDto> profileFileDtoRowMapper = new RowMapper<ProfileFileDto>() {
        @Override
        public ProfileFileDto mapRow(ResultSet rs, int i) throws SQLException {
            ProfileFileDto profileFileDto = new ProfileFileDto(
                    rs.getInt("member_id"),
                    rs.getInt("file_id")
            );
            return profileFileDto;
        }
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(ProfileFileDto profileFileDto){
        jdbcTemplate.update("insert into profile_file (member_id, file_id) values (?, ?)", profileFileDto.getMember_id(), profileFileDto.getFile_id());
    }
}
