package com.project.config;

import com.project.member.join.JoinController;
import com.project.member.login.LoginController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {
    @Bean
    public LoginController loginController(){ return new LoginController(); }

    @Bean
    public JoinController joinController(){ return new JoinController(); }
}
