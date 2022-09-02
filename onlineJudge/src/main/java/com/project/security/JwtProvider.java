package com.project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import java.util.Date;

public class JwtProvider {

    // secret key
    private String secretKey = "secretto";

    // access token 유효시간
    private final long accessTokenValidTime = 2 * 60 * 60 * 1000L;

    // refresh token 유효시간
    private final long refreshTokenValidTime = 2 * 7 * 24 * 60 * 60 * 1000L;

    public String generateJwtToken(Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(authentication.getPrincipal()));
        System.out.println(authentication.getAuthorities().toString());
        claims.put("roles", authentication.getAuthorities());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
