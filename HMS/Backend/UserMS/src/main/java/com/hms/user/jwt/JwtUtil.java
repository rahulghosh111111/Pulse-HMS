package com.hms.user.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    private static final String SECRET = "d44c3b4d70200c65b24ba7d10339868e580d071a5c7cef7aa21f3ac82286dafd04cd708e2e9182b9b8ad715cc5603922a9c81ce82bd735175c4ed559e11c878b";
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60L;
    public String generateToken(UserDetails userDetails) {
      Map<String, Object> claims = new HashMap<>();
      CustomUserDetails user = (CustomUserDetails) userDetails;
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        claims.put("name", user.getName());
        return doGenerateToken(claims, user.getUsername());

    }
    public String doGenerateToken(Map<String, Object> claims , String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}
