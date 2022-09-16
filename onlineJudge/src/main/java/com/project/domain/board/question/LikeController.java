package com.project.domain.board.question;

import com.project.domain.board.LikeDBDao;
import com.project.domain.board.LikeDBDto;
import com.project.security.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LikeController {
    // like 서비스를 구현하는 것이 정석이지만,
    // 재 활용성이 적은 파트이기 때문에 Dao 를 controller 에서 직접 불러오도록 간단하게 구현
    @Autowired
    LikeDBDao likeDBDao;

    @PostMapping("like")
    public String addLike(@RequestParam("board_id") int board_id, Authentication authentication){
        int member_id = ((PrincipalDetails)(authentication.getPrincipal())).getUser().getId();
        try{
            int cnt = likeDBDao.selectCountByBoardIdAndMemberId(board_id, member_id);
            if(cnt > 0) likeDBDao.deleteByBoardIdAndMemberId(board_id, member_id);
            else likeDBDao.insert(new LikeDBDto(board_id, member_id));
        }
        catch (Exception exception){}
        return "redirect:/boards?question_id=" + board_id;
    }
}
