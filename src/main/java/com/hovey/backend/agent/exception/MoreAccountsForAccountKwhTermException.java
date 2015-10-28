package com.hovey.backend.agent.exception;

public class MoreAccountsForAccountKwhTermException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		
		return "More than One Account Exists for Account kWh and Term";
	}
	
}
