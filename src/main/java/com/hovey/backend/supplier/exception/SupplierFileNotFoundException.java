package com.hovey.backend.supplier.exception;

public class SupplierFileNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String toString(){
		return "No Supplier File Found";
	}
}
