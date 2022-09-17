package com.project.domain.board.question;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class QuestionUpdateData{
    int member_id;
    int board_id;
    @NotBlank
    int problem_id;
    @NotBlank
    String title;
    @NotBlank
    String content;
    List<Integer> images;

    public QuestionUpdateData(int member_id, int board_id, int problem_id, String title, String content, List<Integer> images) {
        this.member_id = member_id;
        this. board_id = board_id;
        this.problem_id = problem_id;
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
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

    public List<Integer> getImages() {return images;}

    public void setImages(List<Integer> images) {this.images = images;}

    @Override
    public String toString() {
        return "QuestionUpdateData{" +
                "member_id=" + member_id +
                ", board_id=" + board_id +
                ", problem_id=" + problem_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
