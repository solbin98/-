package com.project.config;


import com.project.board.common.BoardService;
import com.project.dao.ProfileDao;
import com.project.file.FileService;
import com.project.member.MemberService;
import com.project.member.join.JoinService;
import com.project.member.join.MailService;
import com.project.member.login.LoginService;
import com.project.member.profile.ProfileService;
import com.project.problem.*;
import com.project.member.ranking.RankingService;
import com.project.security.PrincipalDetailsService;
import com.project.submission.LanguageService;
import com.project.submission.SubmissionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import(DBConfig.class)
public class ServiceConfig {
    @Bean
    public LoginService loginService(){ return new LoginService();}
    @Bean
    public JoinService joinService(){ return new JoinService();}
    @Bean
    public MailService mailService() { return new MailService(); }
    @Bean
    public PrincipalDetailsService principalDetailsService(){ return new PrincipalDetailsService(); }
    @Bean
    public ProblemService problemService() { return new ProblemService(); }
    @Bean
    public TestcaseService testcaseService() { return new TestcaseService(); }
    @Bean
    public TagService tagService() { return new TagService(); }
    @Bean
    public ProblemTagService problemTagService() { return new ProblemTagService(); }
    @Bean
    public SubmissionService submissionService() { return new SubmissionService(); }
    @Bean
    public LanguageService languageService() { return new LanguageService(); }
    @Bean
    public MemberService memberService() { return new MemberService(); }
    @Bean
    public BoardService boardService() { return new BoardService(); }
    @Bean
    public FileService fileService() { return new FileService(); }
    @Bean
    public RankingService rankingService() { return new RankingService(); }
    @Bean
    public ProfileService profileService() { return new ProfileService(); }
}
