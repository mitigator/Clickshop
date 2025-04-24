package com.admin.config;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthentication implements Authentication {
    private static final long serialVersionUID = 1L;
    
    private final SimpleUserDetails principal;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean authenticated;
    
    public UserAuthentication(SimpleUserDetails principal, Collection<? extends GrantedAuthority> authorities, boolean authenticated) {
        this.principal = principal;
        this.authorities = authorities;
        this.authenticated = authenticated;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public Object getCredentials() {
        return null;
    }
    
    @Override
    public Object getDetails() {
        return null;
    }
    
    @Override
    public SimpleUserDetails getPrincipal() {
        return principal;
    }
    
    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }
    
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("Cannot change authentication status");
    }
    
    @Override
    public String getName() {
        return principal.getUsername();
    }
}