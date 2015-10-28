package com.hovey.backend.supplier.model;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 	@author JEEVAN
 *   created on July,02, 2013.
 *   A model for Suppliers
 *   
 *   
 *   
 * **/

@Entity
@Table(name="supplier")
public class Supplier implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	
	@Column(name="supplier_name", unique=true,nullable=false)
	private String supplierName;
	
	@Column(name="contract_commission",unique=false,nullable=false)
	private Double contractCommission;
	
	@Column(name="renewal_commission",unique=false,nullable=false)
	private Double renewalCommission;
	
	// added by bhagya on May 1st ,2014
	@Column(name="uploadKwh_from_supplier",unique=false,nullable=false)
	private Boolean uploadKwhFromSupplier;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Double getContractCommission() {
		return contractCommission;
	}

	public void setContractCommission(Double contractCommission) {
		this.contractCommission = contractCommission;
	}

	public Double getRenewalCommission() {
		return renewalCommission;
	}

	public void setRenewalCommission(Double renewalCommission) {
		this.renewalCommission = renewalCommission;
	}
    // added by bhagya on May 1st ,2014
	public Boolean isUploadKwhFromSupplier() {
		return uploadKwhFromSupplier;
	}

	public void setUploadKwhFromSupplier(Boolean uploadKwhFromSupplier) {
		this.uploadKwhFromSupplier = uploadKwhFromSupplier;
	}
																		
	
	
	
	
}
