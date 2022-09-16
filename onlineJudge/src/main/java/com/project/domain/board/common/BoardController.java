package com.project.domain.board.common;

import com.project.common.ResponseForm;
import com.project.domain.board.answer.AnswerUpdateData;
import com.project.domain.board.answer.AnswerWriteData;
import com.project.domain.board.question.BoardWriteData;
import com.project.domain.board.question.QuestionUpdateData;
import com.project.domain.board.BoardFileDto;
import com.project.domain.file.FileService;
import com.project.security.PrincipalDetails;
import com.project.common.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
public class BoardController {
    static final int perPage = 10;
    @Autowired
    BoardService boardService;
    @Autowired
    FileService fileService;

    @ExceptionHandler
    public ResponseEntity<ResponseForm> exceptionHandler(Exception e){
        ResponseForm responseForm = new ResponseForm(e.getMessage());
        return new ResponseEntity<ResponseForm>(responseForm, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("boardList*")
    public String getBoardListPage(@RequestParam(value = "page", required = false) Integer nowPage,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "type", required = false) String type,
                                   Model model){
        if(nowPage == null) nowPage = 1;
        Paging paging = new Paging(nowPage, perPage, boardService.getTotalBoard());
        String sql = boardService.getSqlConditionByKeyWord(keyword, type);
        List<BoardListPageDto> boardList = boardService.getBoardListPageDtoByConditionSqlAndPaging(sql, paging);
        model.addAttribute("boards", boardList);
        model.addAttribute("paging", paging);
        model.addAttribute("keyword", keyword);
        model.addAttribute("type", type);
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
    public String getBoardUpdatePage(@RequestParam("board_id") int board_id, Model model) throws Exception {
        BoardListPageDto data = boardService.getBoardByBoardId(board_id);

        model.addAttribute("board_id", data.getBoard_id());
        model.addAttribute("problem_id", data.getProblem_id());
        model.addAttribute("content", data.getContent());
        model.addAttribute("title", data.getTitle());
        model.addAttribute("member_id", data.getMember_id());
        return "/board/boardUpdatePage";
    }

    @PostMapping("boards/question")
    @ResponseBody
    public String addQuestionBoard(@Valid @ModelAttribute("BoardWriteData") BoardWriteData boardWriteData, BindingResult bindingResult, Authentication authentication) throws Exception {
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
    public String updateQuestionBoard(@Valid @ModelAttribute("QuestionUpdateData") QuestionUpdateData questionUpdateData, BindingResult bindingResult, Authentication authentication) throws Exception {
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
    public String addAnswerBoard(@Valid @ModelAttribute("AnswerWriteData") AnswerWriteData answerWriteData, BindingResult bindingResult, Authentication authentication) throws Exception{
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
    public String updateAnswerBoard(@Valid @ModelAttribute("AnswerUpdateData") AnswerUpdateData answerUpdateData, BindingResult bindingResult) throws Exception{
        boardService.updateAnswer(answerUpdateData);
        List<BoardFileDto> boardFileDtoList = fileService.getBoardFileDtoByBoardId(answerUpdateData.getBoard_id());
        List<Integer> fileIdList = fileService.getFileIdListFromBoardFileDtoList(boardFileDtoList);
        fileService.setUsedColumnFalseByIdList(fileIdList);
        fileService.setUsedColumnTrueByIdList(answerUpdateData.getImages());
        fileService.addBoardFileByFileIdListAndBoardId(answerUpdateData.getImages(), answerUpdateData.getBoard_id());
        return "/boards?question_id=" +  answerUpdateData.getQuestion_id();
    }

    @DeleteMapping("boards*")
    @PreAuthorize("isAuthenticated() and (( #boardDeleteData.member_id == principal.id ) or hasRole('ROLE_ADMIN'))")
    @ResponseBody
    public String deleteBoard(BoardDeleteData boardDeleteData) throws Exception {
        List<BoardFileDto> boardFileDtoList = fileService.getBoardFileDtoByBoardId(boardDeleteData.getBoard_id());
        List<Integer> fileIdList = fileService.getFileIdListFromBoardFileDtoList(boardFileDtoList);
        fileService.setUsedColumnFalseByIdList(fileIdList);
        boardService.deleteBoardById(boardDeleteData.getBoard_id());
        return "/boards?question_id=" + boardDeleteData.getQuestion_id();
    }
}
