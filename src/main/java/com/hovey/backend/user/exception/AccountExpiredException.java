package com.hovey.backend.user.exception;


public class AccountExpiredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Unique Key Expired";
	}
	
	
}
