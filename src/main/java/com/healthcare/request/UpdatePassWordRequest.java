package com.healthcare.request;

public class UpdatePassWordRequest {
	
	private String userName;
	private String newPassword;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	@Override
	public String toString() {
		return "UpdatePassWordRequest [userName=" + userName + ", newPassword=" + newPassword + "]";
	}
	
}
