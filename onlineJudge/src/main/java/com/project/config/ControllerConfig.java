package com.project.config;

import com.project.board.BoardController;
import com.project.image.ImageController;
import com.project.mainController;
import com.project.member.join.JoinController;
import com.project.member.login.LoginController;
import com.project.problem.ProblemController;
import com.project.submission.SubmissionController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {
    @Bean
    public LoginController loginController(){ return new LoginController(); }

    @Bean
    public JoinController joinController(){ return new JoinController(); }

    @Bean
    public mainController mainController() { return new mainController();}

    @Bean
    public ProblemController problemController() { return new ProblemController(); }

    @Bean
    public ImageController imageController() { return new ImageController(); }

    @Bean
    public SubmissionController submissionController() { return new SubmissionController(); }

    @Bean
    public BoardController boardController() { return new BoardController(); }

}
