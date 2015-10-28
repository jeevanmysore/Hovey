package com.hovey.frontend.admin.dto;

/**
 * 
 * @author JEEVAN
 * 
 * DTO for Line Chart
 *
 */

public class LineChartDto {

	private String date;
	private Integer totalKwh;
	private Integer lastYearTotalKwh;
	
	private Double totalCommission;
	private Double lastYearTotalCommission;
	
	public Double getTotalCommission() {
		return totalCommission;
	}
	public void setTotalCommission(Double totalCommission) {
		this.totalCommission = totalCommission;
	}
	public Double getLastYearTotalCommission() {
		return lastYearTotalCommission;
	}
	public void setLastYearTotalCommission(Double lastYearTotalCommission) {
		this.lastYearTotalCommission = lastYearTotalCommission;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getTotalKwh() {
		return totalKwh;
	}
	public void setTotalKwh(Integer totalKwh) {
		this.totalKwh = totalKwh;
	}
	public Integer getLastYearTotalKwh() {
		return lastYearTotalKwh;
	}
	public void setLastYearTotalKwh(Integer lastYearTotalKwh) {
		this.lastYearTotalKwh = lastYearTotalKwh;
	}
	
	
	
	
}
