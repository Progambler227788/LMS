package com.codingwork.lms.security.jwt;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

/*
Generate a JWT token using HMAC SHA256 with a secret key.
Extract the username from the token.
Validate the token for expiration and authenticity.
*/
@Component
public class JwtUtil {

    private String SECRET_KEY;

    public JwtUtil(Dotenv dotenv) {
        this.SECRET_KEY = dotenv.get("SECRET_KEY");
        // Add validation
        if (this.SECRET_KEY == null || this.SECRET_KEY.isEmpty()) {
            throw new IllegalStateException("SECRET_KEY must be configured in .env file");
        }
    }


    private final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Generate token using username, set the role as a claim, and set the expiration time, issue time
    // and sign it with the secret key
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username) // Now using username
                .claim("role", role ) // Include role as claim
                .setIssuedAt(new Date()) // issue time, creation time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    //  Extract username from token
    public String extractUserName(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // Validate JWT, make sure token is signed and not expired
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        return authentication.getName();
    }
}
