package com.project.domain.problem.controller;

import com.project.common.ResponseForm;
import com.project.domain.file.FileService;
import com.project.domain.problem.dto.*;
import com.project.domain.problem.service.ProblemService;
import com.project.domain.problem.service.ProblemTagService;
import com.project.domain.problem.service.TagService;
import com.project.domain.problem.service.TestcaseService;
import com.project.domain.submission.service.SubmissionService;
import com.project.common.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class ProblemController {
    private final static int perPage = 10;
    @Autowired
    MessageSource messageSource;
    @Autowired
    ProblemService problemService;
    @Autowired
    TestcaseService testcaseService;
    @Autowired
    ProblemTagService problemTagService;
    @Autowired
    TagService tagService;
    @Autowired
    SubmissionService submissionService;
    @Autowired
    FileService fileService;

    @GetMapping("/problem/{problem_id}")
    public String getProblemDetailPage(@PathVariable("problem_id") int problem_id, Model model) throws Exception {
        ProblemDto problemDto = problemService.getProblemById(problem_id);

        List<ProblemTagJoinDto> problemTagJoinDtoList = problemTagService.getProblemTagJoinDtoListByProblemId(problem_id);

        int submitNumber = submissionService.getSubmissionCountByProblemId(problem_id);
        int acSubmitNumber = submissionService.getAcSubmissionCountByProblemId(problem_id);

        List<TestcaseDto> testcaseDtoList = testcaseService.getTestCase(problem_id);

        model.addAttribute("tags", problemTagJoinDtoList);
        model.addAttribute("problem", problemDto);
        model.addAttribute("submitNumber", submitNumber);
        model.addAttribute("acSubmitNumber", acSubmitNumber);
        model.addAttribute("testcases", testcaseDtoList);
        return "problem/problemPage";
    }

    @GetMapping("/problemList*")
    public String getProblemListPage(@RequestParam(value = "page", required = false) Integer nowPage, Model model) throws Exception {
        if(nowPage == null) nowPage = 1;
        int total = problemService.getTotal();

        Paging paging = new Paging(nowPage, perPage, total);
        List<ProblemDto> problemDtoList = problemService.getProblemsByPaging(paging);
        List<ProblemInfoData> problems = new ArrayList<>();

        for(int i=0;i<problemDtoList.size();i++){
            int problem_id = problemDtoList.get(i).getProblem_id();
            List<ProblemTagJoinDto> tagDtoList = problemTagService.getProblemTagJoinDtoListByProblemId(problem_id);
            int submitNumber = submissionService.getSubmissionCountByProblemId(problem_id);
            int acSubmitNumber = submissionService.getAcSubmissionCountByProblemId(problem_id);
            problems.add(problemService.convertProblemDtoToProblemInfoData(problemDtoList.get(i), submitNumber, acSubmitNumber, tagDtoList));
        }

        model.addAttribute("problems", problems);
        model.addAttribute("paging", paging);
        return "problem/problemListPage";
    }

    @GetMapping("/problem-write")
    public String getProblemWritePage(){
        return "problem/problemWritePage";
    }

    @PostMapping("/problem")
    @ResponseBody
    @Transactional
    public ResponseEntity<ResponseForm> writeProblem(@Valid @ModelAttribute ProblemWriteInfoData problemWriteInfoData, BindingResult bindingResult,
                                                     @RequestPart(value = "testcases", required = false) Map<String, Object> testcases) throws Exception {

        if(bindingResult.hasErrors()){
            List<ObjectError> list = bindingResult.getAllErrors();
            String message = "";
            for(int i=0;i<list.size();i++) message += list.get(i).getDefaultMessage() + "\n";
            throw new IllegalArgumentException(message);
        }

        ProblemDto problemDto = ConvertProblemWriteInfoDateToProblemDto(problemWriteInfoData);
        // problem db에 삽입하기
        int problem_id = problemService.addNewProblem(problemDto);

        // problem 테스트 케이스 db에 추가
        List<TestcaseDto> testcaseDtoList = getTestCaseDtoList(testcases, problem_id);
        for(int i=0;i<testcaseDtoList.size();i++) testcaseService.addTestCase(testcaseDtoList.get(i));

        // 채점 데이터 해당하는 경로에 저장해주기
        // 1. makeProblemInputOutputFilesFolder 호출해서 폴더가 없는 경우 만들어주고
        // 2. saveInputOutputFiles() 호출해서 채점 파일들을 저장해준다.
        makeProblemInputOutputFilesFolder(problem_id);
        saveInputOutputFiles(problemWriteInfoData.getInputFiles(), problemWriteInfoData.getOutputFiles(), problem_id);

        // 태그 db에 추가
        // 1. 태그의 이름을 바탕으로 태그 db 업데이트 해줌.
        // 2. 문제-태그 연관 db도 같이 업데이트
        tagService.addTagByTagNameList(problemWriteInfoData.getTags());
        List<TagDto> tagDtoList = tagService.getTagDtoListByTagNames(problemWriteInfoData.getTags());
        problemTagService.addProblemTagDtos(tagDtoList, problem_id);

        // 1. 게시글에서 실제로 사용하는 이미지 파일의 used 칼럼 값을 true 로 바꿔주기.
        // 2. problem_file 테이블에 새로운 행들 추가해주기.
        fileService.setUsedColumnTrueByIdList(problemWriteInfoData.getImages());
        fileService.addProblemFileByFileIdListAndBoardId(problemWriteInfoData.getImages(), problem_id);

        ResponseForm responseForm = new ResponseForm("");
        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

    public ProblemDto ConvertProblemWriteInfoDateToProblemDto(ProblemWriteInfoData problemWriteInfoData){
        return new ProblemDto(0, // AutoIncrement 이므로 그냥 0을 줌.
                problemWriteInfoData.getTitle(),
                problemWriteInfoData.getTime_limit(),
                problemWriteInfoData.getMemory_limit(),
                problemWriteInfoData.getContent(),
                problemWriteInfoData.getInput_condition(),
                problemWriteInfoData.getOutput_condition(),
                problemWriteInfoData.getDifficulty(),
                problemWriteInfoData.getTestcase_num()
                );
    }

    public List<TestcaseDto> getTestCaseDtoList(Map<String, Object> testcases, int problem_id){
        int testCaseNumber = Integer.parseInt(testcases.get("testcaseNumber").toString());
        List<TestcaseDto> ret = new ArrayList<TestcaseDto>();
        for(int i=1;i<=testCaseNumber;i++){
            TestcaseDto testcaseDto = new TestcaseDto(
                    0,
                    problem_id,
                    testcases.get("input"+i).toString(),
                    testcases.get("output"+i).toString());
            ret.add(testcaseDto);
        }
        return ret;
    }

    public void makeProblemInputOutputFilesFolder(int problem_id){
        String path = messageSource.getMessage("path.testcases.board", null, Locale.KOREAN) + "\\" + problem_id;
        File Folder = new File(path);
        if (!Folder.exists()) {
            try{
                Folder.mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }
    }

    public void saveInputOutputFiles(List<MultipartFile> inputFiles, List<MultipartFile> outputFiles, int problem_id) throws IOException {
        String path = messageSource.getMessage("path.testcases.board", null, Locale.KOREAN) + "\\" + problem_id;

        for(int i=0;i<inputFiles.size();i++){
            String fileSavePath = path + "\\input" + (i+1) + ".txt";
            inputFiles.get(i).transferTo(new File(fileSavePath));
        }
        for(int i=0;i<outputFiles.size();i++){
            String fileSavePath = path + "\\output" + (i+1) + ".txt";
            outputFiles.get(i).transferTo(new File(fileSavePath));
        }
    }
}
