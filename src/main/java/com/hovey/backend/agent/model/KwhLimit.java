package com.hovey.backend.agent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="kwh_limit")
public class KwhLimit {

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="kwh_limit")
	private Integer kWhLimit;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getkWhLimit() {
		return kWhLimit;
	}

	public void setkWhLimit(Integer kWhLimit) {
		this.kWhLimit = kWhLimit;
	}
	
	
	
	
}
