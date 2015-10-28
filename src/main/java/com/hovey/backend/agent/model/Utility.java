package com.hovey.backend.agent.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="utility")
public class Utility implements Serializable {

	/**
	 * @author JEEVAN
	 */
	private static final long serialVersionUID = 1L;
     
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="utility",unique=true)
	private String utility;
	
	@Column(name="account_lenght")
	private int accountLenght;
	
	@Column(name="isenabled",nullable=false)
	private boolean isEnabled;
	
	
	
	

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getUtility() {
		return utility;
	}

	public void setUtility(String utility) {
		this.utility = utility;
	}

	public int getAccountLenght() {
		return accountLenght;
	}

	public void setAccountLenght(int accountLenght) {
		this.accountLenght = accountLenght;
		
	}
	
	
	
	
	
	
}
