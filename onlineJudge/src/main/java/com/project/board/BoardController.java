package com.project.board;

import com.project.dto.BoardFileDto;
import com.project.file.FileService;
import com.project.security.PrincipalDetails;
import com.project.util.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    FileService fileService;

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
    public String getBoardUpdatePage(@Valid @ModelAttribute("QuestionUpdateData") QuestionUpdateData questionUpdateData,
                                     Model model){
        model.addAttribute("board_id", questionUpdateData.getBoard_id());
        model.addAttribute("problem_id", questionUpdateData.getProblem_id());
        model.addAttribute("content", questionUpdateData.getContent());
        model.addAttribute("title", questionUpdateData.getTitle());
        model.addAttribute("member_id", questionUpdateData.getMember_id());
        return "/board/boardUpdatePage";
    }

    @PostMapping("boards/question")
    @ResponseBody
    public String addQuestionBoard(@Valid @ModelAttribute("BoardWriteData") BoardWriteData boardWriteData, Authentication authentication) throws Exception {
        int member_id = ((PrincipalDetails)(authentication.getPrincipal())).getUser().getId();
        boardWriteData.setMember_id(member_id);
        boardService.addQuestion(boardWriteData);
        int board_id = boardService.getLastBoardId();
        fileService.setUsedColumnTrueByIdList(boardWriteData.getImages());
        fileService.addBoardFileByFileIdListAndBoardId(boardWriteData.getImages(), board_id);
        return "/boardList?page=1";
    }

    @PreAuthorize("isAuthenticated() and (( #questionUpdateData.member_id == principal.id ) or hasRole('ROLE_ADMIN'))")
    @PutMapping("boards/question*")
    @ResponseBody
    public String updateQuestionBoard(@Valid @ModelAttribute("QuestionUpdateData") QuestionUpdateData questionUpdateData, Authentication authentication) throws Exception {
        boardService.updateQuestion(questionUpdateData);
        List<BoardFileDto> boardFileDtoList = fileService.getBoardFileDtoByBoardId(questionUpdateData.getBoard_id());
        List<Integer> fileIdList = fileService.getFileIdListFromBoardFileDtoList(boardFileDtoList);
        fileService.setUsedColumnFalseByIdList(fileIdList);
        fileService.setUsedColumnTrueByIdList(questionUpdateData.getImages());
        fileService.addBoardFileByFileIdListAndBoardId(questionUpdateData.getImages(), questionUpdateData.getBoard_id());
        return "/boards?question_id=" +  questionUpdateData.getBoard_id();
    }

    @PostMapping("boards/answer")
    @ResponseBody
    public String addAnswerBoard(@Valid @ModelAttribute("AnswerWriteData") AnswerWriteData answerWriteData, Authentication authentication) throws Exception{
        int member_id = ((PrincipalDetails)(authentication.getPrincipal())).getUser().getId();
        answerWriteData.setMember_id(member_id);
        boardService.addAnswer(answerWriteData);
        int board_id = boardService.getLastBoardId();
        fileService.setUsedColumnTrueByIdList(answerWriteData.getImages());
        fileService.addBoardFileByFileIdListAndBoardId(answerWriteData.getImages(), board_id);
        return "/boards?question_id=" + answerWriteData.getQuestion_id();
    }

    @PutMapping("boards/answer*")
    @PreAuthorize("isAuthenticated() and (( #answerUpdateData.member_id == principal.id ) or hasRole('ROLE_ADMIN'))")
    @ResponseBody
    public String updateAnswerBoard(@Valid @ModelAttribute("AnswerUpdateData") AnswerUpdateData answerUpdateData) throws Exception{
        boardService.updateAnswer(answerUpdateData);
        System.out.println("여기까지 1");
        List<BoardFileDto> boardFileDtoList = fileService.getBoardFileDtoByBoardId(answerUpdateData.getBoard_id());
        System.out.println("여기까지 2");
        List<Integer> fileIdList = fileService.getFileIdListFromBoardFileDtoList(boardFileDtoList);
        System.out.println("여기까지 3");
        fileService.setUsedColumnFalseByIdList(fileIdList);
        fileService.setUsedColumnTrueByIdList(answerUpdateData.getImages());
        fileService.addBoardFileByFileIdListAndBoardId(answerUpdateData.getImages(), answerUpdateData.getBoard_id());
        return "/boards?question_id=" +  answerUpdateData.getQuestion_id();
    }

    @DeleteMapping("boards*")
    @PreAuthorize("isAuthenticated() and (( #boardDeleteData.member_id == principal.id ) or hasRole('ROLE_ADMIN'))")
    @ResponseBody
    public String deleteBoard(BoardDeleteData boardDeleteData) throws Exception {
        boardService.deleteBoardById(boardDeleteData.getBoard_id());
        List<BoardFileDto> boardFileDtoList = fileService.getBoardFileDtoByBoardId(boardDeleteData.getBoard_id());
        List<Integer> fileIdList = fileService.getFileIdListFromBoardFileDtoList(boardFileDtoList);
        fileService.setUsedColumnFalseByIdList(fileIdList);
        return "/boards?question_id=" + boardDeleteData.getQuestion_id();
    }
}
