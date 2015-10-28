package com.hovey.backend.user.exception;

public class HoveyUserNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		
		return ("Hovey User Not Found");
	}
	
}
