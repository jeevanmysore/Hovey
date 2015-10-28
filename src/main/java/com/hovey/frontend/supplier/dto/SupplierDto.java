package com.hovey.frontend.supplier.dto;

import com.hovey.backend.supplier.model.Supplier;


/**
 * 
 * @author JEEVAN
 * 
 * July 02, 2013
 *
 *Dto for Supplier..
 */

public class SupplierDto {

	private Integer supplierId;
	private String supplierName;
	private Double contractCommission;  // for new contracts
	private Double renewalCommission;
	
	//added decleration of uploadKwhFromSupplier ,by bhagya on may 1st,2014
	private Boolean uploadKwhFromSupplier;
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
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
	
	//added by bhagya on may 1st,2014
	public Boolean getUploadKwhFromSupplier() {
		return uploadKwhFromSupplier;
	}
	public void setUploadKwhFromSupplier(Boolean uploadKwhFromSupplier) {
		this.uploadKwhFromSupplier = uploadKwhFromSupplier;
	}
	/*
	 * Populates SupplierDto from the Supplier
	 * added uploadKwhFromSupplier,by bhagya on May1st,2014
	 */
	public static SupplierDto populateSupplier(Supplier supplier){
		SupplierDto supplierDto=new SupplierDto();		
		supplierDto.setSupplierId(supplier.getId());
		supplierDto.setSupplierName(supplier.getSupplierName());
		supplierDto.setContractCommission(supplier.getContractCommission());
		supplierDto.setRenewalCommission(supplier.getRenewalCommission());
		supplierDto.setUploadKwhFromSupplier(supplier.isUploadKwhFromSupplier());
		return supplierDto;
		
	}
	
	
}
