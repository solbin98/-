package com.project.board;

public class AnswerWriteData extends BoardWriteData{
    int question_id;

    public AnswerWriteData(int problem_id, boolean question, String title, String content) {
        super(problem_id, question, title, content);
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
}
