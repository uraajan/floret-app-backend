package com.floret.floretappbackend.basic.exception;

public class DuplicateUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3905889574192981546L;

	public DuplicateUserException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
