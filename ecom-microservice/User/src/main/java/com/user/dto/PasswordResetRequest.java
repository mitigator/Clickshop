package com.user.dto;

public class PasswordResetRequest {
	private String email;
    private String oldPassword;
    private String newPassword;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public PasswordResetRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PasswordResetRequest(String email, String oldPassword, String newPassword) {
		super();
		this.email = email;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	@Override
	public String toString() {
		return "PasswordResetRequest [email=" + email + ", oldPassword=" + oldPassword + ", newPassword=" + newPassword
				+ "]";
	}
    
    

}
