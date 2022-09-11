package com.project.dao;

import com.project.dto.FileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FileDao {

    private RowMapper<FileDto> FileDtoMapper = new RowMapper<FileDto>() {
        @Override
        public FileDto mapRow(ResultSet rs, int i) throws SQLException {
            FileDto fileDto = new FileDto(
                    rs.getInt("file_id"),
                    rs.getString("name"),
                    rs.getString("path"),
                    rs.getString("type"),
                    rs.getString("date"),
                    rs.getString("size"),
                    rs.getBoolean("used")

            );
            return fileDto;
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(FileDto fileDto) throws Exception{
        jdbcTemplate.update("insert into file(name, path, type, date, size, used) values(?, ?, ?, ?, ?, ?)",
                fileDto.getName(),
                fileDto.getPath(),
                fileDto.getType(),
                fileDto.getDate(),
                fileDto.getSize(),
                fileDto.getUsed());
    }
}
