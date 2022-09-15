package com.project.member.ranking;

import com.project.util.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RankingController {
    private final static int perPage = 50;
    @Autowired
    RankingService rankingService ;

    @GetMapping("rankingPage*")
    public String getRankingPage(@RequestParam(value = "page", required = false) Integer page, Model model) throws Exception {
        if(page == null) page = 1;
        int total = rankingService.getTotal();
        Paging paging = new Paging(page, perPage, total);
        List<RankingData> rankingData = rankingService.getRankingDataByPaging(paging);
        model.addAttribute("rankingData", rankingData);
        model.addAttribute("paging", paging);
        return "ranking/rankingPage";
    }
}
