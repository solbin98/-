package com.project.problem;

import com.project.dto.ProblemDto;
import com.project.dto.ProblemTagDto;
import com.project.dto.TagDto;
import com.project.dto.TestcaseDto;
import com.project.submission.SubmissionService;
import com.project.util.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/problems/{problem_id}")
    public String getProblemDetailPage(@PathVariable("problem_id") int problem_id, Model model) throws Exception {
        ProblemDto problemDto = problemService.getProblemById(problem_id);

        List<ProblemTagDto> problemTagDtoList = problemTagService.getProblemTagByProblemId(problem_id);
        List<TagDto> tagDtoList = tagService.getTagDtoListByProblemTagDtoList(problemTagDtoList);

        int submitNumber = submissionService.getSubmissionCountByProblemId(problem_id);
        int acSubmitNumber = submissionService.getAcSubmissionCountByProblemId(problem_id);

        List<TestcaseDto> testcaseDtoList = testcaseService.getTestCase(problem_id);

        model.addAttribute("tags", tagDtoList);
        model.addAttribute("problem", problemDto);
        model.addAttribute("submitNumber", submitNumber);
        model.addAttribute("acSubmitNumber", acSubmitNumber);
        model.addAttribute("testcases", testcaseDtoList);
        return "problem/problemPage";
    }

    @GetMapping("/problemsList*")
    public String getProblemListPage(@RequestParam(value = "page", required = false) String nowPage, Model model) throws Exception {
        int total = problemService.getTotal();
        Paging paging = new Paging(Integer.parseInt(nowPage), perPage, total);
        List<ProblemDto> problemDtoList = problemService.getProblemsByPaging(paging);

        Map<Integer, List<TagDto>> tags = new HashMap<>();
        Map<Integer, String> submits = new HashMap<>();
        Map<Integer, String> acSubmits = new HashMap<>();

        for(int i=0;i<problemDtoList.size();i++){
            int problem_id = problemDtoList.get(i).getProblem_id();
            List<ProblemTagDto> problemTagDtoList = problemTagService.getProblemTagByProblemId(problem_id);
            List<TagDto> tagDtoList = tagService.getTagDtoListByProblemTagDtoList(problemTagDtoList);

            String submitNumber = Integer.toString(submissionService.getSubmissionCountByProblemId(problem_id));
            String acSubmitNumber = Integer.toString(submissionService.getAcSubmissionCountByProblemId(problem_id));

            tags.put(problem_id, tagDtoList);
            submits.put(problem_id,submitNumber);
            acSubmits.put(problem_id, acSubmitNumber);
        }

        List<ProblemInfoData> problems = problemService.getProblemsInfoDataListByProblemDtoList(problemDtoList, tags, submits, acSubmits);

        model.addAttribute("problems", problems);
        model.addAttribute("paging", paging);
        return "problem/problemListPage";
    }

    @GetMapping("/problemWrite")
    public String getProblemWritePage(){
        return "problem/problemWritePage";
    }

    @PostMapping("/problems")
    public String writeProblem(@Validated @ModelAttribute ProblemWriteInfoData problemWriteInfoData,
                               @RequestPart(value = "testcases", required = false) Map<String, Object> testcases) throws Exception {

        ProblemDto problemDto = ConvertProblemWriteInfoDateToProblemDto(problemWriteInfoData);
        try{
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

            // Todo : 이미지 파일 db에 삽입하기
        }
        catch (Exception e){
            System.out.println("서비스 에러 발생 에러 내용 : " + e);
        }

        return "problem/problemListPage";
    }

    public ProblemDto ConvertProblemWriteInfoDateToProblemDto(ProblemWriteInfoData problemWriteInfoData){
        return new ProblemDto(0, // AutoIncrement 이므로 그냥 0을 줌.
                problemWriteInfoData.getTitle(),
                problemWriteInfoData.getTime_limit(),
                problemWriteInfoData.getMemory_limit(),
                problemWriteInfoData.getContent(),
                problemWriteInfoData.getInput_condition(),
                problemWriteInfoData.getOutput_condition(),
                problemWriteInfoData.getDifficulty()
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

        // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
        if (!Folder.exists()) {
            try{
                Folder.mkdir(); //폴더 생성합니다.
                System.out.println("폴더가 생성되었습니다.");
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }else {
            System.out.println("이미 폴더가 생성되어 있습니다.");
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
