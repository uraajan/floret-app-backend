package com.floret.floretappbackend.basic.auth;

import java.io.Serializable;

public class BasicAuthResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7909472865592483818L;

	private String token;

	public BasicAuthResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

}
