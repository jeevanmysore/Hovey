package com.hovey.frontend.admin.dto;

import java.util.Date;

import com.hovey.frontend.user.dto.HoveyUserDto;

/*
 * Created by Jeevan on August 09,2013.
 * 
 * Dto to Store Order related info of an Agent..
 * 
 */

public class AgentOrderDto {

	
	private HoveyUserDto agentNumber;
	private String agentName;
	private Integer totalKwh;
	private Integer totalOrders;
	private Double totalExpectedCommission;
	private Double totalReceivedCommission;
	private Date rstartDate;
	private Date rendDate;
	private String accountNumber;
	//added by bhagya on april 14th 2014
	private String orderStatus;
	private Date sentToSupplier;
	
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Integer getTotalOrders() {
		return totalOrders;
	}
	public void setTotalOrders(Integer totalOrders) {
		this.totalOrders = totalOrders;
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
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	public HoveyUserDto getAgentNumber() {
		return agentNumber;
	}
	public void setAgentNumber(HoveyUserDto agentNumber) {
		this.agentNumber = agentNumber;
	}
	public Integer getTotalKwh() {
		return totalKwh;
	}
	public void setTotalKwh(Integer totalKwh) {
		this.totalKwh = totalKwh;
	}
	public Double getTotalExpectedCommission() {
		return totalExpectedCommission;
	}
	public void setTotalExpectedCommission(Double totalExpectedCommission) {
		this.totalExpectedCommission = totalExpectedCommission;
	}
	public Double getTotalReceivedCommission() {
		return totalReceivedCommission;
	}
	public void setTotalReceivedCommission(Double totalReceivedCommission) {
		this.totalReceivedCommission = totalReceivedCommission;
	}
	/*added from bhagya
	 * on april 14th 2014*/
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public Date getSentToSupplier() {
		return sentToSupplier;
	}
	public void setSentToSupplier(Date sentToSupplier) {
		this.sentToSupplier = sentToSupplier;
	}
	
	
}
