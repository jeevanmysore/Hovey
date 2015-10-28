package com.hovey.backend.agent.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author JEEVAN
 * Created by Jeevan on Aug 19, 2013 ..
 * Model to store Rescinded Orders.........
 *
 */

@Entity
@Table(name="rescinded_orders")
public class RescindedOrders implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="order_id",referencedColumnName="order_id",unique=true,nullable=false)
	private Orders orderId;
	
	@Column(name="refund_status")
	private Boolean refundStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Orders getOrderId() {
		return orderId;
	}

	public void setOrderId(Orders orderId) {
		this.orderId = orderId;
	}

	public Boolean getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Boolean refundStatus) {
		this.refundStatus = refundStatus;
	}
	
	
	

}
