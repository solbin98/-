package com.project.board;

import com.project.security.PrincipalDetails;
import com.project.util.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {
    static final int perPage = 10;
    @Autowired
    BoardService boardService;

    @GetMapping("boardList*")
    public String getBoardListPage(@RequestParam(value = "page", required = false) Integer nowPage,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   Model model){
        if(nowPage == null) nowPage = 1;
        Paging paging = new Paging(nowPage, perPage, boardService.getTotalBoard());
        String sql = boardService.getSqlConditionByKeyWord(keyword);
        List<BoardListPageDto> boardList = boardService.getBoardListPageDtoByConditionSqlAndPaging(sql, paging);
        model.addAttribute("boards", boardList);
        model.addAttribute("paging", paging);
        return "board/boardListPage";
    }

    @GetMapping("board-write")
    public String getBoardWritePage(){
        return "board/boardWritePage";
    }

    @PostMapping("boards/question")
    @ResponseBody
    public Map<String, Object> addQuestionBoard(@Valid @ModelAttribute("BoardWriteData") BoardWriteData boardWriteData, Authentication authentication){
        int member_id = ((PrincipalDetails)(authentication.getPrincipal())).getUser().getId();
        boardWriteData.setMember_id(member_id);
        boardService.addQuestion(boardWriteData);
        Map<String, Object> map = new HashMap<>();
        map.put("resultCode", 1);
        return map;
    }

    @PostMapping("boards/answer")
    @ResponseBody
    public Map<String, Object> addAnswerBoard(@Valid @ModelAttribute("AnswerWriteData") AnswerWriteData answerWriteData, Authentication authentication){
        int member_id = ((PrincipalDetails)(authentication.getPrincipal())).getUser().getId();
        answerWriteData.setMember_id(member_id);
        boardService.addAnswer(answerWriteData);
        Map<String, Object> map = new HashMap<>();
        map.put("resultCode", 1);
        return map;
    }
}
