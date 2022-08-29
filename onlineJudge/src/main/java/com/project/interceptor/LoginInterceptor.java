package com.project.interceptor;

import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LoginInterceptor implements HandlerInterceptor {
    private final static String[] whiteUriList = {};
    private final static String[] whiteMethodList = {};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    private boolean isInWhiteList(String[] whiteUriList, String requestURI){
        return PatternMatchUtils.simpleMatch(whiteUriList, requestURI);
    }
}
