package com.project.domain.member.profile;

import com.project.domain.member.ranking.RankingData;
import com.project.common.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProfileDao {
    private RowMapper<ProfileDto> profileDtoRowMapper = new RowMapper<ProfileDto>() {
        @Override
        public ProfileDto mapRow(ResultSet rs, int i) throws SQLException {
            ProfileDto profileDto = new ProfileDto(
                    rs.getInt("member_id"),
                    rs.getInt("submission_num"),
                    rs.getInt("solved_num")
            );
            return profileDto;
        }
    };

    private RowMapper<RankingData> rankingDataRowMapper = new RowMapper<RankingData>() {
        @Override
        public RankingData mapRow(ResultSet rs, int i) throws SQLException {
            RankingData rankingData = new RankingData(
                    rs.getInt("solved_num"),
                    rs.getInt("submission_num"),
                    rs.getString("name"),
                    rs.getString("introduction"));
            return rankingData;
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int selectCount(){
        return jdbcTemplate.queryForObject("select count(*) from profile", Integer.class);
    }

    public List<RankingData> selectByPagingAndOrderByAcNumber(Paging paging) throws Exception{
        int offset = (paging.getNowPage()-1) * paging.getPerPage();
        int limit = paging.getPerPage();

        String query = "select profile.solved_num, profile.submission_num, member.name, member.introduction";
        query += " from profile inner join member on member.member_id = profile.member_id order by profile.solved_num desc";
        query += " limit ?, ?";

        return jdbcTemplate.query(query, rankingDataRowMapper, offset, limit);
    }

    public void insert(ProfileDto profileDto){
        jdbcTemplate.update("insert into profile (member_id, submission_num, solved_num) values(?, ?, ?)",
                profileDto.getMember_id(),
                profileDto.getSubmission_num(),
                profileDto.getSolved_num());
    }

    public void update(ProfileDto profileDto){
        jdbcTemplate.update("update profile set submission_num = ?, solved_num = ? where member_id = ?", profileDto.getSubmission_num(), profileDto.getSolved_num(), profileDto.getMember_id());
    }

    public ProfileDto selectByMemberId(int member_id) {
        List<ProfileDto> ret = jdbcTemplate.query("select * from profile where member_id = ?", profileDtoRowMapper, member_id);
        if(ret.size() > 0) return ret.get(0);
        else return new ProfileDto(1,0,0);
    }
}
