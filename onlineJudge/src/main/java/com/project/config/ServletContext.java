package com.project.config;



import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan(
        basePackages = "com.project.domain",
        excludeFilters= @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Service.class, Repository.class})
)
public class ServletContext implements WebMvcConfigurer {
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
}
