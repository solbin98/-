package com.project.domain.member.profile;

import com.project.domain.submission.dao.SubmissionDao;
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

    public void update(ProfileDto profileDto){
        profileDao.update(profileDto);
    }

    public void insert(ProfileDto profileDto){
        profileDao.insert(profileDto);
    }

    public Integer getSolvedProblemCountByMemberId(int member_id){
        return submissionDao.selectCountSolvedProblemByMemberId(member_id);
    }

    public Integer getSubmittedProblemCountByMemberId(int member_id){
        return submissionDao.selectCountSubmittedProblemByMemberId(member_id);
    }

    public ProfileDto getProfileDtoByMemberId(int member_id){
        return profileDao.selectByMemberId(member_id);
    }

}
