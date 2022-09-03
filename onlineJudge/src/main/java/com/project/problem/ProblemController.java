package com.project.problem;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ProblemController {
    @GetMapping("/problems*")
    public String getProblemListPage(){
        return "problem/problemListPage";
    }

    @GetMapping("/problemWrite")
    public String getProblemWritePage(){
        return "problem/problemWritePage";
    }

    @PostMapping("/problems")
    public String writeProblem(@Validated @ModelAttribute ProblemWriteInfoData problemWriteInfoData,
                               @RequestPart(value = "testcases", required = false) Map<String, Object> testcases) throws ParseException {

        int testCaseNumber = Integer.parseInt(testcases.get("testcaseNumber").toString());
        testcases.get("input"+(1));


        return "problem/problemListPage";
    }

    public void saveInputOutputFiles(List<MultipartFile> files){
        for(int i=0;i<files.size();i++){

        }
    }
}
