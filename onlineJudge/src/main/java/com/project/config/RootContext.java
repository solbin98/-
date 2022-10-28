package com.project.config;

import com.project.common.Scheduler;
import com.project.domain.member.join.AuthKeyMaker;
import com.project.domain.member.join.JoinValidator;
import com.project.exception.ExceptionHandlerClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Properties;

@Configuration
@ComponentScan(
        basePackages= "com.project.domain",
        excludeFilters= @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
)

public class RootContext {
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasenames("/resources/messages/errors_ko",
                "/resources/messages/labels_ko",
                "/resources/messages/system_ko",
                "/resources/messages/response_ko");
        source.setDefaultEncoding("utf-8");
        return source;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxInMemorySize(100000000);
        resolver.setMaxUploadSize(200000000);
        return resolver;
    }

    @Bean(name="mailSender")
    public JavaMailSenderImpl mailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername(InfoForProject.JavaMailSenderUserName);
        javaMailSender.setPassword(InfoForProject.JavaMailSenderPassword);
        javaMailSender.setDefaultEncoding("utf-8");

        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "true");
        properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }

    @Bean
    public AuthKeyMaker authKeyMaker(){
        return new AuthKeyMaker();
    }

    @Bean
    public JoinValidator joinValidator() { return new JoinValidator();}

    @Bean
    public Scheduler scheduler() { return new Scheduler(); }

    @Bean
    public ExceptionHandlerClass exceptionHandlerController() { return new ExceptionHandlerClass(); }
}
