package com.auth.config;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {
    private final UserDetails principal;
    private String credentials;
    private int userId;  // Additional user information
    private String role; // Additional role information

    // Constructor for authenticated requests
    public JWTAuthenticationToken(UserDetails principal, 
                                Collection<? extends GrantedAuthority> authorities,
                                String credentials,
                                int userId,
                                String role) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.userId = userId;
        this.role = role;
        super.setAuthenticated(true); // Mark as authenticated
    }

    // Constructor for unauthenticated requests
    public JWTAuthenticationToken(String credentials) {
        super(null);
        this.principal = null;
        this.credentials = credentials;
        this.userId = -1;
        this.role = null;
        super.setAuthenticated(false); // Mark as unauthenticated
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public int getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}