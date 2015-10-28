package com.hovey.backend.supplier.exception;

public class SupplierNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String toString(){
		return("Supplier Not Found for the given entry");
	}

}
