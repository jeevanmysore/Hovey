package com.hovey.backend.agent.exception;

public class AgentNotFoundException extends Exception{

	/**
	 * @author JEEVAN
	 * Created to handle conditions where no Agents Registered..
	 */
	private static final long serialVersionUID = 1L;

	public String toString(){
		return("Agents Not Found");
	}
}
