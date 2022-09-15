package com.project.member.profile;

public class SolvedProblemData {
    int problem_id;
    String problem_name;

    public SolvedProblemData(int problem_id, String problem_name) {
        this.problem_id = problem_id;
        this.problem_name = problem_name;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public String getProblem_name() {
        return problem_name;
    }

    public void setProblem_name(String problem_name) {
        this.problem_name = problem_name;
    }
}
