package com.example.demo.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtServiceUtil {
    @Value("${jwt.secret:}")
    private String secret;

    @Value("${jwt.expiration:`3600000}")
    private long expiration;

    @Value("${jwt.username-field:}")
    private String userName;

    @Value("${jwt.password-field:}")
    private String password;

    @Value("${jwt.domain:}")
    private String domain;

    @Value("${jwt.groupCode:}")
    private String groupCode;

    private static final long serialVersionUID = 1L;

    public String generateToken(String username, String password, String domain, String groupCode) {
        // Validate credentials against configured values
        if (!this.userName.equals(username) || !this.password.equals(password)
                || !this.domain.equals(domain) || !this.groupCode.equals(groupCode)) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public long getExpirationTime() {
        return expiration;
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        String extractedUsername = extractUsername(token);
        return (this.userName.equals(extractedUsername) && !isTokenExpired(token));
    }

    public UserDetails getUserDetails(String username) {
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password("") // No password in token
                .authorities("USER") // Default authority
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
