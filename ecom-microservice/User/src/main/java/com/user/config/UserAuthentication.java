package com.user.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthentication implements Authentication {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final SimpleUserDetails principal;
	private final boolean authenticated;

	public UserAuthentication(SimpleUserDetails principal, boolean authenticated) {
		this.principal = principal;
		this.authenticated = authenticated;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
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
