package com.hovey.backend.supplier.exception;

//for Handling Supplier Files if they already Exists
public class SupplierFileAlreadyExistsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String toString(){
		return "Supplier File Already Uploaded";
	}
}
