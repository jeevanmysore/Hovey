package com.hovey.frontend.agent.dto;

/*
 * 
 * 
 * Created by Jeevan on July 19, 2013.
 * 
 * Purpose: It is a Dto for Handling Existing Customers in Deal sheet Page..
 */

public class CustomerSearchDto {
	
	private String firstName;
	private String lastName;
	private String taxId;
	private String businessName;
	private String phoneNo;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
