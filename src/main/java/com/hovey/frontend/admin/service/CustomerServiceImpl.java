package com.hovey.frontend.admin.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hovey.backend.admin.dao.CustomerDao;
import com.hovey.backend.agent.dao.DealSheetDao;
import com.hovey.backend.agent.exception.CustomersNotFoundException;
import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.agent.model.Customer;
import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.supplier.model.Supplier;
import com.hovey.backend.supplier.model.SupplierMapping;
import com.hovey.frontend.agent.dto.CustomerDto;
import com.hovey.frontend.agent.dto.OrdersDto;
import com.hovey.frontend.supplier.dto.SupplierMappingDto;

@Service("customerService")
public class CustomerServiceImpl  implements CustomerService{

	
	private static Logger log=Logger.getLogger(CustomerServiceImpl.class);
	
	@Resource(name="customerDao")
	private CustomerDao customerDao;
	
	@Resource(name="dealSheetDao")
	private DealSheetDao dealSheetDao;
	 //added by bhagya on may 26th,2014
	@Resource(name="adminService")
	private AdminService adminService;
	
	
	
	public int getTotalCustomers()throws Exception{
		log.info("inside getTotalCustomers()");
		int totalCustomers=this.customerDao.getTotalCustomers();
		return totalCustomers;
	}
	
	public int getTotalCustomers(String searchBy)throws Exception{
		log.info("inside getTotalCustomers()");
		int totalCustomers;
		if(null!=searchBy || searchBy==""){
			totalCustomers=this.customerDao.getTotalCustomers(searchBy);
		}
		else{
			totalCustomers=this.customerDao.getTotalCustomers();
		}
		
		return totalCustomers;
	}
	
	
	
	//returns total no of Customers..
		public int findTotalNoOfCustomerPages(int pageSize,String searchBy) throws Exception{
			log.info("inside findTotalNoOfCustomers()");
			int totalRecords;
			if(null!=searchBy || searchBy==""){
				totalRecords=this.customerDao.getTotalCustomers(searchBy);
			}
			else{
				totalRecords=this.customerDao.getTotalCustomers();
			}
			int result=totalRecords/pageSize;
			int pagesNeeded;
			if(totalRecords%pageSize>0){
				pagesNeeded=result+1;
			}
			else{
				pagesNeeded=result;
			}
			return pagesNeeded;
		}
		
	
	//gets all the Customers from DAO
	public ArrayList<CustomerDto> getCustomersFromDb(int pageNo,int pageSize,String sortBy,String searchBy)throws Exception{
		log.info("inside getCustomersFromDB()");
		//ArrayList<Customer> customers=this.customerDao.getCustomers(pageNo,pageSize);
		ArrayList<Customer> customers=this.customerDao.getCustomersFromOrders(pageNo, pageSize,sortBy,searchBy);
		ArrayList<CustomerDto> customerDtos=new  ArrayList<CustomerDto>();
		for(Customer customer:customers){			
				CustomerDto customerDto=CustomerDto.populateCustomerDto(customer);
				customerDtos.add(customerDto);			
		}			
		return customerDtos;
	}
	
	
	
	//gets Customer by Id
	public CustomerDto getCustomerByIdFromDAO(Integer customerId)throws CustomersNotFoundException{
		log.info("inside getCustomerByIdfromDAO()");
		Customer customer =this.customerDao.getCustomerById(customerId);
		CustomerDto customerDto=CustomerDto.populateCustomerDto(customer);
		return customerDto;
	}
	
	// Editing Customer
	public Integer editCustomerinDao(CustomerDto customerDto) throws Exception{
		log.info("inside editCustomerinDAO()");
		Customer customer=new Customer();
		customer.setCustomerId(customerDto.getCustomerId());
		customer.setEmail(customerDto.getEmail());
		customer.setFaxNo(customerDto.getFaxNo());
		customer.setFirstName(customerDto.getFirstName());
		customer.setLastName(customerDto.getLastName());
		customer.setPhoneNo(customerDto.getPhoneNo());
		
		customer.setTitle(customerDto.getTitle());
		customer.setType(customerDto.getType());
		if(customerDto.getTaxExempt().equalsIgnoreCase("yes")){
	    	customer.setTaxExempt(true);
	    }
	    else if(customerDto.getTaxExempt().equalsIgnoreCase("no")){
	    	customer.setTaxExempt(false);
	    }
		
		if(null!=customerDto.getTaxId() && customerDto.getTaxId()!=""){
			customer.setTaxId(customerDto.getTaxId());
		}
		int result=this.customerDao.editCustomer(customer);
		return result;
	}
	
	
	
	
	/*
	 * Added by Jeevan on August 26,2013..
	 * 
	 * Method to Retreive Business Names of All Customers..
	 * 
	 * Workflow:
	 * 1. Get All Customers...
	 * 2. Get Orders Of Each Customers.
	 * 3. Populate OrdersDto with First Orders of Each Customer..
	 */
	
