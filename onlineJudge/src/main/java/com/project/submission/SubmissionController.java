package com.project.submission;

import com.project.security.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SubmissionController {

    @GetMapping("/submissionPage*")
    public String getSubmissionPage(@RequestParam(value = "problem_id", required = false) String problem_id, Model model){
        model.addAttribute("problem_id", problem_id);
        return "submission/submissionPage";
    }

    @PostMapping("/submission")
    public String submitCode(SubmissionInfoData submissionInfoData, Authentication authentication){
        System.out.println("도착한 데이터 : " + submissionInfoData);
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetails.getUsername();
        return "submission/submissionPage";
    }
}
