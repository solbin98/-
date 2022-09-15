package com.project.member.ranking;

import com.project.dao.ProfileDao;
import com.project.util.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingService {
    @Autowired
    ProfileDao profileDao;
    public List<RankingData> getRankingDataByPaging(Paging paging) throws Exception {
        return profileDao.selectByPagingAndOrderByAcNumber(paging);
    }
    public int getTotal(){
        return profileDao.selectCount();
    }
}