	public ArrayList<OrdersDto> getOrdersOfaCustomer(ArrayList<CustomerDto> customerDtos)throws OrderNotFoundException,CustomersNotFoundException{
		log.info("inside getOrdersOfaCustomer()");
		ArrayList<Customer> customers=new ArrayList<Customer>();
		for(CustomerDto customerDto:customerDtos){
			Customer customer=this.customerDao.getCustomerById(customerDto.getCustomerId());
			customers.add(customer);
		}
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		for(Customer customer:customers){
			try{
				ArrayList<Orders> orders=this.dealSheetDao.getOrdersofCustomers(customer);
				OrdersDto orderDto=OrdersDto.populateOrderDto(orders.get(0));
				orderDtos.add(orderDto);
			}
			catch(Exception e){
				log.error("No orders Exists for Customer "+customer.getCustomerId());
			}			
		}	

		/*Collections.sort(orderDtos, new Comparator<OrdersDto>() {

			@Override
			public int compare(OrdersDto o1, OrdersDto o2) {
				return o1.getId().compareTo(o2.getId());
			}		
			
		});*/
		return orderDtos;		
	}
	
	
	
	
	
/* *******FOR DELETION ************************/	
	
	//getTotalOrdersofa Customer from Dao
	public int getTotlaOrdersOfaCustomerFromDAO(int customerId)throws Exception{
		log.info("inside getTotalOrdersofaCustomerFromDAO()");
		int totalOrders=this.customerDao.getTotalOrdersofaCustomer(customerId);
		return totalOrders;
	}
	
	
	//gettitng all Orders of a customer
	public ArrayList<OrdersDto> getOrdersOfaCustomerFromDAO(int customerId)throws Exception{
		log.info("inside getOrdersOfaCustomerFromDAO()");
		//making use of method in dealsheet dao..
		Customer customer=new Customer();
		customer.setCustomerId(customerId);
		ArrayList<Orders> orders=this.dealSheetDao.getOrdersofCustomers(customer);
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		for(Orders order:orders){
			//modified by bhagya on may 26th,2014
			orderDtos.add(this.adminService.populateOrderDtoWithAnnualUpfrontCommissionsAndUpfrontPaidDate(order));
		}
		return orderDtos;
	}
	
	
	
