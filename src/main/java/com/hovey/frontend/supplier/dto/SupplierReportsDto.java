package com.hovey.frontend.supplier.dto;

import java.util.Date;

import com.hovey.backend.supplier.model.SupplierReports;
import com.hovey.frontend.user.dto.HoveyUserDto;

public class SupplierReportsDto {

	/**
	 * @author JEEVAN
	 * 
	 * DTO for Supplier Reports..
	 */
	
	
	private Integer reportId;
	private SupplierDto supplierName;
	private String customerName;
	private String accountNumber;
	private Date contractStartDate;
	private Date contractEndDate;
	private String term;
	private Double rate;
	private Integer kwh;
	private HoveyUserDto agentId;
	private Double commissionRate;
	private Date datePaid;
	private Double totalCommissionPaid;
	/*
	 * Added on July 24,2013 by Jeevan
	 */
	private String fileName;	
	private boolean noAccountinPipelineforSupplierReports;
	
	
	/*
	 * Added on August 12,2013 to display date ranges in reports
	 */
	private Date rstartDate;
	private Date rendDate;
	
	/*
	 * Added on August 28,2013 to know whether the report is updated to pipeline
	 */
	private Boolean updatedPipeline;
	
	
	public Boolean getUpdatedPipeline() {
		return updatedPipeline;
	}
	public void setUpdatedPipeline(Boolean updatedPipeline) {
		this.updatedPipeline = updatedPipeline;
	}
	public Date getRstartDate() {
		return rstartDate;
	}
	public void setRstartDate(Date rstartDate) {
		this.rstartDate = rstartDate;
	}
	
	
	
	public Date getRendDate() {
		return rendDate;
	}
	public void setRendDate(Date rendDate) {
		this.rendDate = rendDate;
	}
	public boolean isNoAccountinPipelineforSupplierReports() {
		return noAccountinPipelineforSupplierReports;
	}
	public void setNoAccountinPipelineforSupplierReports(
			boolean noAccountinPipelineforSupplierReports) {
		this.noAccountinPipelineforSupplierReports = noAccountinPipelineforSupplierReports;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/*
	 * End of modification	 */
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	public SupplierDto getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(SupplierDto supplierName) {
		this.supplierName = supplierName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Date getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public Date getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Integer getKwh() {
		return kwh;
	}
	public void setKwh(Integer kwh) {
		this.kwh = kwh;
	}
	public HoveyUserDto getAgentId() {
		return agentId;
	}
	public void setAgentId(HoveyUserDto agentId) {
		this.agentId = agentId;
	}
	public Double getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}
	public Date getDatePaid() {
		return datePaid;
	}
	public void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}
	public Double getTotalCommissionPaid() {
		return totalCommissionPaid;
	}
	public void setTotalCommissionPaid(Double totalCommissionPaid) {
		this.totalCommissionPaid = totalCommissionPaid;
	}
	
	
	//for populatting reportDto from SupplierReports Model	
	public static SupplierReportsDto populateSupplierReportsDto(SupplierReports report){
		SupplierReportsDto reportDto=new SupplierReportsDto();
		reportDto.setReportId(report.getId());
		reportDto.setAccountNumber(report.getAccountNumber());
		reportDto.setCommissionRate(report.getCommissionRate());
		reportDto.setContractEndDate(report.getContractEndDate());
		reportDto.setContractStartDate(report.getContractStartDate());
		reportDto.setCustomerName(report.getCustomerName());
		reportDto.setDatePaid(report.getUpfrontPaidDate());
		reportDto.setKwh(report.getKwh());
		reportDto.setTerm(report.getTerm());
		reportDto.setRate(report.getRate());
		reportDto.setTotalCommissionPaid(report.getTotalCommissionPaid());
		if(null!=report.getUpdatedPipeline()){
			reportDto.setUpdatedPipeline(report.getUpdatedPipeline());
		}
		SupplierDto supplierDto=SupplierDto.populateSupplier(report.getSupplierName());
		reportDto.setSupplierName(supplierDto);
		return reportDto;		
	}
	
	
}
