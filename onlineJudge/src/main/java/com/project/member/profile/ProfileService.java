package com.project.member.profile;

import com.project.dao.ProfileDao;
import com.project.dao.SubmissionDao;
import com.project.dto.ProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    @Autowired
    ProfileDao profileDao;
    @Autowired
    SubmissionDao submissionDao;

    public List<SolvedProblemData> getSolvedProblemIdListByMemberId(int member_id){
        return submissionDao.selectSolvedProblemIdListByMemberId(member_id);
    }

    public ProfileDto getProfileDtoByMemberId(int member_id){
        return profileDao.selectByMemberId(member_id);
    }

}
