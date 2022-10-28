package com.project.domain.board.common.controller;

import com.project.common.ResponseForm;
import com.project.domain.board.answer.AnswerUpdateData;
import com.project.domain.board.answer.AnswerWriteData;
import com.project.domain.board.common.dto.BoardDeleteData;
import com.project.domain.board.common.dto.BoardFileDto;
import com.project.domain.board.common.dto.BoardFileQuestionDto;
import com.project.domain.board.common.dto.BoardListPageDto;
import com.project.domain.board.common.service.BoardService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
        Paging paging = new Paging(nowPage, perPage, boardService.getTotalQuestionBoard());
        String sql = boardService.getSqlConditionByKeyWord(keyword, type);
        List<BoardListPageDto> boardList = boardService.getBoardListPageDtoByConditionSqlAndPaging(sql, paging);

        int boardListSize = boardList.size();
        for(int i=0;i<boardListSize;i++){
            String content = boardList.get(i).getContent();
            boardList.get(i).setContent(makeViewMoreStr(content, 100));
        }

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
    @Transactional
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
    @Transactional
    public ResponseEntity<ResponseForm> updateQuestionBoard(@Valid @RequestBody QuestionUpdateData questionUpdateData, Authentication authentication) throws Exception {
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
    @Transactional
    public ResponseEntity<ResponseForm> addAnswerBoard(@RequestBody AnswerWriteData answerWriteData, Authentication authentication) throws Exception{
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
    @Transactional
    public ResponseEntity<ResponseForm> updateAnswerBoard(@ModelAttribute AnswerUpdateData answerUpdateData) throws Exception{

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
    @Transactional
    public ResponseEntity<ResponseForm> deleteBoard(BoardDeleteData boardDeleteData) throws Exception {
        List<BoardFileDto> questionBoardFileDtoList = fileService.getBoardFileDtoByBoardId(boardDeleteData.getBoard_id());
        List<BoardFileQuestionDto> answerBoardFileDtoList = fileService.getBoardFileDtoByQuestionId(boardDeleteData.getBoard_id());

        List<Integer> questionFileIdList = fileService.getFileIdListFromBoardFileDtoList(questionBoardFileDtoList);
        List<Integer> answerFileIdList = fileService.getFileIdListFromBoardFileQuestionDtoList(answerBoardFileDtoList);

        fileService.setUsedColumnFalseByIdList(questionFileIdList);
        fileService.setUsedColumnFalseByIdList(answerFileIdList);
        boardService.deleteBoardById(boardDeleteData.getBoard_id());
        boardService.deleteBoardByQuestionId(boardDeleteData.getBoard_id());
        return new ResponseEntity<>(new ResponseForm("/boardList?page=1"), HttpStatus.OK);
    }

    String makeViewMoreStr(String str, int maxSize){
        if(str.length() > maxSize) str = str.substring(0, maxSize) + "...";
        return str;
    }
}
