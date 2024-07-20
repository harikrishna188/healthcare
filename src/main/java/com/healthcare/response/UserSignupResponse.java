package com.healthcare.response;

public class UserSignupResponse {
	
	private boolean status;
	private String statusText;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	@Override
	public String toString() {
		return "UserSignupResponse [status=" + status + ", statusText=" + statusText + "]";
	}
	

}
