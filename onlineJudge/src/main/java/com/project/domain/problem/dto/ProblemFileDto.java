package com.project.domain.problem.dto;

public class ProblemFileDto {
    int problem_id;
    int file_id;

    public ProblemFileDto(int problem_id, int file_id) {
        this.problem_id = problem_id;
        this.file_id = file_id;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }
}
