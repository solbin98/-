package com.project.domain.submission.controller;

import com.project.domain.member.MemberService;
import com.project.domain.submission.dto.SubmissionDto;
import com.project.domain.submission.dto.SubmissionInfoData;
import com.project.domain.submission.dto.SubmissionJoinDto;
import com.project.domain.submission.service.LanguageService;
import com.project.domain.submission.service.SubmissionService;
import com.project.security.PrincipalDetails;
import com.project.common.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class SubmissionController {
    private final static int perPage = 10;
    @Autowired
    MemberService memberService;
    @Autowired
    SubmissionService submissionService;
    @Autowired
    LanguageService languageService;


    @GetMapping("/submission?*")
    public String getSubmissionPage(@RequestParam(value = "problem_id") String problem_id, Model model){
        model.addAttribute("problem_id", problem_id);
        model.addAttribute("languages", languageService.getLanguage());
        return "submission/submissionPage";
    }

    @GetMapping("/submissionHistory?*")
    public String getSubmissionHistoryPage(@RequestParam(value = "submission_id") int submission_id, Model model){
        model.addAttribute("code", submissionService.getCodeBySubmissionId(submission_id));
        model.addAttribute("submission_id", submission_id);
        return "submission/submissionHistoryPage";
    }

    @GetMapping("/submissionList*")
    public String getSubmissionListPage(@RequestParam(value = "page", required = false) Integer nowPage, Model model){
        if(nowPage == null) nowPage = 1;
        int total = submissionService.getTotal();
        Paging paging = new Paging(nowPage, perPage, total);
        List<SubmissionJoinDto> submissionJoinDtoList = submissionService.getSubmissionJoinDtoByPaging(paging);
        model.addAttribute("submissions", submissionJoinDtoList);
        model.addAttribute("paging", paging);
        return "submission/submissionListPage";
    }

    @PostMapping("/submission")
    @Transactional
    public String submitCode(SubmissionInfoData submissionInfoData, Authentication authentication) throws Exception {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String nickName = principalDetails.getNickname();

        int member_id = principalDetails.getUser().getId();
        int problem_id = submissionInfoData.getProblem_id();
        int language_id = submissionInfoData.getLanguage_id();
        String code = submissionInfoData.getSourceCode();
        int code_length = code.getBytes().length;

        SubmissionDto submissionDto = new SubmissionDto(0, problem_id, language_id, code, "PC","", "", code_length, LocalDateTime.now(), member_id);
        submissionService.addSubmission(submissionDto);
        return "redirect:/submissionList?page=1";
    }
}
