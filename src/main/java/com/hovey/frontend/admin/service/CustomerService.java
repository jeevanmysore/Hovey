package com.hovey.frontend.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.hovey.backend.agent.exception.CustomersNotFoundException;
import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.agent.model.Orders;
import com.hovey.frontend.agent.dto.CustomerDto;
import com.hovey.frontend.agent.dto.OrdersDto;

public interface CustomerService {
	public int findTotalNoOfCustomerPages(int pageSize,String searchBy) throws Exception;
	public ArrayList<CustomerDto> getCustomersFromDb(int pageNo,int pageSize,String sortBy,String searchBy)throws Exception;
	public CustomerDto getCustomerByIdFromDAO(Integer customerId)throws CustomersNotFoundException;
	public Integer editCustomerinDao(CustomerDto customerDto) throws Exception;
	public int getTotlaOrdersOfaCustomerFromDAO(int customerId)throws Exception;
	public ArrayList<OrdersDto> getOrdersOfaCustomerFromDAO(int customerId)throws Exception;
	public int updateOrderswithChangeCustomersinDAO(int customerId,String taxId)throws Exception;
	public int deleteCustomersnOrders(int customerId)throws Exception;
	public Map<String, Object> getCommissionsOfaCustomerById(Integer customerId)throws Exception;
	public int getTotalCustomers()throws Exception;
	public int getTotalCustomers(String searchBy)throws Exception;
	public ArrayList<OrdersDto> getOrdersOfaCustomer(ArrayList<CustomerDto> customerDtos)throws OrderNotFoundException,CustomersNotFoundException;
	// for upfront commissions and paid dates
	public OrdersDto populateOrderDto(Orders order);
	public OrdersDto getReconcileOrdersByOrderId(int orderId)throws Exception;
	public Integer saveorReconcileCommissions(Double upfrontCommission,Double upfrontCommission2,Double upfrontCommission3,Date upDate1,
			Date upDate2,Date upDate3,Integer orderId )throws Exception;
}
