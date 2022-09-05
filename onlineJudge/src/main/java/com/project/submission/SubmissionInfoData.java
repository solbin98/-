package com.project.submission;

public class SubmissionInfoData {
    int language_id;
    String sourceCode;
    int problem_id;

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public SubmissionInfoData(int language_id, String sourceCode, int problem_id) {
        this.language_id = language_id;
        this.sourceCode = sourceCode;
        this.problem_id = problem_id;
    }

    public int getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    @Override
    public String toString() {
        return "SubmissionInfoData{" +
                "language='" + language_id + '\'' +
                ", sourceCode='" + sourceCode + '\'' +
                ", problem_id=" + problem_id +
                '}';
    }
}
