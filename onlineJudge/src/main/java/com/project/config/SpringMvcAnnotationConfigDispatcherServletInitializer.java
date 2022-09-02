package com.project.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class SpringMvcAnnotationConfigDispatcherServletInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {
                SecurityConfig.class,
                ServiceConfig.class,
                MvcConfig.class,
                ControllerConfig.class,
                DBConfig.class,
        };
    }
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
