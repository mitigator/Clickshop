package com.user.dto;

public class UserProfileDTO {
	private int userId;
    private String name;
    private String userName;
    private String email;
    private Long phoneNumber;
    private String address;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public UserProfileDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserProfileDTO(int userId, String name, String userName, String email, Long phoneNumber, String address) {
		super();
		this.userId = userId;
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
	@Override
	public String toString() {
		return "UserProfileDTO [userId=" + userId + ", name=" + name + ", userName=" + userName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", address=" + address + "]";
	}
    
    
}
