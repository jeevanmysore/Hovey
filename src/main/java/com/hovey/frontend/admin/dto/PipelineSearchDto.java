package com.hovey.frontend.admin.dto;

import java.util.ArrayList;
import java.util.Date;

import com.hovey.backend.user.model.HoveyUser;

public class PipelineSearchDto {

					
	private Date startDate;
	
	
	private Date endDate;
	
	private String supplierName;
	private String businessName;
	private String contractType;
	private Integer kwh1;
	private Integer kwh2;
	private String agentName;
	private String status;
	private Double commission1;
	private Double commission2;
	private Date sentToSupplier1;
	private Date sentToSupplier2;
	private Date DealStartDate1;
	private Date DealStartDate2;
	private Double upfrontCommission1;
	private Double upfrontCommission2;
	private Date upfrontPaidDate1;
	private Date upfrontPaidDate2;
	private Integer term1;
	private Integer term2;
	private String notes;
	private ArrayList<HoveyUser> agents;
	private String accountNumber;
	//added resAgent, by bhagya on april 30,2014
	private ArrayList<HoveyUser> resAgents;
	private String resAgentName;
	//added serviceType by bhagya on december 12,2014.as per clients need
	private String serviceType;
	
/* **********************Modiified by Jeevan on July 18,2013 to add States and Utility for Dynamic Search ****/	
	private String utility;
	private String state;	
	
	/* ******************Added on October 16,2013**************************/
	private String commissionStatus;
	
	
	public String getCommissionStatus() {
		return commissionStatus;
	}
	public void setCommissionStatus(String commissionStatus) {
		this.commissionStatus = commissionStatus;
	}
	
	
	public String getUtility() {
		return utility;
	}
	public void setUtility(String utility) {
		this.utility = utility;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
/* ****End of Modification on July 18,2013.. *********/
	public Date getSentToSupplier1() {
		return sentToSupplier1;
	}
	public void setSentToSupplier1(Date sentToSupplier1) {
		this.sentToSupplier1 = sentToSupplier1;
	}
	public Date getSentToSupplier2() {
		return sentToSupplier2;
	}
	public void setSentToSupplier2(Date sentToSupplier2) {
		this.sentToSupplier2 = sentToSupplier2;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public ArrayList<HoveyUser> getAgents() {
		return agents;
	}
	public void setAgents(ArrayList<HoveyUser> agents) {
		this.agents = agents;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public Integer getKwh1() {
		return kwh1;
	}
	public void setKwh1(Integer kwh1) {
		this.kwh1 = kwh1;
	}
	public Integer getKwh2() {
		return kwh2;
	}
	public void setKwh2(Integer kwh2) {
		this.kwh2 = kwh2;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getCommission1() {
		return commission1;
	}
	public void setCommission1(Double commission1) {
		this.commission1 = commission1;
	}
	public Double getCommission2() {
		return commission2;
	}
	public void setCommission2(Double commission2) {
		this.commission2 = commission2;
	}
	
	public Date getDealStartDate1() {
		return DealStartDate1;
	}
	public void setDealStartDate1(Date dealStartDate1) {
		DealStartDate1 = dealStartDate1;
	}
	public Date getDealStartDate2() {
		return DealStartDate2;
	}
	public void setDealStartDate2(Date dealStartDate2) {
		DealStartDate2 = dealStartDate2;
	}
	public Double getUpfrontCommission1() {
		return upfrontCommission1;
	}
	public void setUpfrontCommission1(Double upfrontCommission1) {
		this.upfrontCommission1 = upfrontCommission1;
	}
	public Double getUpfrontCommission2() {
		return upfrontCommission2;
	}
	public void setUpfrontCommission2(Double upfrontCommission2) {
		this.upfrontCommission2 = upfrontCommission2;
	}
	public Date getUpfrontPaidDate1() {
		return upfrontPaidDate1;
	}
	public void setUpfrontPaidDate1(Date upfrontPaidDate1) {
		this.upfrontPaidDate1 = upfrontPaidDate1;
	}
	public Date getUpfrontPaidDate2() {
		return upfrontPaidDate2;
	}
	public void setUpfrontPaidDate2(Date upfrontPaidDate2) {
		this.upfrontPaidDate2 = upfrontPaidDate2;
	}
	public Integer getTerm1() {
		return term1;
	}
	public void setTerm1(Integer term1) {
		this.term1 = term1;
	}
	public Integer getTerm2() {
		return term2;
	}
	public void setTerm2(Integer term2) {
		this.term2 = term2;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	// added resAgent ,by bhagya on April 30,2014
	public ArrayList<HoveyUser> getResAgents() {
		return resAgents;
	}
	public void setResAgents(ArrayList<HoveyUser> resAgents) {
		this.resAgents = resAgents;
	}
	public String getResAgentName() {
		return resAgentName;
	}
	public void setResAgentName(String resAgentName) {
		this.resAgentName = resAgentName;
	}
	// added getter and setter for service type by bhagya on december 11,2014
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	
	
}
