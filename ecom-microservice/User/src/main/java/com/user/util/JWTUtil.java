package com.user.util;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
	
	private final Key key = Keys.hmacShaKeyFor("your-strong-secret-key-must-be-32-bytes!".getBytes());
	
	public String generateToken(String username, int userId, List<String> roles) {
	    System.out.println("Generating token with roles: " + roles);
	    return Jwts.builder()
	        .setSubject(username)
	        .claim("userId", userId)
	        .claim("roles", roles)
	        .setIssuedAt(new Date())
	        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5000))
	        .signWith(key)
	        .compact();
	}
	
	public int extractUserId(String token) {
        return extractAllClaims(token).get("userId", Integer.class);
    }
	
	public Claims extractAllClaims(String token) {
		System.out.println("Extracting All Claims "+ Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody());
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
	
	public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
	
	public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date()); // Check if the token's expiration date is before the current date
    }
	public String extractRole(String token) {
	    Claims claims = extractAllClaims(token);
	    List<String> roles = claims.get("roles", List.class);
	    if (roles != null && !roles.isEmpty()) {
	        return roles.get(0);
	    }
	    return "ROLE_USER"; // Default role if none found
	}
	
	 public boolean validateToken(String token, String username) {
	        // Here we check if the username matches and if the token is not expired
	        System.out.println("Extracted Username: " + extractUsername(token)); // Optional: for debugging
	        System.out.println("Provided Username: " + username); // Optional: for debugging
	        System.out.println("Is token expired? " + !isTokenExpired(token)); // Optional: for debugging
	        return (extractUsername(token).equals(username) && !isTokenExpired(token)); // Token is valid if username matches and token is not expired
	    }


}