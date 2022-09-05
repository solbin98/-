package com.project.submission;

import com.project.dto.SubmissionDto;
import com.project.dto.SubmissionJoinDto;
import com.project.member.MemberService;
import com.project.security.PrincipalDetails;
import com.project.util.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
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


    @GetMapping("/submissionPage*")
    public String getSubmissionPage(@RequestParam(value = "problem_id", required = false) String problem_id, Model model){
        model.addAttribute("problem_id", problem_id);
        model.addAttribute("languages", languageService.getLanguage());
        return "submission/submissionPage";
    }

    @GetMapping("/submissionList*")
    public String getSubmissionListPage(@RequestParam(value = "page", required = false) Integer nowPage,
                                        @RequestParam(value = "nickname", required = false) String nickname,
                                        @RequestParam(value = "problem_id", required = false) Integer problem_id,
                                        Model model){

        if(nowPage == null) nowPage = 1;
        Integer member_id = memberService.getMemberIdByNickname(nickname);
        String sqlCondition = submissionService.createSqlConditionForSubmissionSelectQuery(problem_id, member_id);
        int total = submissionService.getTotalByQuery(sqlCondition);
        Paging paging = new Paging(nowPage, perPage, total);
        List<SubmissionJoinDto> submissionJoinDtoList = submissionService.getSubmissionJoinDtoByQueryAndPaging(sqlCondition, paging);
        model.addAttribute("submissions", submissionJoinDtoList);
        model.addAttribute("paging", paging);
        return "submission/submissionListPage";
    }

    @PostMapping("/submission")
    public String submitCode(SubmissionInfoData submissionInfoData, Authentication authentication) throws Exception {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String nickName = principalDetails.getNickname();

        int member_id = principalDetails.getUser().getId();
        int problem_id = submissionInfoData.getProblem_id();
        int language_id = submissionInfoData.getLanguage_id();
        String code = submissionInfoData.getSourceCode();
        int code_length = code.getBytes().length;

        SubmissionDto submissionDto = new SubmissionDto(0, problem_id, language_id, code, "CP","", "", code_length, LocalDateTime.now(), member_id);
        submissionService.addSubmission(submissionDto);
        return "redirect:/submissionList?problem_id=" + problem_id + "&" + "nickname=" + nickName;
    }
}
