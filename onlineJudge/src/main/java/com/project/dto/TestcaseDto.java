package com.project.dto;

public class TestcaseDto {
    int testcase_id;
    int problem_id;
    String input;
    String output;

    public int getTestcase_id() {
        return testcase_id;
    }

    public TestcaseDto(int testcase_id, int problem_id, String input, String output) {
        this.testcase_id = testcase_id;
        this.problem_id = problem_id;
        this.input = input;
        this.output = output;
    }

    public void setTestcase_id(int testcase_id) {
        this.testcase_id = testcase_id;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
