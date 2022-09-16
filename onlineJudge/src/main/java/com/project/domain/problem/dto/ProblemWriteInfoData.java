package com.project.domain.problem.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class ProblemWriteInfoData {
    @NotBlank
    String title;
    @NotBlank
    String time_limit;
    @NotBlank
    String memory_limit;
    @NotBlank
    String content;
    @NotBlank
    String input_condition;
    @NotBlank
    String output_condition;
    @NotBlank
    String difficulty;
    int testcase_num;
    List<String> tags; // 파싱되어 넘어온 태그 목록
    List<Integer> images; // 최종적으로 들어온 이미지 파일 아이디 목록
    List<MultipartFile> inputFiles; // 문제의 입력 파일들
    List<MultipartFile> outputFiles; // 문제의 출력 파일들

    public int getTestcase_num() {
        return testcase_num;
    }
    public void setTestcase_num(int testcase_num) {
        this.testcase_num = testcase_num;
    }

    public ProblemWriteInfoData(String title,
                                String time_limit,
                                String memory_limit,
                                String content,
                                String input_condition,
                                String output_condition,
                                String difficulty,
                                List<String> tags,
                                List<Integer> images,
                                List<MultipartFile> inputFiles,
                                List<MultipartFile> outputFiles,
                                int testcase_num) {
        this.title = title;
        this.time_limit = time_limit;
        this.memory_limit = memory_limit;
        this.content = content;
        this.input_condition = input_condition;
        this.output_condition = output_condition;
        this.tags = tags;
        this.images = images;
        this.inputFiles = inputFiles;
        this.outputFiles = outputFiles;
        this.difficulty = difficulty;
        this.testcase_num = testcase_num;
    }

    public List<MultipartFile> getInputFiles() { return inputFiles; }

    public void setInputFiles(List<MultipartFile> inputFiles) { this.inputFiles = inputFiles; }
    public List<MultipartFile> getOutputFiles() { return outputFiles; }

    public void setOutputFiles(List<MultipartFile> outputFiles) { this.outputFiles = outputFiles; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(String time_limit) {
        this.time_limit = time_limit;
    }

    public String getMemory_limit() {
        return memory_limit;
    }

    public void setMemory_limit(String memory_limit) {
        this.memory_limit = memory_limit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInput_condition() {
        return input_condition;
    }

    public void setInput_condition(String input_condition) {
        this.input_condition = input_condition;
    }

    public String getOutput_condition() {
        return output_condition;
    }

    public void setOutput_condition(String output_condition) {
        this.output_condition = output_condition;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "ProblemWriteInfoData{" +
                "title='" + title + '\'' +
                ", time_limit='" + time_limit + '\'' +
                ", memory_limit='" + memory_limit + '\'' +
                ", content='" + content + '\'' +
                ", input_condition='" + input_condition + '\'' +
                ", output_condition='" + output_condition + '\'' +
                ", tags=" + tags +
                ", images=" + images +
                ", inputFiles=" + inputFiles +
                ", outputFiles=" + outputFiles +
                '}';
    }
}
