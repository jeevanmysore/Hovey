package com.hovey.frontend.admin.dto;

public class ChartOrderDataDto {
	
	private String date;
	private Integer totalKwh;
	private Double totalCommission;
	
	
	private Integer totalResKwh;
	private Double totalResCommission;
	
	
	
	
	public Integer getTotalResKwh() {
		return totalResKwh;
	}
	public void setTotalResKwh(Integer totalResKwh) {
		this.totalResKwh = totalResKwh;
	}
	public Double getTotalResCommission() {
		return totalResCommission;
	}
	public void setTotalResCommission(Double totalResCommission) {
		this.totalResCommission = totalResCommission;
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
	public Double getTotalCommission() {
		return totalCommission;
	}
	public void setTotalCommission(Double totalCommission) {
		this.totalCommission = totalCommission;
	}

	
	
	
	
}
