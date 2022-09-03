package com.project.config;

import com.project.mainController;
import com.project.member.join.JoinController;
import com.project.member.login.LoginController;
import com.project.problem.ProblemController;
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

}
