package com.admin.util;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
    private final Key key = Keys.hmacShaKeyFor("your-strong-secret-key-must-be-32-bytes!".getBytes());
    private static final long EXPIRATION_TIME = 86400000; // 24 hours
    
    public String generateToken(String username, int userId, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    
    public int extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        // Different ways to handle extraction based on how userId is stored
        Object userIdObj = claims.get("userId");
        if (userIdObj instanceof Integer) {
            return (Integer) userIdObj;
        } else if (userIdObj instanceof Map) {
            return ((Number) ((Map<?, ?>) userIdObj).get("userId")).intValue();
        } else if (userIdObj instanceof Number) {
            return ((Number) userIdObj).intValue();
        }
        return 0; // Default if not found or wrong format
    }
    
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        Object rolesObj = claims.get("roles");
        if (rolesObj instanceof List) {
            return (List<String>) rolesObj;
        }
        return List.of(); // Return empty list if roles not found or wrong format
    }
    
    public String extractPrimaryRole(String token) {
        List<String> roles = extractRoles(token);
        return roles != null && !roles.isEmpty() ? roles.get(0) : "USER";
    }
    
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = extractAllClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true; // Consider invalid tokens as expired
        }
    }
    
    public boolean validateToken(String token, String username) {
        try {
            final String tokenUsername = extractUsername(token);
            return (tokenUsername.equals(username) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean hasAdminRole(String token) {
        List<String> roles = extractRoles(token);
        return roles != null && roles.contains("ADMIN");
    }
}