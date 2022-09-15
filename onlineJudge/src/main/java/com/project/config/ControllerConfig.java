package com.project.config;

import com.project.board.common.BoardController;
import com.project.board.question.LikeController;
import com.project.image.ImageController;
import com.project.mainController;
import com.project.member.join.JoinController;
import com.project.member.login.LoginController;
import com.project.member.profile.ProfileController;
import com.project.problem.ProblemController;
import com.project.member.ranking.RankingController;
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

    @Bean
    public LikeController likeController() { return new LikeController(); }

    @Bean
    public RankingController rankingController() { return new RankingController(); }

    @Bean
    public ProfileController profileController() { return new ProfileController(); }
}
