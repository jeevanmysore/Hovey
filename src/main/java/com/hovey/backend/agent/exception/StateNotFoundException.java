package com.hovey.backend.agent.exception;

public class StateNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public String toString(){
		return("State Not Found");
	}
}
