package com.hovey.frontend.agent.dto;

import org.springframework.util.AutoPopulatingList;

/**
 * 
 * @author JEEVAN
 *
 */

//for Binding dealSheet.jsp
public class DealSheetForm {
	private CustomerDto customer=new CustomerDto();
   private AutoPopulatingList<OrdersDto> orders =new AutoPopulatingList<OrdersDto>(OrdersDto.class);		
	public AutoPopulatingList<OrdersDto> getOrders() {
	return orders;
}
public void setOrders(AutoPopulatingList<OrdersDto> orders) {
	this.orders = orders;
}
	public CustomerDto getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}
	
	
	
	

}
