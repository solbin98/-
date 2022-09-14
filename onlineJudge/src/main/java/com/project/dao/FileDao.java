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
                    rs.getTimestamp("date").toLocalDateTime(),
                    rs.getBoolean("used")

            );
            return fileDto;
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public int selectLastFileId(){
        return jdbcTemplate.queryForObject("SELECT last_insert_id()", Integer.class);
    }

    public void insert(FileDto fileDto) throws Exception{
        jdbcTemplate.update("insert into file(name, path, type, date, used) values(?, ?, ?, ?, ?)",
                fileDto.getName(),
                fileDto.getPath(),
                fileDto.getType(),
                fileDto.getDate(),
                fileDto.getUsed());
    }

    public void setUsedColumnTrueByFileId(int file_id) throws Exception{
        jdbcTemplate.update("update file set used=true where file_id = ?", file_id);
    }

    public void setUsedColumnFalseByFileId(int file_id) throws Exception{
        jdbcTemplate.update("update file set used=false where file_id = ?", file_id);
    }
}
