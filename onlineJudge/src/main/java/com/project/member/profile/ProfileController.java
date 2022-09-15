package com.project.member.profile;

import com.project.dao.ProfileDao;
import com.project.dto.MemberDto;
import com.project.dto.ProfileDto;
import com.project.member.MemberService;
import com.project.security.PrincipalDetails;
import com.project.submission.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    ProfileService profileService;
    @Autowired
    MemberService memberService;

    @GetMapping("profilePage")
    public String getProfilePage(Model model, Authentication authentication){
        int member_id = ((PrincipalDetails)(authentication.getPrincipal())).getUser().getId();

        List<SolvedProblemData> solvedProblemIdList = profileService.getSolvedProblemIdListByMemberId(member_id);
        ProfileDto profileDto = profileService.getProfileDtoByMemberId(member_id);
        String introduction = memberService.getIntroductionByMemberId(member_id);

        model.addAttribute("introduction", introduction);
        model.addAttribute("problemIdList", solvedProblemIdList);
        model.addAttribute("profileDto", profileDto);
        return "profile/profilePage";
    }
}
