package com.admin.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.admin.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String requestPath = request.getServletPath();

            // Bypass authentication for public endpoints
            if (isPublicEndpoint(requestPath)) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwt = extractJwtFromRequest(request);
            
            if (!StringUtils.hasText(jwt)) {
                sendUnauthorizedError(response, "Missing authorization token");
                return;
            }

            if (jwtUtil.isTokenExpired(jwt)) {
                sendUnauthorizedError(response, "Token has expired");
                return;
            }

            String username = jwtUtil.extractUsername(jwt);
            int userId = jwtUtil.extractUserId(jwt);
            List<String> roles = jwtUtil.extractRoles(jwt);

            // Debug logging
            logger.debug("Extracted JWT info - Username: {}, UserId: {}, Roles: {}", 
                         username, userId, roles);
            
            // Verify ADMIN role for admin endpoints
            if (isAdminEndpoint(requestPath) && !hasAdminRole(roles)) {
                logger.warn("Access denied to admin endpoint for user: {} with roles: {}", 
                           username, roles);
                sendForbiddenError(response, "Admin access required");
                return;
            }

            setAuthenticationInContext(request, username, userId, roles);
            
        } catch (Exception e) {
            logger.error("Failed to process authentication request", e);
            sendUnauthorizedError(response, "Authentication failed: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String path) {
        return path.startsWith("/api/users/password/reset") || 
               path.startsWith("/auth/login") ||
               path.startsWith("/auth/register");
    }

    private boolean isAdminEndpoint(String path) {
        return path.startsWith("/api/admin");
    }

    private boolean hasAdminRole(List<String> roles) {
        return roles != null && roles.contains("ADMIN");
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private void setAuthenticationInContext(HttpServletRequest request, String username, 
                                          int userId, List<String> roles) {
        // Convert roles to Spring Security authorities with ROLE_ prefix
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        // Log the authorities being set
        logger.debug("Setting authentication with authorities: {}", authorities);

        UsernamePasswordAuthenticationToken authentication = 
            new UsernamePasswordAuthenticationToken(
                username,
                null,
                authorities
            );
        
        authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void sendUnauthorizedError(HttpServletResponse response, String message) throws IOException {
        logger.warn("Unauthorized error: {}", message);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"" + message + "\"}");
    }

    private void sendForbiddenError(HttpServletResponse response, String message) throws IOException {
        logger.warn("Forbidden error: {}", message);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"" + message + "\"}");
    }
}