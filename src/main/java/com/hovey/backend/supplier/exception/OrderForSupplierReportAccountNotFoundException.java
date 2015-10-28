package com.hovey.backend.supplier.exception;

public class OrderForSupplierReportAccountNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String toString(){
		return "No Order found in Pipeline for the Account in Supplier Reports";
	}
}
