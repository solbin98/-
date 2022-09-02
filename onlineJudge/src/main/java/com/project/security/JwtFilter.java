package com.project.security;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;

        // 토큰 : 코스
        if(req.getMethod().equals("POST")){
            String headerAuth = req.getHeader("Authorization");
            System.out.println("헤드 어쓰 : " + headerAuth);

            if(headerAuth != null && headerAuth.equals("코스")){
                chain.doFilter(req, res);
            }
            else{
                System.out.println("인증 실패했음.");
                PrintWriter out = res.getWriter();
                out.println("인증안됨");
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 필터 인스턴스를 종료시키기 전에 호출하는 메소드
    }
}
