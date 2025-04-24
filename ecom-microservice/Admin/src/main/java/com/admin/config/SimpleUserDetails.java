package com.admin.config;

public class SimpleUserDetails {
	private String username;
    private int userId;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public SimpleUserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SimpleUserDetails(String username, int userId) {
		super();
		this.username = username;
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "SimpleUserDetails [username=" + username + ", userId=" + userId + "]";
	}
    

}
