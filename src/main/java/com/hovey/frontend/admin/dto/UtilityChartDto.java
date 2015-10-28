package com.hovey.frontend.admin.dto;

/**
 * 
 * @author Jeevan
 *
 *Utility chart Dto.. Used for transfereing Utility piue Chart data among presentation layer and Controller..
 */
public class UtilityChartDto {

	private String property;
	private Integer noOfOrders;
	
	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Integer getNoOfOrders() {
		return noOfOrders;
	}
	public void setNoOfOrders(Integer noOfOrders) {
		this.noOfOrders = noOfOrders;
	}

	
	
	
}
