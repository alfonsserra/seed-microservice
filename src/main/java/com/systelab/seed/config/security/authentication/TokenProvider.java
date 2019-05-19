package com.systelab.seed.config.security.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Configuration
public class TokenProvider implements Serializable {

    public static final String AUTHORITIES_KEY = "scopes";

    private final JwtConfig jwtConfig;

    @Autowired
    public TokenProvider(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }


    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getClientSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    public List<String> getAuthorities(Claims claims) {
        return Arrays.asList(claims.get(AUTHORITIES_KEY).toString().split(","));
    }

    public String generateToken(String username, String roles, int validityInSeconds) {
        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, roles)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getClientSecret())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityInSeconds * 1000))
                .compact();
    }
}
