package com.project.config;

import com.project.domain.board.common.BoardDao;
import com.project.domain.board.common.BoardFileDao;
import com.project.domain.board.LikeDBDao;
import com.project.domain.file.FileDao;
import com.project.domain.member.join.EmailAuthDao;
import com.project.domain.member.MemberDao;
import com.project.domain.member.profile.ProfileDao;
import com.project.domain.problem.dao.*;
import com.project.domain.submission.dao.LanguageDao;
import com.project.domain.submission.dao.SubmissionDao;
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
        ds.setUrl("jdbc:mysql://13.209.144.139/onlineJudge?characterEncoding=UTF-8&amp");
        ds.setUsername("solbin98");
        ds.setPassword("rmsmsrksma1@");
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
    @Bean
    public ProblemDao problemDao() { return new ProblemDao(); }
    @Bean
    public TestcaseDao testcaseDto() { return new TestcaseDao(); }
    @Bean
    public TagDao tagDao() { return new TagDao(); }
    @Bean
    public ProblemTagDao problemTagDao() { return new ProblemTagDao(); }
    @Bean
    public SubmissionDao submissionDao() { return new SubmissionDao(); }
    @Bean
    public BoardDao boardDao() { return new BoardDao(); }
    @Bean
    public FileDao fileDao() { return new FileDao(); }
    @Bean
    public LikeDBDao likeDBDao() { return new LikeDBDao(); }
    @Bean
    public ProfileDao profileDao() { return new ProfileDao(); }
    @Bean
    public LanguageDao languageDao() { return new LanguageDao(); }
    @Bean
    public ProblemFileDao problemFileDao() { return new ProblemFileDao(); }
    @Bean
    public BoardFileDao boardFileDao() { return new BoardFileDao(); }

}
