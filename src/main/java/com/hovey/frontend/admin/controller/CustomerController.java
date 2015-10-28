/**
 * 
 
*  Class to Monitor and Maintain Customers of the Deals..
 * 
 * */


package com.hovey.frontend.admin.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hovey.backend.agent.exception.CustomersNotFoundException;
import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.frontend.admin.service.CustomerService;
import com.hovey.frontend.agent.dto.CustomerDto;
import com.hovey.frontend.agent.dto.OrdersDto;
import com.hovey.frontend.agent.service.DealSheetService;

/**
 * 	!@author JEEVAN
 * */

@Controller
public class CustomerController {

	private static Logger log=Logger.getLogger(CustomerController.class);
	
	@Resource(name="dealSheetService")
	private DealSheetService dealSheetService;
	
	@Resource(name="customerService")
	private CustomerService customerService;
	
	// Displays the customers
	@RequestMapping(value="/admin/customer.do", method=RequestMethod.GET)
	public String displayCustomers(Map<String,Object> map,@RequestParam(value="page",required=false,defaultValue="0")Integer pageNo,
			@RequestParam(value="range",required=false,defaultValue="20")	Integer pageSize,@RequestParam(value="sortby",required=false,defaultValue="businessName")String sortBy,
			@RequestParam(value="searchBy",required=false)String searchBy){
		log.info("inside displayCustomers()");
		try{
			int pagesNeeded=this.customerService.findTotalNoOfCustomerPages(pageSize,searchBy);	
			int firstCustomer=pageSize*pageNo+1;
			int totalCustomers=this.customerService.getTotalCustomers(searchBy);
			int lastCustomer;
			ArrayList<CustomerDto> customerDtos=this.customerService.getCustomersFromDb(pageNo, pageSize,sortBy,searchBy);
			if(!customerDtos.isEmpty()){
				for(CustomerDto customer:customerDtos){	
					try{
						Map<String, Object>customerCommissions=this.customerService.getCommissionsOfaCustomerById(customer.getCustomerId());
						customer.setTotalExpectedCommission((Double) customerCommissions.get("expectedCommission"));
						customer.setTotalCommissionReceived((Double)customerCommissions.get("receivedCommission"));
						double totalAccounts=(Double) customerCommissions.get("accounts");
						customer.setTotalAccounts((int) totalAccounts);
						customer.setBusinessName(customerCommissions.get("businessName").toString());
					}
					catch(OrderNotFoundException e){
						customer.setTotalExpectedCommission(0.0);
						customer.setTotalCommissionReceived(0.0);
						customer.setTotalAccounts(0);
						log.error("No Orders Exists for Current Customer"+e.toString());
					}					
				}
				lastCustomer=this.getCustomersOfLastPageFromRangeandTotalCustomers(pageSize, totalCustomers, firstCustomer);
				map.put("first", firstCustomer);
				map.put("total", totalCustomers);
				map.put("last", lastCustomer);
				map.put("customers", customerDtos);
				map.put("page",pageNo);
				map.put("end", pagesNeeded);
				map.put("sortby", sortBy);
				return "admin/customers";
			}
			else{
				throw new CustomersNotFoundException();
			}
			
		}
		catch(CustomersNotFoundException e){
			String message="No Customers Found ";
			map.put("message",message);
			log.error(message+e.toString());
			return "admin/customers";			
		}
		
		catch(Exception e){
			e.printStackTrace();
			String message="Error While Displaying Customers";
			map.put("message",message);
			log.error(message+e.toString());
			return "error";
		}	
	}
	
	
	//Displays Edit Page for Customer
	@RequestMapping(value="/admin/editcustomer.do",method=RequestMethod.GET)
	public String displayEditCustomer(Map<String, Object> map, @RequestParam("id") int customerId,@ModelAttribute("customer") CustomerDto customer){
		log.info("inside editCustomer()");
		try{
			CustomerDto customerDto=this.customerService.getCustomerByIdFromDAO(customerId);
			map.put("customer", customerDto);
			return "admin/editCustomer";
		}
		catch(CustomersNotFoundException e){
			String message="Customer Not Found";
			log.error(message+e.toString());
			map.put("message", message);
			return "admin/editCustomer";
		}
	}
	
