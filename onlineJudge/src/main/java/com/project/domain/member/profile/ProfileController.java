package com.project.domain.member.profile;

import com.project.domain.member.MemberService;
import com.project.security.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("introduction")
    public String updateIntroduction(@RequestParam("introduction") String introduction, Authentication authentication){
        int member_id = ((PrincipalDetails)(authentication.getPrincipal())).getUser().getId();
        memberService.updateIntroductionById(introduction, member_id);
        return "redirect:/profilePage";
    }
}
