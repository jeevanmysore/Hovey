package com.hovey.backend.agent.exception;

public class CustomersNotFoundException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String toString(){
		return("Customers Not Found for the Current Agent");
	}
	

}