	//Editing Customer
	@RequestMapping(value="/admin/editcustomer.do",method=RequestMethod.POST)
	public String editCustomer(Map<String,Object> map,@ModelAttribute("customer") CustomerDto customer) throws Exception{
		log.info("inside editCustomer()");
		try{
			int result=this.customerService.editCustomerinDao(customer);
			if(result>0){
				return "redirect:/admin/customer.do";
			}
			else{
				throw new Exception();
			}			
		}
		catch(Exception e){
			String message="Error while Editing Customer";
			e.printStackTrace();
			log.error(message+e.toString());
			map.put("message", message);
			return "error";
		}		
	}
	
	
	
	//Handles Delete Customer Request and Shows a Page for Deletion..
	@RequestMapping(value="/admin/deletecustomer.do",method=RequestMethod.GET)
	public String showCustomerDelete(@RequestParam("id")Integer customerId,Map<String, Object> map){
		log.info("inside showCustomerDelete()");
		try{
			int totalOrders=this.customerService.getTotlaOrdersOfaCustomerFromDAO(customerId);
			ArrayList<CustomerDto> customers=this.dealSheetService.getCustomersFromDb();
			map.put("customers", customers);
			map.put("orders", totalOrders);
			map.put("id", customerId);
			return "admin/deleteCustomer";
		}
		catch(Exception e){
			String message="Error While initating Customer Deletion";
			log.error(message+e.toString());
			map.put("message", message);
			return "error";
		}
	}
	
	//Deletes customers and corresponding Orders
		@RequestMapping(value="/admin/deletecustomer.do",method=RequestMethod.POST)
		public String deleteCustomers(@RequestParam("id")Integer customerId,Map<String, Object> map){
			log.info("inside showCustomerDelete()");
			try{
				int result=this.customerService.deleteCustomersnOrders(customerId);
				if(result>0){
					return "redirect:/admin/customer.do";
				}
				else{
					throw new Exception();
				}
			}
			catch(Exception e){
				String message="Error While Deleting Customer";
				log.error(message+e.toString());
				map.put("message", message);
				return "error";
			}
		}
		
	
	
	//Handles Delete Customer Request and Shows a Page for Deletion..
		@RequestMapping(value="/admin/changecustomerorders.do",method=RequestMethod.POST)
		public String changeOrdersofCustomerToDelete(@RequestParam("id")Integer customerId,@RequestParam("taxId") String taxId,Map<String, Object> map){
			log.info("inside showCustomerDelete()");
			try{
				
				int result=this.customerService.updateOrderswithChangeCustomersinDAO(customerId, taxId);
				if(result>0)
					return "redirect:/admin/customer.do";
				else
					throw new Exception();
			}
			catch(Exception e){
				e.printStackTrace();
				String message="Error While Transfering Orders";
				log.error(message+e.toString());
				map.put("message", message);
				return "error";
			}
		}
	
	
	
	/*
	 * Added on July 22,2013 by Jeevan to show Customer accounts along with their commissions,
	 * 
	 */
	@RequestMapping(value="/admin/customeraccounts.do",method=RequestMethod.GET)
	public String getCustomerAccountDetails(@RequestParam("id")Integer customerId,Map<String, Object>map){
		log.info("inside getCustomerAccountDetails()");
		try{
			ArrayList<OrdersDto> orderDtos=this.customerService.getOrdersOfaCustomerFromDAO(customerId);
			map.put("orders", orderDtos);
			return "admin/customerAccounts";			
		}
		catch(OrderNotFoundException e){
			log.error("No Orders Found for the Current Customers"+e.toString());
			String message="No Orders found for Customer ";
			map.put("message", message);
			return "admin/customerAccounts";
		}
		catch(Exception e){
			log.error("Error while getting Customer Accounts");
			map.put("message","Error while getting Customer Accounts");
			return "error";
		}
	}
		
	/* ****for Calculating Orders of LAst Page Based on Range and Total Records...... *
	 * 
	 * 
	 * Added on July 19,2013, by Jeevan...
	 * 
	 * 
	 * *****/			
	private int getCustomersOfLastPageFromRangeandTotalCustomers(int range,int totalOrders,int firstOrder){
		log.info("inside getOrdersOfLastPageFromRangeandTotalOrders()	");
		int lastOrder=firstOrder+range-1;
		if(lastOrder>totalOrders){
			lastOrder=(totalOrders%range)+firstOrder-1;
		}
		return lastOrder;
	}
	
	
}
