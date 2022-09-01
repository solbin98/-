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

    // bean 설정과 spring container 설정을 위한 Config 클래스를 등록한다.
// Config 클래스는 web.xml의 dispatcher servlet 초기화에 사용된 xml과 같은 기능을 한다.
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

    // web.xml의 servlet mapping 부분을 대체한다.
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    //<filter>
        //<filter-name>springSecurityFilterChain</filter-name>
        //<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
    //</filter>

    //<filter-mapping>
        //<filter-name>springSecurityFilterChain</filter-name>
        //<url-pattern>/*</url-pattern>
    //</filter-mapping>
}
