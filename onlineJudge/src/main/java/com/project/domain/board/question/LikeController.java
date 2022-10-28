package com.project.domain.board.question;

import com.project.domain.board.like.LikeDBDao;
import com.project.domain.board.like.LikeDBDto;
import com.project.security.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LikeController {
    @Autowired
    LikeDBDao likeDBDao;

    @PostMapping("like")
    public String addLike(@RequestParam("board_id") int board_id, Authentication authentication){
        int member_id = ((PrincipalDetails)(authentication.getPrincipal())).getUser().getId();
        int cnt = likeDBDao.selectCountByBoardIdAndMemberId(board_id, member_id);
        if(cnt > 0) likeDBDao.deleteByBoardIdAndMemberId(board_id, member_id);
        else likeDBDao.insert(new LikeDBDto(board_id, member_id));
        return "redirect:/boards?question_id=" + board_id;
    }
}
