package com.project.config;


import com.project.domain.board.common.BoardService;
import com.project.domain.problem.service.ProblemService;
import com.project.domain.problem.service.ProblemTagService;
import com.project.domain.problem.service.TagService;
import com.project.domain.problem.service.TestcaseService;
import com.project.domain.file.FileService;
import com.project.domain.member.MemberService;
import com.project.domain.member.join.JoinService;
import com.project.domain.member.join.MailService;
import com.project.domain.member.login.LoginService;
import com.project.domain.member.profile.ProfileService;
import com.project.domain.member.ranking.RankingService;
import com.project.security.PrincipalDetailsService;
import com.project.domain.submission.service.LanguageService;
import com.project.domain.submission.service.SubmissionService;
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
