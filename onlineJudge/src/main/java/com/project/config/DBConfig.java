package com.project.config;

import com.project.dao.EmailAuthDao;
import com.project.dao.MemberDao;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DBConfig {
    @Bean(destroyMethod = "close")
    public DataSource dataSource(){
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost/onlinejudge?characterEncoding=utf8");
        ds.setUsername("root");
        ds.setPassword("232423e");
        ds.setInitialSize(5);
        ds.setMaxActive(20);
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public EmailAuthDao emailAuthDao(){ return new EmailAuthDao(); }
    @Bean
    public MemberDao memberDao(){ return new MemberDao(); }
}
