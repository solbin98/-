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

    @GetMapping("boards*")
    public String getBoardPage(@RequestParam("question_id") int question_id, Model model) throws Exception{
        List<BoardListPageDto> answerList = boardService.getBoardsByQuestionId(question_id);
        BoardListPageDto question = boardService.getBoardByBoardId(question_id);
        model.addAttribute("question", question);
        model.addAttribute("answers", answerList);
        return "board/boardPage";
    }

    @GetMapping("boards/question-update")
    public String getBoardUpdatePage(@RequestParam("board_id") int board_id,
                                     @RequestParam("problem_id") int problem_id,
                                     @RequestParam("content") String content,
                                     @RequestParam("title") String title,
                                     Model model){
        model.addAttribute("board_id", board_id);
        model.addAttribute("problem_id", problem_id);
        model.addAttribute("content", content);
        model.addAttribute("title", title);
        return "/board/boardUpdatePage";
    }

    @PutMapping("boards/question*")
    public String updateQuestionBoard(@Valid @ModelAttribute("QuestionUpdateData") QuestionUpdateData questionUpdateData, Authentication authentication){
        int member_id = ((PrincipalDetails)(authentication.getPrincipal())).getUser().getId();
        questionUpdateData.setMember_id(member_id);
        boardService.updateQuestion(questionUpdateData);
        return "redirect:/boards?question_id=" +  questionUpdateData.getBoard_id();
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
    public Map<String, Object> addAnswerBoard(@Valid @ModelAttribute("AnswerWriteData") AnswerWriteData answerWriteData, Authentication authentication)
    throws Exception{
        int member_id = ((PrincipalDetails)(authentication.getPrincipal())).getUser().getId();
        answerWriteData.setMember_id(member_id);
        boardService.addAnswer(answerWriteData);
        Map<String, Object> map = new HashMap<>();
        map.put("resultCode", 1);
        return map;
    }
}
