package com.hovey.backend.agent.exception;

public class OrdersExistForAccountException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	public String toString() {
		
		return "MoreThan One Account Exists for Account No";
	}
	
	
	
}
