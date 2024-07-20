package com.healthcare.response;

import com.healthcare.domain.User;

public class PasswordResponse {
	
	private String response;
	private User user;
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "PasswordResponse [response=" + response + ", user=" + user + "]";
	}
	
	
	

}
