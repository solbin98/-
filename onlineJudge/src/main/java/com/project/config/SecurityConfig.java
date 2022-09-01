package com.project.config;

import com.project.security.JwtFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

    @Override
    protected void configure(HttpSecurity http) throws  Exception{
        JwtFilter jwtFilter = new JwtFilter();

        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);


        http.addFilterBefore(jwtFilter, BasicAuthenticationFilter.class);
        http.addFilterBefore(encodingFilter, jwtFilter.getClass());
        http.csrf().disable();
        http.authorizeRequests().anyRequest().permitAll();
    }

}
