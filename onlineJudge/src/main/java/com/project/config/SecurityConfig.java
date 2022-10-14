package com.project.config;

import com.project.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.filter.CharacterEncodingFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
    @Bean
    public FailureHandler failureHandler() { return new FailureHandler();}

    @Override
    protected void configure(HttpSecurity http) throws  Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/submit", "/board-write", "/boards/question*", "/boards/answer*", "/submissionPage*", "profilePage*").hasAnyRole("USER", "ADMIN")
                .antMatchers("/problem-write").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureHandler(failureHandler()) // 로그인 실패 핸들러
                .and()
                .logout()
                .invalidateHttpSession(true).deleteCookies("JSESSIONID");

    }
}
