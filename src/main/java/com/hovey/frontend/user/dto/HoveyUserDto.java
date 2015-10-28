/* Project      : Hovey Energy 
 * Current File : HoveyUserDto.java 
 * Created By   : Nnupur Krishnaa on 18-04-2013
 * 
 * Copyright (c) 2012 KNS Technologies, Bangalore. All rights reserved.
 * 
 * Reason       : This is Dto class that contains values same as model class but is for backend use. 
 * 
 */
package com.hovey.frontend.user.dto;


import java.util.Date;

import com.hovey.backend.user.model.HoveyUser;

public class HoveyUserDto {
	
	
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String street;
	private String city;
	private String zipcode;
	private String state;
	private String agentNumber;
	private char userType;
	private String email;
	private boolean enabled;
	private Date timeStamp;
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
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	// Populating Dto from model.
	public static HoveyUserDto populateHoveyUserDto(HoveyUser user){
		HoveyUserDto userDto=new HoveyUserDto();
		userDto.setAgentNumber(user.getAgentNumber());
		userDto.setCity(user.getCity());
		userDto.setEmail(user.getEmail());
		userDto.setEnabled(user.isEnabled());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setPassword(user.getPassword());
		userDto.setState(user.getState());
		//userDto.setStreet(user.getStreet());
		userDto.setUsername(user.getUsername());
		userDto.setUserType(user.getUserType());
		userDto.setZipcode(user.getZipcode());		
		if(null!=user.getTimeStamp()){
			userDto.setTimeStamp(user.getTimeStamp());
		}
		if(null!=user.getWelcomeMessage()){
			userDto.setWelcomeMessage(user.getWelcomeMessage());
		}
		return userDto;
	}
	
	
	

}
