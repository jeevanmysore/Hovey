package com.hovey.backend.admin.dao;

import java.util.ArrayList;

import com.hovey.backend.agent.exception.CustomersNotFoundException;
import com.hovey.backend.agent.model.Customer;
import com.hovey.backend.agent.model.Orders;

public interface CustomerDao {
	
	public int getTotalCustomers()throws Exception;
	public ArrayList<Customer> getCustomers(int pageNo,int pageSize)throws Exception;
	public Customer getCustomerById(Integer customerId)throws CustomersNotFoundException;
	public Integer editCustomer(Customer customer)throws Exception;
	public Integer getTotalOrdersofaCustomer(int customerId)throws Exception;
	public ArrayList<Orders> getOrdersofaCustomer(int customerId)throws Exception;
	public Integer updateOrderswithChangedCustomer(ArrayList<Orders> orders) throws Exception;
	public Integer deleteCustomer(Customer customer)throws Exception;
	public void deleteAllOrdersOfaCustomer(ArrayList<Orders> orders)throws Exception;
	public ArrayList<Customer> getAllCustomers()throws CustomersNotFoundException;
	public ArrayList<Customer> getCustomersFromOrders(int pageNo,int pageSize,String sortBy,String searchBy)throws Exception;
	public int getTotalCustomers(String searchBy)throws Exception;
	
}
