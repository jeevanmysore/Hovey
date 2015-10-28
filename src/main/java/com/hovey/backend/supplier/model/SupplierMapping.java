package com.hovey.backend.supplier.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="supplier_mapping")
public class SupplierMapping implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="supplier_name",referencedColumnName="supplier_name",unique=true)
	private Supplier supplierName;
	
	@Column(name="customer_field",nullable=false)
	private String fieldForCustomer;
	
	@Column(name="account_field",nullable=false)
	private String fieldForAccount;
	
	@Column(name="startdate_field",nullable=false)
	private String fieldforStartDate;
	
	@Column(name="endDate_field",nullable=false)
	private String fieldforEndDate;
	
	@Column(name="term_field",nullable=false)
	private String fieldForTerm;

	@Column(name="rate_field")
	private String fieldForRate;

	@Column(name="kwh_field",nullable=false)
	private String fieldForKwh;
	
	@Column(name="agentId_field")
	private String fieldForAgentID;

	@Column(name="commission_rate_field",nullable=false)
	private String fieldForCommissonRate;

	@Column(name="paidDate_field",nullable=false)
	private String fieldForPaidDate;
	
	@Column(name="totalCommission_field",nullable=false)
	private String fieldForTotalCommission;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Supplier getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(Supplier supplierName) {
		this.supplierName = supplierName;
	}

	public String getFieldForCustomer() {
		return fieldForCustomer;
	}

	public void setFieldForCustomer(String fieldForCustomer) {
		this.fieldForCustomer = fieldForCustomer;
	}

	public String getFieldForAccount() {
		return fieldForAccount;
	}

	public void setFieldForAccount(String fieldForAccount) {
		this.fieldForAccount = fieldForAccount;
	}

	public String getFieldforStartDate() {
		return fieldforStartDate;
	}

	public void setFieldforStartDate(String fieldforStartDate) {
		this.fieldforStartDate = fieldforStartDate;
	}

	public String getFieldforEndDate() {
		return fieldforEndDate;
	}

	public void setFieldforEndDate(String fieldforEndDate) {
		this.fieldforEndDate = fieldforEndDate;
	}

	public String getFieldForTerm() {
		return fieldForTerm;
	}

	public void setFieldForTerm(String fieldForTerm) {
		this.fieldForTerm = fieldForTerm;
	}

	public String getFieldForRate() {
		return fieldForRate;
	}

	public void setFieldForRate(String fieldForRate) {
		this.fieldForRate = fieldForRate;
	}

	public String getFieldForKwh() {
		return fieldForKwh;
	}

	public void setFieldForKwh(String fieldForKwh) {
		this.fieldForKwh = fieldForKwh;
	}

	public String getFieldForAgentID() {
		return fieldForAgentID;
	}

	public void setFieldForAgentID(String fieldForAgentID) {
		this.fieldForAgentID = fieldForAgentID;
	}

	public String getFieldForCommissonRate() {
		return fieldForCommissonRate;
	}

	public void setFieldForCommissonRate(String fieldForCommissonRate) {
		this.fieldForCommissonRate = fieldForCommissonRate;
	}

	public String getFieldForPaidDate() {
		return fieldForPaidDate;
	}

	public void setFieldForPaidDate(String fieldForPaidDate) {
		this.fieldForPaidDate = fieldForPaidDate;
	}

	public String getFieldForTotalCommission() {
		return fieldForTotalCommission;
	}

	public void setFieldForTotalCommission(String fieldForTotalCommission) {
		this.fieldForTotalCommission = fieldForTotalCommission;
	}

	
}
