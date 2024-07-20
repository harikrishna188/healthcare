package com.healthcare.response;

public class UserSearchDto {
	
	private Long userId;
	private String fullName;
	private String contactNumber;
	private String userName;
	private String email;
	private String mode;
	private boolean approved;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	@Override
	public String toString() {
		return "UserSearchDto [userId=" + userId + ", fullName=" + fullName + ", contactNumber=" + contactNumber
				+ ", userName=" + userName + ", email=" + email + ", mode=" + mode + ", approved=" + approved + "]";
	}
	
	
}
