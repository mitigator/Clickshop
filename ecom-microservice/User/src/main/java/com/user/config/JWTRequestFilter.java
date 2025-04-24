package com.user.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.user.service.UserDetailsServiceImpl;
import com.user.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(JWTRequestFilter.class);
    
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    @Autowired
    private JWTUtil jwtUtil;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain)
            throws ServletException, IOException {
        
        logger.info("Processing request: " + request.getRequestURI());
        final String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.info("No Bearer token found in request");
            filterChain.doFilter(request, response);
            return;
        }
        
        try {
            String jwt = authHeader.substring(7);
            logger.info("JWT token extracted: " + jwt.substring(0, Math.min(10, jwt.length())) + "...");
            
            String username = jwtUtil.extractUsername(jwt);
            logger.info("Username extracted from token: " + username);
            
            // Only proceed if we have a username and no authentication exists yet
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                logger.info("Loading user details for: " + username);
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                logger.info("User loaded: " + userDetails.getUsername() + ", authorities: " + userDetails.getAuthorities());
                
                // Validate the token using the username from UserDetails
                boolean isValid = jwtUtil.validateToken(jwt, userDetails.getUsername());
                logger.info("Token validation result: " + isValid);
                
                if (isValid) {
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(
                            userDetails, 
                            null, 
                            userDetails.getAuthorities()
                        );
                    
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.info("Authentication set in SecurityContext");
                } else {
                    logger.warn("Token validation failed");
                }
            } else {
                logger.info("Username is null or authentication already exists in context");
            }
            
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Authentication error: ", e);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token: " + e.getMessage());
        }
    }
}