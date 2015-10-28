package com.hovey.frontend.agent.dto;

import com.hovey.backend.agent.model.AgentCommissions;
import com.hovey.frontend.supplier.dto.SupplierDto;
import com.hovey.frontend.user.dto.HoveyUserDto;

/**
 * 
 * @author JEEVAN
 * DTO for Agent Commission
 *
 */
public class AgentCommissionsDto {

	private Integer id;
	private HoveyUserDto agent;
	private OrdersDto order;
	private Double commissionRate;
	private Double agentCommission;
	private SupplierDto supplier;
	private Integer week;
	private boolean commissionPayable;
	private Long totalKwh;
	private Integer year;
	
	
	
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Long getTotalKwh() {
		return totalKwh;
	}
	public void setTotalKwh(Long totalKwh) {
		this.totalKwh = totalKwh;
	}
	public boolean isCommissionPayable() {
		return commissionPayable;
	}
	public void setCommissionPayable(boolean commissionPayable) {
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
	public HoveyUserDto getAgent() {
		return agent;
	}
	public void setAgent(HoveyUserDto agent) {
		this.agent = agent;
	}
	public OrdersDto getOrder() {
		return order;
	}
	public void setOrder(OrdersDto order) {
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
	public SupplierDto getSupplier() {
		return supplier;
	}
	public void setSupplier(SupplierDto supplier) {
		this.supplier = supplier;
	}
	
	
	public static AgentCommissionsDto populateAgentCommissions(AgentCommissions commissions){
		AgentCommissionsDto commissionsDto=new AgentCommissionsDto();
		commissionsDto.setId(commissions.getId());
		commissionsDto.setCommissionRate(commissions.getCommissionRate());
		commissionsDto.setAgentCommission(commissions.getAgentCommission());
		commissionsDto.setAgent(HoveyUserDto.populateHoveyUserDto(commissions.getAgent()));
		commissionsDto.setOrder(OrdersDto.populateOrderDto(commissions.getOrder()));
		commissionsDto.setSupplier(SupplierDto.populateSupplier(commissions.getSupplierName()));
		commissionsDto.setWeek(commissions.getWeek());
		if(null!=commissions.getCommissionPayable()){
			commissionsDto.setCommissionPayable(commissions.getCommissionPayable());
		}
		commissionsDto.setYear(commissions.getYear());
		return commissionsDto;
	}
}