	//Changes ORder from Customer and Deletes previous Customer.
	public int updateOrderswithChangeCustomersinDAO(int customerId,String taxId)throws Exception{
		log.info("inside updateOrderswithChangeCustomersinDAO()");
		Customer newCust=this.dealSheetDao.getCustomerByTaxId(taxId);	
		Customer customer=this.customerDao.getCustomerById(customerId);
		ArrayList<Orders> orders=this.customerDao.getOrdersofaCustomer(customerId);
		
		for(Orders order: orders){
			order.setTaxId(newCust);
		}
		int result=this.customerDao.updateOrderswithChangedCustomer(orders);
		if(result>0){
			this.customerDao.deleteCustomer(customer);
		}
		return result;
		
	}
	
	
	//Deleting Customer and ORders Directly..	
	public int deleteCustomersnOrders(int customerId)throws Exception{
		log.info("inside deleteCustomersnOrders()");
		Customer customer=this.customerDao.getCustomerById(customerId);
		try{		
			ArrayList<Orders> orders=this.dealSheetDao.getOrdersofCustomers(customer);
			this.customerDao.deleteAllOrdersOfaCustomer(orders);
			int result=this.customerDao.deleteCustomer(customer);
			return result;
		}
		catch(OrderNotFoundException e){
			int result=this.customerDao.deleteCustomer(customer);
			return result;
		}
		
	}
	
	
	/*
	 * Added by Jeevan on 22 July 2013 in order to give Commision Data in Customers page..
	 * 
	 * Not asked by Client, added to give additional functionality..
	 * 
	 */
	public Map<String, Object> getCommissionsOfaCustomerById(Integer customerId)throws Exception{
		log.info("inside getCommissionsOfaCustomerById()");
		Map<String, Object>customerCommissions=new HashMap<String, Object>();
		ArrayList<OrdersDto> orderDtos=this.getOrdersOfaCustomerFromDAO(customerId);
		Double expectedCommission=0.0;
		Double receiveCommission=0.0;
		Double expectedTermCommission=0.0;
		int i=0;
		
		for(OrdersDto order:orderDtos){
			expectedCommission=expectedCommission+order.getCommission();
			receiveCommission=receiveCommission+order.getUpfrontCommission();
			expectedTermCommission=expectedTermCommission+order.getTermCommission();
			i++;
		}
		customerCommissions.put("accounts", (double) i);
		customerCommissions.put("businessName", orderDtos.get(0).getBusinessName());
		customerCommissions.put("expectedCommission", expectedCommission);
		customerCommissions.put("expectedTermCommission", expectedTermCommission);
		customerCommissions.put("receivedCommission", receiveCommission);
		return customerCommissions;
	}
	/*Added by bhagya on may 26th,2014
	 * populate the orderDto with upfrontcommission 2,3,4 and upfront paid date 2,3,4
	 * 
	 */
	public OrdersDto populateOrderDto(Orders order){
		OrdersDto orderDto=OrdersDto.populateOrderDto(order);
		orderDto.setUpfrontCommission(order.getUpfrontCommission());
		orderDto.setUpfrontPaidDate(order.getUpfrontPaidDate());
		orderDto.setUpfrontCommission2(order.getUpfrontCommission2());
		orderDto.setUpfrontPaidDate2(order.getUpfrontPaidDate2());
		orderDto.setUpfrontCommission3(order.getUpfrontCommission3());
		orderDto.setUpfrontPaidDate3(order.getUpfrontPaidDate3());
		orderDto.setUpfrontCommission4(order.getUpfrontCommission4());
		orderDto.setUpfrontPaidDate4(order.getUpfrontPaidDate4());
		return orderDto;
		
	}
	
	//gettitng all Orders of a customer
		public OrdersDto getReconcileOrdersByOrderId(int orderId)throws Exception{
			log.info("inside getReconcileOrdersOfaCustomerFromDAO()");
			//making use of method in dealsheet dao..
			Orders orders=this.dealSheetDao.getOrderByOrderId(orderId);
			System.out.println(orders.getUpfrontCommission2()+"com");
			OrdersDto orderDto=this.populateOrderDto(orders);			
			return orderDto;
		}
	
	//Added by bhagya on may 26th,2014
	//saves or Updates Reconcile commissions
			public Integer saveorReconcileCommissions(Double upfrontCommission,Double upfrontCommission2,Double upfrontCommission3,Date upfrontPaidDate,
					Date upfrontPaidDate2,Date upfrontPaidDate3,Integer orderId )throws Exception{
				log.info("inside saveOrReconcileCommissions()");
				Orders order=this.dealSheetDao.getOrderByOrderId(orderId);
				if(upfrontCommission!=null){
					order.setUpfrontCommission(upfrontCommission);				
				}
				else{
				order.setUpfrontCommission(0.0);
				}
				if(upfrontCommission2!=null){
					order.setUpfrontCommission2(upfrontCommission2);
				}
				else{
				order.setUpfrontCommission2(0.0);
				}
				if(upfrontCommission3!=null){
					order.setUpfrontCommission3(upfrontCommission3);
					}
				else{
				order.setUpfrontCommission3(0.0);
				}
				order.setUpfrontPaidDate(upfrontPaidDate);
				order.setUpfrontPaidDate2(upfrontPaidDate2);
				order.setUpfrontPaidDate3(upfrontPaidDate3);
				
				Integer status=this.dealSheetDao.saveDealSheetToDB(order);
				
				return status;
				
			}
}
