package com.project.config;


import com.project.member.join.JoinService;
import com.project.member.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
@Import(DBConfig.class)
public class ServiceConfig {

    @Bean
    public LoginService loginService(){ return new LoginService();}

    @Bean
    public JoinService joinService(){ return new JoinService();}
}
