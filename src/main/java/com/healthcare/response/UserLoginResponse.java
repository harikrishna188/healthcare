package com.healthcare.response;

public class UserLoginResponse {
	
	private String status;
	private String mode;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	@Override
	public String toString() {
		return "UserLoginResponse [status=" + status + ", mode=" + mode + "]";
	}
}
