package com.hovey.backend.user.exception;

public class MailNotSendException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Failed to Send Email";
	}
}
