package com.project.domain.problem.dto;

public class ProblemTagJoinDto {
    int problem_id;
    int tag_id;
    String name;

    public ProblemTagJoinDto(int problem_id, int tag_id, String name) {
        this.problem_id = problem_id;
        this.tag_id = tag_id;
        this.name = name;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
