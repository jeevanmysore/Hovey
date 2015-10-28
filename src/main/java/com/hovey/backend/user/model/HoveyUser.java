/* Project      : Hovey Energy 
 * Current File : HoveyUser.java 
 * Created By   : Nnupur Krishnaa on 18-04-2013
 * 
 * Copyright (c) 2012 KNS Technologies, Bangalore. All rights reserved.
 * 
 * Reason       : This is model class that gets created in database. 
 * 
 */
package com.hovey.backend.user.model;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;

import javax.persistence.Table;




@Entity
@Table(name="hovey_user")
public class HoveyUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/*@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;*/
	
	@Id
	@Column(name="userid",unique=true)
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	/*@Column(name="street")
	private String street;*/
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="zip_code",length=5)
	private String zipcode;
	
	@Column(name="agent_number",length=4,unique=true)
	private String agentNumber;
	
	@Column(name="user_type",length=1)
	private char userType;
	
	/*@Column(name="phn_number",unique=true)
	private int phnNumber;*/
	
	@Column(name="email_address",unique=true)
	private String email;
	
	@Column(name="enabled")
	private boolean enabled;
	
	@Column(name="ukey")
	private String ukey;
	
	@Column(name="time_stamp")
	private Date timeStamp;
	
	
	/*
	 * Added on October 28th,2013 as per Clients Need to Change the Welcome Message
	 */
	@Column(name="welcome_message",columnDefinition="TEXT")
	private String welcomeMessage;
	
	

	public String getWelcomeMessage() {
		return welcomeMessage;
	}

	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	/*public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}*/

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAgentNumber() {
		return agentNumber;
	}

	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}

	public char getUserType() {
		return userType;
	}

	public void setUserType(char userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/*public int getPhnNumber() {
		return phnNumber;
	}

	public void setPhnNumber(int phnNumber) {
		this.phnNumber = phnNumber;
	}*/

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUkey() {
		return ukey;
	}

	public void setUkey(String ukey) {
		this.ukey = ukey;
	}
	
	
	
	
}
