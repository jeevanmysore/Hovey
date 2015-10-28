package com.hovey.frontend.agent.dto;

import com.hovey.backend.agent.model.RescindedOrders;

/*
 * Created b Jeevan. on Aug 19,2013..
 * Dtoi for Rescinded Accounts..
 */
public class RescindedOrdersDto {

	
	private Integer id;
	private OrdersDto orderId;
	private Boolean refundStatus;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public OrdersDto getOrderId() {
		return orderId;
	}
	public void setOrderId(OrdersDto orderId) {
		this.orderId = orderId;
	}
	public Boolean getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(Boolean refundStatus) {
		this.refundStatus = refundStatus;
	}
	
	
	public static RescindedOrdersDto populateRescindedOrder(RescindedOrders orders){
		RescindedOrdersDto orderDto=new RescindedOrdersDto();
		orderDto.setId(orders.getId());
		orderDto.setRefundStatus(orders.getRefundStatus());
		OrdersDto ordersDto=OrdersDto.populateOrderDto(orders.getOrderId());
		orderDto.setOrderId(ordersDto);
		return orderDto;
	}
}
