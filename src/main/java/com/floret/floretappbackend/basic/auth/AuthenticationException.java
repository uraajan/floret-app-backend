package com.floret.floretappbackend.basic.auth;

public class AuthenticationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4702728193926620808L;

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}
