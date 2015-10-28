package com.hovey.backend.agent.exception;

public class OrderNotFoundException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String toString(){
		return("Orders Not Found for the Current Agent");
	}
	

}
