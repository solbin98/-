package com.project.domain.problem.dto;

public class ProblemTagDto {
    int problem_id;
    int tag_id;

    public ProblemTagDto(int problem_id, int tag_id) {
        this.problem_id = problem_id;
        this.tag_id = tag_id;
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
}
