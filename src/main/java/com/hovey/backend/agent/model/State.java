package com.hovey.backend.agent.model;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name="state")
public class State implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	
	@Column(name="state",unique=true)
	private String state;
	
/*	@OneToMany(mappedBy="serviceState")
	private Set<Orders> serviceState;
	
	
	@OneToMany(mappedBy="billingState")
	private Set<Orders> billingState;
	
	

	
	
	public Set<Orders> getServiceState() {
		return serviceState;
	}

	public void setServiceState(Set<Orders> serviceState) {
		this.serviceState = serviceState;
	}

	public Set<Orders> getBillingState() {
		return billingState;
	}

	public void setBillingState(Set<Orders> billingState) {
		this.billingState = billingState;
	}*/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
	
}
