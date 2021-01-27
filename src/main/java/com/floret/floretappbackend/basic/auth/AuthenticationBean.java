package com.floret.floretappbackend.basic.auth;

public class AuthenticationBean {

	private String token;

	public AuthenticationBean(String token) {
		this.token = token;
	}

	public String getMessage() {
		return token;
	}

	public void setMessage(String token) {
		this.token = token;
	}

}
