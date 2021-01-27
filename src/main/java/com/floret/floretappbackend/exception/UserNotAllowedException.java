package com.floret.floretappbackend.exception;

public class UserNotAllowedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1336735616837633194L;

	public UserNotAllowedException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
