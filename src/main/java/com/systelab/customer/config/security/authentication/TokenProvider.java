package com.systelab.customer.config.security.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
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
        List<String> authorities = (List<String>) claims.get(AUTHORITIES_KEY);
        return authorities;
    }
}
