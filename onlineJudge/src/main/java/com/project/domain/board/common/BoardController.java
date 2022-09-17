package com.project.domain.board.common;

import com.project.common.ResponseForm;
import com.project.domain.board.answer.AnswerUpdateData;
import com.project.domain.board.answer.AnswerWriteData;
import com.project.domain.board.question.BoardWriteData;
import com.project.domain.board.question.QuestionUpdateData;
import com.project.domain.file.FileService;
import com.project.domain.problem.service.ProblemService;
import com.project.security.PrincipalDetails;
import com.project.common.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
public class BoardController {
    static final int perPage = 10;

    @Autowired
    MessageSource messageSource;
    @Autowired
    ProblemService problemService;
    @Autowired
    BoardService boardService;
    @Autowired
    FileService fileService;

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
    public ResponseEntity<ResponseForm> addQuestionBoard(@Valid @RequestBody BoardWriteData boardWriteData, Authentication authentication) throws IllegalArgumentException, Exception {
        int problem_id = Integer.parseInt(boardWriteData.getProblem_id());
        if(problemService.getProblemById(problem_id) == null )
            throw new IllegalArgumentException(messageSource.getMessage("problemNotExist", null, Locale.getDefault()));

        int member_id = ((PrincipalDetails)(authentication.getPrincipal())).getUser().getId();
        boardWriteData.setMember_id(member_id);
        boardService.addQuestion(boardWriteData);
        int board_id = boardService.getLastBoardId();
        fileService.setUsedColumnTrueByIdList(boardWriteData.getImages());
        fileService.addBoardFileByFileIdListAndBoardId(boardWriteData.getImages(), board_id);

        return new ResponseEntity<>(new ResponseForm("/boardList?page=1"), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and (( #questionUpdateData.member_id == principal.id ) or hasRole('ROLE_ADMIN'))")
    @PutMapping("boards/question*")
    @ResponseBody
    public ResponseEntity<ResponseForm> updateQuestionBoard(@Valid @RequestBody QuestionUpdateData questionUpdateData, BindingResult bindingResult, Authentication authentication) throws Exception {
        if(bindingResult.hasErrors()) throw new IllegalArgumentException();

        boardService.updateQuestion(questionUpdateData);
        List<BoardFileDto> boardFileDtoList = fileService.getBoardFileDtoByBoardId(questionUpdateData.getBoard_id());
        List<Integer> fileIdList = fileService.getFileIdListFromBoardFileDtoList(boardFileDtoList);
        fileService.setUsedColumnFalseByIdList(fileIdList);
        fileService.setUsedColumnTrueByIdList(questionUpdateData.getImages());
        fileService.addBoardFileByFileIdListAndBoardId(questionUpdateData.getImages(), questionUpdateData.getBoard_id());

        return new ResponseEntity<>(new ResponseForm("/boards?question_id=" +  questionUpdateData.getBoard_id()), HttpStatus.OK);
    }

    @PostMapping("boards/answer")
    @ResponseBody
    public ResponseEntity<ResponseForm> addAnswerBoard(@RequestBody AnswerWriteData answerWriteData, BindingResult bindingResult, Authentication authentication) throws Exception{
        if(bindingResult.hasErrors()) throw new IllegalArgumentException();

        int member_id = ((PrincipalDetails)(authentication.getPrincipal())).getUser().getId();
        answerWriteData.setMember_id(member_id);
        boardService.addAnswer(answerWriteData);
        int board_id = boardService.getLastBoardId();
        fileService.setUsedColumnTrueByIdList(answerWriteData.getImages());
        fileService.addBoardFileByFileIdListAndBoardId(answerWriteData.getImages(), board_id);
        return new ResponseEntity<>(new ResponseForm("/boards?question_id=" + answerWriteData.getQuestion_id()), HttpStatus.OK);
    }

    @PutMapping("boards/answer*")
    @PreAuthorize("isAuthenticated() and (( #answerUpdateData.member_id == principal.id ) or hasRole('ROLE_ADMIN'))")
    @ResponseBody
    public ResponseEntity<ResponseForm> updateAnswerBoard(@RequestBody AnswerUpdateData answerUpdateData, BindingResult bindingResult) throws Exception{
        boardService.updateAnswer(answerUpdateData);
        List<BoardFileDto> boardFileDtoList = fileService.getBoardFileDtoByBoardId(answerUpdateData.getBoard_id());
        List<Integer> fileIdList = fileService.getFileIdListFromBoardFileDtoList(boardFileDtoList);
        fileService.setUsedColumnFalseByIdList(fileIdList);
        fileService.setUsedColumnTrueByIdList(answerUpdateData.getImages());
        fileService.addBoardFileByFileIdListAndBoardId(answerUpdateData.getImages(), answerUpdateData.getBoard_id());

        return new ResponseEntity<>(new ResponseForm("/boards?question_id=" +  answerUpdateData.getQuestion_id()), HttpStatus.OK);
    }

    @DeleteMapping("boards*")
    @PreAuthorize("isAuthenticated() and (( #boardDeleteData.member_id == principal.id ) or hasRole('ROLE_ADMIN'))")
    @ResponseBody
    public ResponseEntity<ResponseForm> deleteBoard(BoardDeleteData boardDeleteData) throws Exception {
        List<BoardFileDto> boardFileDtoList = fileService.getBoardFileDtoByBoardId(boardDeleteData.getBoard_id());
        List<Integer> fileIdList = fileService.getFileIdListFromBoardFileDtoList(boardFileDtoList);
        fileService.setUsedColumnFalseByIdList(fileIdList);
        boardService.deleteBoardById(boardDeleteData.getBoard_id());

        return new ResponseEntity<>(new ResponseForm("/boards?question_id=" + boardDeleteData.getQuestion_id()), HttpStatus.OK);
    }
}
