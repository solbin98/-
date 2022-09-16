package com.project.domain.problem.dao;

import com.project.domain.problem.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TagDao {

    private RowMapper<TagDto> tagDtoRowMapper = new RowMapper<TagDto>() {
        @Override
        public TagDto mapRow(ResultSet rs, int i) throws SQLException {
            TagDto tagDto = new TagDto(
                    rs.getInt("tag_id"),
                    rs.getString("name")
            );
            return tagDto;
        }
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TagDto selectByName(String name) throws Exception{
        List<TagDto> ret = jdbcTemplate.query("select * from tag where name = ?", tagDtoRowMapper, name);
        if(ret.size() == 0) return null;
        else return ret.get(0);
    }

    public TagDto selectById(int tag_id){
        List<TagDto> ret = jdbcTemplate.query("select * from tag where tag_id = ?", tagDtoRowMapper, tag_id);
        if(ret.size() == 0) return null;
        else return ret.get(0);
    }

    public void insert(TagDto tagDto) throws Exception{
        jdbcTemplate.update("insert into tag(name) values(?)", tagDto.getName());
    }
}
