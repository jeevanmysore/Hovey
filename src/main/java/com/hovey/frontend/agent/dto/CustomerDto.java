package com.hovey.frontend.agent.dto;



import org.apache.commons.lang.WordUtils;

import com.hovey.backend.agent.model.Customer;

public class CustomerDto {
	
	private int customerId;
	private String firstName;
	private String lastName;
	private String title;
	private String taxId;
	private String phoneNo;
	private String email;
	private String faxNo;
	private String taxExempt;	
	private String type;
	private String fronter;
	
	private Double totalExpectedCommission;
	private Double totalCommissionReceived;
	
	//added by Jeevan on May 39, 2014
	private Double totalTermCommission;
	
	
	private Integer totalAccounts;
	//added on August 28,2013 as per client need to display Business Name along with Customer ?name
	private String businessName;
	
	
	
	
	
	
	
	public Double getTotalTermCommission() {
		return totalTermCommission;
	}

	public void setTotalTermCommission(Double totalTermCommission) {
		this.totalTermCommission = totalTermCommission;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getTotalAccounts() {
		return totalAccounts;
	}

	public void setTotalAccounts(Integer totalAccounts) {
		this.totalAccounts = totalAccounts;
	}

	public Double getTotalExpectedCommission() {
		return totalExpectedCommission;
	}

	public void setTotalExpectedCommission(Double totalExpectedCommission) {
		this.totalExpectedCommission = totalExpectedCommission;
	}

	public Double getTotalCommissionReceived() {
		return totalCommissionReceived;
	}

	public void setTotalCommissionReceived(Double totalCommissionReceived) {
		this.totalCommissionReceived = totalCommissionReceived;
	}

	public String getFronter() {
		return fronter;
	}

	public void setFronter(String fronter) {
		this.fronter = fronter;
	}

	public int getCustomerId() {
		return customerId;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	
	
	
	
	
	
	
	
	
	public String getTaxExempt() {
		return taxExempt;
	}
	public void setTaxExempt(String taxExempt) {
		this.taxExempt = taxExempt;
	}
	public static CustomerDto populateCustomerDto(Customer customer){
		CustomerDto customerDto=new CustomerDto();
		customerDto.setCustomerId(customer.getCustomerId());
		customerDto.setEmail(customer.getEmail());
		customerDto.setFaxNo(customer.getFaxNo());
		customerDto.setFirstName(WordUtils.capitalize(customer.getFirstName()));
		customerDto.setLastName(WordUtils.capitalize(customer.getLastName()));
		customerDto.setPhoneNo(customer.getPhoneNo());
		customerDto.setFronter(WordUtils.capitalize(customer.getFronter()));
		customerDto.setType(customer.getType());
		if(null!=customer.getTaxExempt()){
			if(customer.getTaxExempt()==true)
				customerDto.setTaxExempt("true");
			else if(customer.getTaxExempt()==false)
				customerDto.setTaxExempt("false");
		}
		customerDto.setTaxId(customer.getTaxId());
		customerDto.setTitle(WordUtils.capitalize(customer.getTitle()));
		/*HoveyUserDto userDto=HoveyUserDto.populateHoveyUserDto(customer.getAgent());
		customerDto.setHoveyUserDto(userDto);*/
		
		return customerDto;
	}
	

}
