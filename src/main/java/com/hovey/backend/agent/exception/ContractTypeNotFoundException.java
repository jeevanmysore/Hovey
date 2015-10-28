package com.hovey.backend.agent.exception;

public class ContractTypeNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public String toString(){
		return("ContractType Not Found");
	}
}