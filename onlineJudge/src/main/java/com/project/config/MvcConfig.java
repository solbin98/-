package com.project.config;


import com.project.common.Scheduler;
import com.project.domain.member.join.JoinValidator;
import com.project.domain.member.join.AuthKeyMaker;
import com.project.exception.ExceptionHandlerClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableScheduling
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/images/boards/**").addResourceLocations("file:///C:/springTest/images/boards/");
        registry.addResourceHandler("/images/profiles/**").addResourceLocations("file:///C:/springTest/images/profiles/");
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxInMemorySize(100000000);
        resolver.setMaxUploadSize(200000000);
        return resolver;
    }

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


    @Bean(name="mailSender")
    public JavaMailSenderImpl mailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("exode4@gmail.com");
        javaMailSender.setPassword("rrbbwfvlmiqjptsi");
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
