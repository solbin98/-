package com.project.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    // login 시도를 하면 호출되는 함수임
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter 도착 !!");

        String username = request.getParameter("username");
        String password  = request.getParameter("password");
        System.out.println("username : "+username + " " + "password : " + password);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(token);

        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        System.out.println(principalDetails.getUser().toString());


        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain
    , Authentication authentication) throws ServletException, IOException {
        System.out.println("successfulAuthentication 호출 : 정상 완료");

        JwtProvider jwtProvider = new JwtProvider();
        String jwtToken = jwtProvider.generateJwtToken(authentication);
        
        response.addHeader("Authorization", "Bearer "+jwtToken);
        super.successfulAuthentication(request, response, chain, authentication);
    }
}
