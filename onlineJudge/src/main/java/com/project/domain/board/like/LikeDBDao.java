package com.project.domain.board.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class LikeDBDao {
    private RowMapper<LikeDBDto> likeDBDtoRowMapper = new RowMapper<LikeDBDto>() {
        @Override
        public LikeDBDto mapRow(ResultSet rs, int i) throws SQLException {
            LikeDBDto likeDBDto = new LikeDBDto(
                    rs.getInt("board_id"),
                    rs.getInt("member_id")
            );
            return likeDBDto;
        }
    };

    @Autowired
    DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int selectCountByBoardIdAndMemberId(int board_id, int member_id) {
        return jdbcTemplate.queryForObject("select count(*) from likeDB where board_id = ? and member_id = ?", Integer.class, board_id ,member_id);
    }

    public int selectCountByBoardId(int board_id){
        return jdbcTemplate.queryForObject("select count(*) from likeDB where board_id = ?", Integer.class, board_id);
    }

    public void insert(LikeDBDto likeDBDto){
        jdbcTemplate.update("insert into likeDB (board_id, member_id) values (?, ?)", likeDBDto.getBoard_id(), likeDBDto.getMember_id());
    }

    public void deleteByBoardIdAndMemberId(int board_id, int member_id){
        jdbcTemplate.update("delete from likeDB where board_id = ? and member_id = ?", board_id, member_id);
    }


}
