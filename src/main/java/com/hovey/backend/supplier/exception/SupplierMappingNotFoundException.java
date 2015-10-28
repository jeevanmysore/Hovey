package com.hovey.backend.supplier.exception;

public class SupplierMappingNotFoundException extends Exception {

	
private static final long serialVersionUID = 1L;
	
	public String toString(){
		return("SupplierMapping Not Found for the given entry");
	}
}
