package com.project.board;

import java.util.List;

public class BoardWriteData {
    int member_id;
    int problem_id;
    boolean question;
    String title;
    String content;
    List<Integer> images;

    public boolean isQuestion() {
        return question;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public boolean getQuestion() {
        return question;
    }

    public void setQuestion(boolean question) {
        this.question = question;
    }

    public BoardWriteData(int problem_id, boolean question, String title, String content, List<Integer> images) {
        this.problem_id = problem_id;
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BoardWriteData{" +
                "member_id=" + member_id +
                ", problem_id=" + problem_id +
                ", question=" + question +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
