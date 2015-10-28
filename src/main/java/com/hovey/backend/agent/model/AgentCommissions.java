package com.hovey.backend.agent.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.hovey.backend.supplier.model.Supplier;
import com.hovey.backend.user.model.HoveyUser;

/*
 * Added By Jeevan on August 19 2013.
 * for storing Agent Commissions.
 */
@Entity
@Table(name="agent_commissions")
public class AgentCommissions implements Serializable{
	
	/**
	 * Added by Jeevan
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	
	@ManyToOne
	@JoinColumn(name="agent_id",referencedColumnName="agent_number")
	private HoveyUser agent;
	
	@OneToOne
	@JoinColumn(name="orderId",referencedColumnName="order_id")
	private Orders order;
	
	@Column(name="commission_rate")
	private Double commissionRate;
	
	@Column(name="agent_commission")
	private Double agentCommission;

	@ManyToOne
	@JoinColumn(name="supplier_name",referencedColumnName="supplier_name")
	private Supplier supplierName;
	
	@Column(name="week")
	private Integer week;
	
	@Column(name="year")
	private Integer year;
	
	@Column(name="commission_payable")
	private Boolean commissionPayable;
	
	
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Boolean getCommissionPayable() {
		return commissionPayable;
	}

	public void setCommissionPayable(Boolean commissionPayable) {
		this.commissionPayable = commissionPayable;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public HoveyUser getAgent() {
		return agent;
	}

	public void setAgent(HoveyUser agent) {
		this.agent = agent;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}

	public Double getAgentCommission() {
		return agentCommission;
	}

	public void setAgentCommission(Double agentCommission) {
		this.agentCommission = agentCommission;
	}

	public Supplier getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(Supplier supplierName) {
		this.supplierName = supplierName;
	}

	
}
