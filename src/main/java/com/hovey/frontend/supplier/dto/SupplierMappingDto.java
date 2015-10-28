package com.hovey.frontend.supplier.dto;

import com.hovey.backend.supplier.model.SupplierMapping;

/**
 * 
 * @author JEEVAN.
 * 
 * Dto for Supplier Mappings...
 * 
 *
 */


public class SupplierMappingDto {
	
	private Integer mappingId;
	private SupplierDto supplierName;
	private String fieldForCustomer;
	private String fieldForAccount;
	private String fieldForStartDate;
	private String fieldForEndDate;
	private String fieldForTerm;
	private String fieldForKwh;
	private String fieldForRate;
	private String fieldForCommissionRate;
	private String fieldForAgemtId;
	private String fieldForPaidDate;
	private String fieldForTotalCommissionPaid;
	public Integer getMappingId() {
		return mappingId;
	}
	public void setMappingId(Integer mappingId) {
		this.mappingId = mappingId;
	}
	public SupplierDto getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(SupplierDto supplierName) {
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
	public String getFieldForStartDate() {
		return fieldForStartDate;
	}
	public void setFieldForStartDate(String fieldForStartDate) {
		this.fieldForStartDate = fieldForStartDate;
	}
	public String getFieldForEndDate() {
		return fieldForEndDate;
	}
	public void setFieldForEndDate(String fieldForEndDate) {
		this.fieldForEndDate = fieldForEndDate;
	}
	public String getFieldForTerm() {
		return fieldForTerm;
	}
	public void setFieldForTerm(String fieldForTerm) {
		this.fieldForTerm = fieldForTerm;
	}
	public String getFieldForKwh() {
		return fieldForKwh;
	}
	public void setFieldForKwh(String fieldForKwh) {
		this.fieldForKwh = fieldForKwh;
	}
	public String getFieldForRate() {
		return fieldForRate;
	}
	public void setFieldForRate(String fieldForRate) {
		this.fieldForRate = fieldForRate;
	}
	public String getFieldForCommissionRate() {
		return fieldForCommissionRate;
	}
	public void setFieldForCommissionRate(String fieldForCommissionRate) {
		this.fieldForCommissionRate = fieldForCommissionRate;
	}
	public String getFieldForAgemtId() {
		return fieldForAgemtId;
	}
	public void setFieldForAgemtId(String fieldForAgemtId) {
		this.fieldForAgemtId = fieldForAgemtId;
	}
	public String getFieldForPaidDate() {
		return fieldForPaidDate;
	}
	public void setFieldForPaidDate(String fieldForPaidDate) {
		this.fieldForPaidDate = fieldForPaidDate;
	}
	public String getFieldForTotalCommissionPaid() {
		return fieldForTotalCommissionPaid;
	}
	public void setFieldForTotalCommissionPaid(String fieldForTotalCommissionPaid) {
		this.fieldForTotalCommissionPaid = fieldForTotalCommissionPaid;
	}
	
	
	
	//populating from model to DTO
	
	public static SupplierMappingDto populateSupplierMappingDto(SupplierMapping mapping){
			SupplierMappingDto mappingDto=new SupplierMappingDto();
			mappingDto.setMappingId(mapping.getId());
			SupplierDto supplier=SupplierDto.populateSupplier(mapping.getSupplierName());
			mappingDto.setSupplierName(supplier);
			mappingDto.setFieldForAccount(mapping.getFieldForAccount());
			mappingDto.setFieldForAgemtId(mapping.getFieldForAgentID());
			mappingDto.setFieldForCommissionRate(mapping.getFieldForCommissonRate());
			mappingDto.setFieldForCustomer(mapping.getFieldForCustomer());
			mappingDto.setFieldForEndDate(mapping.getFieldforEndDate());
			mappingDto.setFieldForKwh(mapping.getFieldForKwh());
			mappingDto.setFieldForPaidDate(mapping.getFieldForPaidDate());
			mappingDto.setFieldForRate(mapping.getFieldForRate());
			mappingDto.setFieldForTotalCommissionPaid(mapping.getFieldForTotalCommission());
			mappingDto.setFieldForTerm(mapping.getFieldForTerm());
			mappingDto.setFieldForStartDate(mapping.getFieldforStartDate());			
			return mappingDto;
			
	}

}
