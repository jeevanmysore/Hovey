package com.hovey.backend.agent.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;



@Entity
@Table(name="customer")
public class Customer implements Serializable {

	/**
	 * @author JEEVAN
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="customer_id")
	private Integer customerId;
	
	/*@Column(name="contact_name",unique=true,nullable=false)
	private String name;*/
	
	@Column(name="first_name", nullable=false)
	private String firstName;
	
	@Column(name="last_name",nullable=false)
	private String lastName;
	
	
	
	@Column(name="customer_title",nullable=false)
	private String title;
	
	@Column(name="tax_id",length=9)
	private String taxId;
	
	@Column(name="phone_no",nullable=false,length=11)	
	private String phoneNo;
	
	@Column(name="email_address",nullable=false)
	private String email;
	
	@Column(name="fax_no",nullable=false,length=11)
	private String faxNo;
	
	@Column(name="tax_exempt")
	private Boolean taxExempt;
	
	
	
	@Column(name="type")
	private String type;
	
	@Column(name="fronter")
	private String fronter;
	
	
	
	
	/*@ManyToOne
	@JoinColumn(name="created_agent_id",referencedColumnName="agent_number",nullable=false)
	private HoveyUser agent;*/


	public String getFronter() {
		return fronter;
	}

	public void setFronter(String fronter) {
		this.fronter = fronter;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
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

	
	public Boolean getTaxExempt() {
		return taxExempt;
	}

	public void setTaxExempt(Boolean taxExempt) {
		this.taxExempt = taxExempt;
	}

	/*public HoveyUser getAgent() {
		return agent;
	}

	public void setAgent(HoveyUser agent) {
		this.agent = agent;
	}*/
	
	
}
