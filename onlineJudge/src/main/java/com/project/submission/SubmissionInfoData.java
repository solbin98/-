package com.project.submission;

public class SubmissionInfoData {
    String language;
    String sourceCode;
    int problem_id;

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public SubmissionInfoData(String language, String sourceCode, int problem_id) {
        this.language = language;
        this.sourceCode = sourceCode;
        this.problem_id = problem_id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
                "language='" + language + '\'' +
                ", sourceCode='" + sourceCode + '\'' +
                ", problem_id=" + problem_id +
                '}';
    }
}
