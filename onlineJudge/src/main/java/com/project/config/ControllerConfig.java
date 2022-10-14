package com.project.config;

import com.project.domain.board.common.controller.BoardController;
import com.project.domain.board.question.LikeController;
import com.project.domain.image.ImageController;
import com.project.domain.member.join.JoinController;
import com.project.domain.member.login.LoginController;
import com.project.domain.member.profile.ProfileController;
import com.project.domain.problem.controller.ProblemController;
import com.project.domain.member.ranking.RankingController;
import com.project.domain.submission.SubmissionController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {
    @Bean
    public LoginController loginController(){ return new LoginController(); }

    @Bean
    public JoinController joinController(){ return new JoinController(); }

    @Bean
    public ProblemController problemController() { return new ProblemController(); }

    @Bean
    public ImageController imageController() { return new ImageController(); }

    @Bean
    public SubmissionController submissionController() { return new SubmissionController(); }

    @Bean
    public BoardController boardController() { return new BoardController(); }

    @Bean
    public LikeController likeController() { return new LikeController(); }

    @Bean
    public RankingController rankingController() { return new RankingController(); }

    @Bean
    public ProfileController profileController() { return new ProfileController(); }
}
