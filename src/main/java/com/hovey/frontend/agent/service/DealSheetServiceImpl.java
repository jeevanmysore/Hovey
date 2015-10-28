/** 
 *  ** $Header:  DealSheetServiceImpl.java, M Jeevan Kumar, 19/04/2013
* Copyright (c) KNS Technologies Pvt. Ltd. 2013 All Rights Reserved
*
* NAME
* DealSheetServiceImpl.java
* USAGE
* Implementation of DealSheetDao, it contains all the methods to interact with database to manipulate Deal Sheet Operations..
* 
* DESCRIPTION
* It is used to create Deal Sheet details table in Database
* CHANGES (most recent first)
* 
*     public CustomerDto getCustomerByTaxIdFromDao(String taxId) throws Exception   22/04
* 
* 
* M JEEVAN KUMAR, 22/04/2013
 * */

package com.hovey.frontend.agent.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AutoPopulatingList;

import com.hovey.backend.admin.dao.AdminDao;
import com.hovey.backend.admin.dao.CustomerDao;
import com.hovey.backend.agent.dao.DealSheetDao;
import com.hovey.backend.agent.exception.StateNotFoundException;
import com.hovey.backend.agent.model.AgentCommissions;
import com.hovey.backend.agent.model.BillingState;
import com.hovey.backend.agent.model.ContractTypes;
import com.hovey.backend.agent.model.Customer;
import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.agent.model.RescindedOrders;
import com.hovey.backend.agent.model.State;
import com.hovey.backend.agent.model.Transactions;
import com.hovey.backend.agent.model.Utility;
import com.hovey.backend.supplier.dao.SupplierDao;
import com.hovey.backend.supplier.model.Supplier;
import com.hovey.backend.user.model.HoveyUser;
import com.hovey.frontend.agent.dto.BillingStateDto;
import com.hovey.frontend.agent.dto.ContractTypeDto;
import com.hovey.frontend.agent.dto.CustomerDto;
import com.hovey.frontend.agent.dto.CustomerSearchDto;
import com.hovey.frontend.agent.dto.DealSheetForm;
import com.hovey.frontend.agent.dto.OrdersDto;
import com.hovey.frontend.agent.dto.StateDto;
import com.hovey.frontend.agent.dto.UtilityDto;
import com.hovey.frontend.user.dto.HoveyUserDto;

@Transactional
@Service("dealSheetService")
public class DealSheetServiceImpl implements DealSheetService {

	/**
	 * @author JEEVAN
	 * 
	 */
	
	private static Logger log=Logger.getLogger(DealSheetServiceImpl.class);
	
	@Resource(name="dealSheetDao")
	private DealSheetDao dealSheetDao;
	
	
	@Resource(name="supplierDao")
	private SupplierDao supplierDao;
	
	@Resource(name="customerDao")
	private CustomerDao customerDao;
	
	@Resource(name="adminDao")
	private AdminDao adminDao;
	
	
	// Retreives CustomerDto's by Customer Number.
	public ArrayList<CustomerDto> getCustomerByAgentNumberFromDb(String agentNumber)throws Exception{
		log.info("inside getCustomerByAgentNumberFromDB()");
		ArrayList<Customer> customers=this.dealSheetDao.getCustomersOfAgent(agentNumber);
		ArrayList<CustomerDto> customerDtos=new  ArrayList<CustomerDto>();
		for(Customer customer:customers){
			CustomerDto customerDto=CustomerDto.populateCustomerDto(customer);
			customerDtos.add(customerDto);
		}
		return customerDtos;
	}
	
	// Retreives CustomerDto's by Customer Number.
		public ArrayList<CustomerDto> getCustomersFromDb()throws Exception{
			log.info("inside getCustomersFromDB()");
			ArrayList<Customer> customers=this.dealSheetDao.getCustomers();
			ArrayList<CustomerDto> customerDtos=new  ArrayList<CustomerDto>();
			for(Customer customer:customers){
			/*	if(null!=customer.getTaxId() && !customer.getTaxId().equals("")){*/
					CustomerDto customerDto=CustomerDto.populateCustomerDto(customer);
					customerDtos.add(customerDto);
				/*}*/
			}			
			return customerDtos;
		}
	
	/*
	 * Added by Jeevan on October 08,2013
	 * (non-Javadoc)
	 * @see com.hovey.frontend.agent.service.DealSheetService#getCustomerByTaxIdFromDao(java.lang.String)
	 */
	  public Map<String, Object> getSortedCustomerDetails(ArrayList<CustomerDto> customerDtos)throws Exception{
		  log.info("inside getSortedCustomerDetails()");
		  Map<String, Object> customerMap=new HashMap<String, Object>();
		  ArrayList<String> firstNames=this.sortOrdersBasedonPropert(customerDtos, "firstName");
		  ArrayList<String> lastNames=this.sortOrdersBasedonPropert(customerDtos, "lastName");
		  ArrayList<String> taxIds=this.sortOrdersBasedonPropert(customerDtos, "taxId");
		  ArrayList<String> phoneNos=this.sortOrdersBasedonPropert(customerDtos, "phoneNo");		  
		  customerMap.put("firstNames", firstNames);
		  customerMap.put("lastNames", lastNames);
		  customerMap.put("taxIds", taxIds);
		  customerMap.put("phoneNos", phoneNos);  
		  return customerMap;
	  }
		
	  
	  private ArrayList<String> sortOrdersBasedonPropert(ArrayList<CustomerDto> customerDtos,final String property)throws Exception{
		  log.info("inside sortOrdersBasedonPropert()");	
		  ArrayList<String> sortedProps=new ArrayList<String>();
		  Set<CustomerDto> set = new TreeSet<CustomerDto>(new Comparator<CustomerDto>() {
		        @Override
		        public int compare(CustomerDto o1, CustomerDto o2) {
		        	if(null!=o1.getTaxId() && null!=o2.getTaxId()){
		        		return o1.getTaxId().compareTo(o2.getTaxId());
		        	}
		        	else{
		        		return 1;
		        	}
		        }
		    });		  
		   set.addAll(customerDtos);		 
		   customerDtos.clear();		  
		   customerDtos.addAll(set);
		   set.clear();		  
		   Collections.sort(customerDtos, new Comparator<CustomerDto>() {
				@Override
				public int compare(CustomerDto c1, CustomerDto c2) {
					if(property.equalsIgnoreCase("firstName")){
						return c1.getFirstName().compareTo(c2.getFirstName());
					}
					else if(property.equalsIgnoreCase("lastName")){
						return c1.getLastName().compareTo(c2.getLastName());
					}
					else if(property.equalsIgnoreCase("taxId")){
						if(null!=c1.getTaxId() && null!=c2.getTaxId()){
							return c1.getTaxId().compareTo(c2.getTaxId());
						}
						else
							return 1;
					}
					else{
						return c1.getPhoneNo().compareTo(c2.getPhoneNo());
					}
				}				
			});
		  
		 
		  
		  for(CustomerDto cust:customerDtos){
			  if(property.equalsIgnoreCase("firstName")){
					sortedProps.add(cust.getFirstName());
				}
				else if(property.equalsIgnoreCase("lastName")){
					sortedProps.add(cust.getLastName());
				}
				else if(property.equalsIgnoreCase("taxId")){
					sortedProps.add(cust.getTaxId());
				}
				else{
					sortedProps.add(cust.getPhoneNo());
				}
		  } 		  
		  return sortedProps;		  
	  }
	  
	  
		
		
	
	
	//get CustomerDto based on TaxID;
	public CustomerDto getCustomerByTaxIdFromDao(String taxId) throws Exception{
		log.info("inside getCustomerByTaxIdFromDao()");
		Customer customer=this.dealSheetDao.getCustomerByTaxId(taxId);
		CustomerDto customerDto=CustomerDto.populateCustomerDto(customer);		
		return customerDto;
	}
	
	//saves customer to DB
	public int saveCustomer(int id,String firstName, String lastName, String email,String title,String phoneNo,String taxId,String fax,Boolean taxExempt)throws Exception{
		System.out.println("tax Id2"+taxId);
		
		Customer customer=new Customer();
		if(id!=0){
			customer.setCustomerId(id);
		}
		customer.setTaxId(taxId);
		customer.setTitle(title);
		
		customer.setEmail(email);
		customer.setFaxNo(fax);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setPhoneNo(phoneNo);		
			customer.setTaxExempt(taxExempt);
	    HoveyUser user=this.dealSheetDao.getUserByAgentNumber("101");   // Needs to Replace with Logged in Agent.
	    //customer.setAgent(user);
		int result=this.dealSheetDao.saveCustomerToDB(customer);
		return result;
	}
	
	
		
	@Transactional
	//for saving deal sheets with multiple accounts/ orders
	public void saveDealSheetToDB(DealSheetForm dealSheet,String agentNumber)throws Exception{
		log.info("inside saveDealSheetTODB()");
		Customer customer=this.saveorUpdateCustomerinDealSheet(dealSheet,agentNumber);
		System.out.println("check1"+dealSheet.getCustomer().getTaxId());
		this.saveOrdersinDealSheet(dealSheet, customer,agentNumber);
	}
	
	//editing deal sheet
	public int editDealSheetToDB(DealSheetForm dealSheet)throws Exception{
		log.info("inside editDealSheetToDB()");
		ArrayList<Orders> orders=new ArrayList<Orders>();
		 AutoPopulatingList<OrdersDto> orderDtoList=dealSheet.getOrders();
		// System.out.println("customer "+dealSheet.getCustomer().getCustomerId());	
		 
		 String agentNumber=dealSheet.getOrders().get(0).getCreatedAgent().getAgentNumber();
		 Customer customer=this.saveorUpdateCustomerinDealSheet(dealSheet, agentNumber);		 
		 int orderId=dealSheet.getOrders().get(0).getId();
		 Orders firstOrder=this.dealSheetDao.getOrderByOrderId(orderId);		 
		HoveyUser agent=this.dealSheetDao.getUserByAgentNumber(dealSheet.getOrders().get(0).getCreatedAgent().getAgentNumber());
		 Iterator<OrdersDto> it=orderDtoList.iterator();
		 int transactionId=0;
		 while(it.hasNext()){			 	
			   OrdersDto orderDto=it.next();
			   Orders order=null;
			   if(null!=orderDto.getId() && orderDto.getId()>0){
				    order=this.dealSheetDao.getOrderByOrderId(orderDto.getId());
				    transactionId=order.getTransactionId().getId();		
				    if(!order.getAgent().getAgentNumber().equals(agent.getAgentNumber())){	 //added on March -05, 2014				    	
				    		order.setAgentCommissionStatus("unpaid");
				    	
				    }
			   }
			   else{
				  order=new Orders();
				  Transactions trans=this.dealSheetDao.getTransactionById(transactionId);
				  order.setTransactionId(trans);
				  order.setAgentCommissionStatus("unpaid");
				  order.setFaxReceived(false);
				   order.setStatus("agent");
				   order.setQA(false);//defaultt
				   order.setUpfrontCommission(0.0);
				   order.setUpfrontCommission2(0.0);
				   order.setUpfrontCommission3(0.0);
				   order.setUpfrontCommission4(0.0);
			   }
			   if(orderDto.getUtility().getUtility().equalsIgnoreCase("DUKE") || orderDto.getUtility().getUtility().equalsIgnoreCase("DPL") ){
				   if(orderDto.getAccountNumber().length()==11){
					   order.setAccountNumber(orderDto.getAccountNumber().substring(0, 10));
				   }
				   else{
					   order.setAccountNumber(orderDto.getAccountNumber());
				   }
			   }
			   else{
				   order.setAccountNumber(orderDto.getAccountNumber());
			   }
			   
			   order.setContractType(orderDto.getContractType());
			   /* 
			    * Modified by Jeevan on July 22,2013 , (converted to hanlde Deal Start Date directly instead of startDate-->DealStartDate
			    * 
			    */
			   //Date dealStartDate=this.populateStartDate(orderDto.getStartDate());
			   order.setDealStartDate(orderDto.getDealStartDate());
			   /* End of Modification*/
			   Utility util=this.dealSheetDao.getUtilityByName(orderDto.getUtility().getUtility());					  
			   order.setUtility(util);
			   BillingState billState=this.dealSheetDao.getBillingStateByStateName(orderDto.getBillingState().getState());			   
			   order.setBillingState(billState);
			   State serviceState=this.dealSheetDao.getStateByStateName(orderDto.getServiceState().getState());
			   order.setServiceState(serviceState);
			   order.setAgentNotes(dealSheet.getOrders().get(0).getAgentNotes());
			   order.setBillingCity(orderDto.getBillingCity());
			   order.setBillingStreet(orderDto.getBillingStreet());
			   order.setBillingZip(orderDto.getBillingZip());
			   order.setBusinessName(orderDto.getBusinessName());
			   order.setBillingUnit(orderDto.getBillingUnit());
			   order.setDba(orderDto.getDba());
			   order.setKwh(orderDto.getKwh());
			   order.setRate(orderDto.getRate());
			   order.setRateClass(orderDto.getRateClass());			
			   order.setServiceCity(orderDto.getServiceCity());				   
			   order.setServiceStreet(orderDto.getServiceStreet());
			   order.setServiceZip(orderDto.getServiceZip());
			   order.setServiceUnit(orderDto.getServiceUnit());
			   order.setOrderDate(dealSheet.getOrders().get(0).getOrderDate());
			   order.setTpv(orderDto.getTpv());			  
			   order.setTerm(orderDto.getTerm());			 	
			   Supplier supplier=this.supplierDao.getSupplierByName(dealSheet.getOrders().get(0).getSupplierName().getSupplierName());
			   order.setSupplierName(supplier);
			   order.setSpecialPricing(orderDto.isSpecialPricing());		 
			   order.setService(orderDto.getService());
			   order.setAgent(agent);
			   
			   if(null!=orderDto.getDealStartDate()){
				   Date dealEndDate=this.getEndDateFromStartDateandTerm(orderDto.getDealStartDate(), orderDto.getTerm());
				   order.setDealEndDate(dealEndDate);
			   }			  
			   //added on September 19,2013 by Jeevan
			   order.setCounty(orderDto.getCounty());
			   order.setMeterReadDate(orderDto.getMeterReadDate());
			   
			   //on October 31
			   order.setTaxId(customer);
			   /*
			    * modified by Jeevan on July 31, 2013 as we are using commission Rate field..
			    */
			   if(null!=orderDto.getId() && orderDto.getId()>1){
				   order.setCommission(order.getKwh() * order.getCommissionRate());
			   }
			   else{
				    order.setAgent(firstOrder.getAgent());
				    order.setTaxId(firstOrder.getTaxId());
			   		Double commission=this.calculateCommission(order.getSupplierName(), order.getKwh(), order.getContractType());
			   		order.setCommission(commission);
				   	 if(order.getContractType().equalsIgnoreCase("new")){
						   order.setCommissionRate(supplier.getContractCommission());
					   }
					   else{
						   order.setCommissionRate(supplier.getRenewalCommission());
					   }
			   }
			   orders.add(order);			
		 }		 
		 int result=this.dealSheetDao.editDealSheetOrdersToDB(orders);
		 return result;
	}
	
	
	
	//for saving customer obtained from Deal Sheet..
	private Customer saveorUpdateCustomerinDealSheet(DealSheetForm dealSheet,String agentNumber) throws Exception{
		log.info("inside saveorUpdateCustomerinDealSheet()");
		Customer customer=new Customer();
		/*HoveyUser user=this.dealSheetDao.getUserByAgentNumber(agentNumber);   // Needs to Replace with Logged in Agent.
	   // customer.setAgent(user);
*/	    if(dealSheet.getCustomer().getCustomerId()!=0){
	    	customer.setCustomerId(dealSheet.getCustomer().getCustomerId());
	    }
	    customer.setEmail(dealSheet.getCustomer().getEmail());
	    customer.setFaxNo(dealSheet.getCustomer().getFaxNo());
	    customer.setFirstName(dealSheet.getCustomer().getFirstName());
	    customer.setLastName(dealSheet.getCustomer().getLastName());
	    customer.setPhoneNo(dealSheet.getCustomer().getPhoneNo());
	    customer.setType(dealSheet.getCustomer().getType());
	    customer.setFronter(dealSheet.getCustomer().getFronter());
	
	    if(dealSheet.getCustomer().getTaxExempt().equalsIgnoreCase("yes")){
	    	customer.setTaxExempt(true);
	    }
	    else if(dealSheet.getCustomer().getTaxExempt().equalsIgnoreCase("no")){
	    	customer.setTaxExempt(false);
	    }	    		
	    if(null!=dealSheet.getCustomer().getTaxId() && dealSheet.getCustomer().getTaxId()!=""){
	    	customer.setTaxId(dealSheet.getCustomer().getTaxId());
	    }	    
	    customer.setTitle(dealSheet.getCustomer().getTitle());
	    int result=this.dealSheetDao.saveCustomerToDB(customer);
	    if(result>0)
	    	return customer;
	    else 
	    	return null;
	}
	
	
	
	//for saving deal sheet accounts .
	private void saveOrdersinDealSheet(DealSheetForm dealSheet,Customer customer,String agentNumber)throws Exception{
		log.info("inside saveOrdersinDealSheet()");
		HoveyUser user=this.dealSheetDao.getUserByAgentNumber(agentNumber);		
		  ArrayList<Orders> ordersList=new ArrayList<Orders>();
		   AutoPopulatingList<OrdersDto> orderDtoList=dealSheet.getOrders();
		   Date orderDate=dealSheet.getOrders().get(0).getOrderDate();
		 //  String taxId=dealSheet.getCustomer().getTaxId();
		   Iterator<OrdersDto> it=orderDtoList.iterator();
		   Transactions trans=new Transactions();
		  Transactions transactionId=this.dealSheetDao.addTransaction(trans);
		   String supplierName=dealSheet.getOrders().get(0).getSupplierName().getSupplierName();
		   String agentNotes=dealSheet.getOrders().get(0).getAgentNotes();
		   while(it.hasNext()){
			   OrdersDto orderDto=it.next();
			   Orders order=new Orders();		
			   if(null!=orderDto.getId() && orderDto.getId()>0){
				   order.setOrderId(orderDto.getId());				  
			   }
			  
			   if(orderDto.getUtility().getUtility().equalsIgnoreCase("DUKE") || orderDto.getUtility().getUtility().equalsIgnoreCase("DPL") ){
				   if(orderDto.getAccountNumber().length()==11){
					   order.setAccountNumber(orderDto.getAccountNumber().substring(0, 10));
				   }
				   else{
					   order.setAccountNumber(orderDto.getAccountNumber());
				   }
			   }
			   else{
				   order.setAccountNumber(orderDto.getAccountNumber());
			   }
			   
			   order.setContractType(orderDto.getContractType());
			   
		/* *******************        Modified by Jeevan on July 18,2013 to add day to DealSheet ***
		 * 	Date dealStartDate=this.populateStartDate(orderDto.getStartDate());
		 *   order.setDealStartDate(dealStartDate);   //removed as not needed, can be implemented directly using Spring Binding......
		 * *******/
			   order.setDealStartDate(orderDto.getDealStartDate());
		/* ***************Modified by Jeevan on July 18,2013**************************************/	 		  
			   
			   order.setBillingCity(orderDto.getBillingCity());
			  // order.setBillingState(orderDto.getBillingState());
			  
			   
			  BillingState billState=this.dealSheetDao.getBillingStateByStateName(orderDto.getBillingState().getState());
			   order.setBillingState(billState);
			   order.setBillingStreet(orderDto.getBillingStreet());
			   order.setBillingZip(orderDto.getBillingZip());
			   order.setBusinessName(orderDto.getBusinessName());
			   order.setBillingUnit(orderDto.getBillingUnit());
			   order.setDba(orderDto.getDba());
			   order.setKwh(orderDto.getKwh());
			   order.setRate(orderDto.getRate());
			   order.setRateClass(orderDto.getRateClass());			
			   order.setServiceCity(orderDto.getServiceCity());
			   
			   State serviceState=this.dealSheetDao.getStateByStateName(orderDto.getServiceState().getState());
			   order.setServiceState(serviceState); 
			
			   order.setServiceStreet(orderDto.getServiceStreet());
			   order.setServiceZip(orderDto.getServiceZip());
			   order.setServiceUnit(orderDto.getServiceUnit());
			   order.setOrderDate(orderDate);
			   order.setTpv(orderDto.getTpv());
			   order.setAgent(user);
			   order.setTaxId(customer);
			   order.setTerm(orderDto.getTerm());
			   order.setSentToSupplier(orderDto.getSentToSupplier());
			   
			   order.setFaxReceived(false);
			   order.setStatus("agent");	//default Status	
			   Supplier supplier=this.supplierDao.getSupplierByName(supplierName);
			   order.setSupplierName(supplier);
			   //Added By Jeevan on July 31, 2013 to display commission Rates along with Orders..			   
			   if(order.getContractType().equalsIgnoreCase("new")){
				   order.setCommissionRate(supplier.getContractCommission());
			   }
			   else{
				   order.setCommissionRate(supplier.getRenewalCommission());
			   }
			   
			   order.setAgentCommissionStatus("unpaid");
			   order.setTransactionId(transactionId);
			   order.setSpecialPricing(orderDto.isSpecialPricing());
			   order.setAgentNotes(agentNotes);
			   Utility util=this.dealSheetDao.getUtilityByName(orderDto.getUtility().getUtility());					  
			   order.setUtility(util);
			   order.setService(orderDto.getService());
			   if(null!=orderDto.getDealStartDate()){
				   Date dealEndDate=this.getEndDateFromStartDateandTerm(orderDto.getDealStartDate(), orderDto.getTerm());
				   order.setDealEndDate(dealEndDate);
			   }
			  
			  
			   Double commission=this.calculateCommission(order.getSupplierName(), order.getKwh(), order.getContractType());
			   order.setCommission(commission);
			   //added on September 19,2013 by Jeevan
			   order.setCounty(orderDto.getCounty());
			   order.setMeterReadDate(orderDto.getMeterReadDate());
			   order.setQA(false); //by Default
			   //added on June 16, 2014
			   order.setUpfrontCommission(0.0);
			   order.setUpfrontCommission2(0.0);
			   order.setUpfrontCommission3(0.0);
			   order.setUpfrontCommission4(0.0);
			   
			   ordersList.add(order);				   
		   }
		   this.dealSheetDao.saveDealSheetOrdersToDB(ordersList);
		
	}
	
	
	
	//calculating dealEndDate based on StartDate and Term...
	
	public Date getEndDateFromStartDateandTerm(Date dealStartDate,String term){
		Calendar calendar=Calendar.getInstance();
		calendar.clear();
		calendar.setTime(dealStartDate);
		calendar.add(Calendar.MONTH, Integer.parseInt(term));
		/* *****Modified by Jeevan on July 18,2013 to considered Deal End Date to include exclusion Date too******
		 *   calendar.add(Calendar.DATE, -1);  //No Need to Reduce a day for Deal End as deal will be from 01/01/xx to 01/01/xy
		 *   
		 * *******/		      
		Date dealEndDate=calendar.getTime();
		return dealEndDate;
	}
	
	
	//calculating Commission,
	//Needs to be refactored.
	private Double calculateCommission(Supplier supplier, int annualkWh, String contractType){/*assumed to be null for now */
		log.info("inside calculate Commission");
		Double commission=0.0;
		
			if(contractType.equalsIgnoreCase("new")){
				commission=annualkWh*supplier.getContractCommission();
			}
			else{
				commission=annualkWh*supplier.getRenewalCommission();
			}		
		return commission;
	}
	
	
	
	// for getting starte date from Date String
	public Date populateStartDate(String startDate){
		String[] strDate=startDate.split(" ");
		Calendar calendar=Calendar.getInstance();
		calendar.clear();
		int month=this.returnMonthNo(strDate[0]);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR,Integer.parseInt(strDate[1]));
		Date date=calendar.getTime();
		return date;		
	}
	
	
	
			public enum Month{
				January, February, March, April, May, June, July, August, September, October, November, December;
			}
			private int returnMonthNo(String monthString){
				Month month=Month.valueOf(monthString);
				int i=0;
				switch (month) {
				case January:			
					i=0;
					break;
				case February:			
					i=1;
					break;	
				case March:			
					i=2;
					break;
				case April:			
					i=3;
					break;	
				case May:			
					i=4;
					break;	
				case June:			
					i=5;
					break;	
					
				case July:			
					i=6;
					break;
				case August:			
					i=7;
					break;
					
				case September:			
					i=8;
					break;
				case October:			
					i=9;
					break;
				case November:			
					i=10;
					break;
				case December:			
					i=11;
					break;
					
				default:
					break;
				}
				return i;				
			};
	
	
	
	public int saveDealSheet(Date orderDate,long tpv,String rateClass,String utility,double rate,int term,String businessName,String dba,String accuount,
			int kwh,String serviceStreet,String serviceCity,String serviceUnit,String serviceState,String serviceZip,String billingStreet,String billingCity,String billingUnit,
			String billingState,String billingZip,String taxId)throws Exception{
		
		Orders order=new Orders();
		order.setAccountNumber(accuount);
		order.setBillingCity(billingCity);
		//order.setBillingState(billingState);
		order.setBillingStreet(billingStreet);
		order.setBillingUnit(billingUnit);
		order.setBillingZip(billingZip);
		order.setBusinessName(businessName);
		order.setDba(dba);
		order.setKwh(kwh);
		order.setRate(rate);
		order.setRateClass(rateClass);
		order.setServiceCity(serviceCity);
		//order.setServiceState(serviceState);
		order.setServiceStreet(serviceStreet);
		order.setServiceUnit(serviceUnit);
		order.setServiceZip(serviceZip);
		order.setOrderDate(orderDate);
		//order.setTerm(term);
		order.setTpv(tpv);
		Utility util=this.dealSheetDao.getUtilityByName(utility);
		order.setUtility(util);
		Customer customer=this.dealSheetDao.getCustomerByTaxId(taxId);
		order.setTaxId(customer);
		int result=this.dealSheetDao.saveDealSheetToDB(order);			
		return result;
	}
	
	
	
	
	
	//for getting Utilities from DB...
	public ArrayList<UtilityDto> getUtilitiesFromDb()throws Exception{
		log.info("inside getUtilitiesFromDB()");
		ArrayList<Utility> utilities=this.dealSheetDao.getUtilities();
		ArrayList<UtilityDto> utilDtos=new ArrayList<UtilityDto>();
		for(Utility util:utilities){
			UtilityDto utilDto=UtilityDto.populateUtility(util);
			utilDtos.add(utilDto);
		}
		return utilDtos;
	}
	
	public ArrayList<UtilityDto> getAllUtilitiesFromDb()throws Exception{
		log.info("inside getUtilitiesFromDB()");
		ArrayList<Utility> utilities=this.dealSheetDao.getAllUtilities();
		ArrayList<UtilityDto> utilDtos=new ArrayList<UtilityDto>();
		for(Utility util:utilities){
			UtilityDto utilDto=UtilityDto.populateUtility(util);
			utilDtos.add(utilDto);
		}
		return utilDtos;
	}
	
	
	// for formatting date..
	public  Date formatDate(String dateString) throws ParseException{
		SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
		//String dateString= month+"/"+day+"/"+year;
		Date date=format.parse(dateString);
		
		/*SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy HH:mm ss");
		
		Date date=format.parse(dateString);*/
		return date;	
	}
	
	
	//validating account based on utility rule
	public String validateAccountNo(String acc,String utility)throws Exception{
		log.info("inside validating account()");
		/**
		 * Steps:
		 * 1. Obtaqin Utility obj based on utility
		 * 2. compare size of rule in utility with of account no
		 * 3. Return error message;
		 */
		
		Utility util=this.dealSheetDao.getUtilityByName(utility);
		int size=util.getAccountLenght();
		int accountLength=acc.length();		
		if(utility.equalsIgnoreCase("DPL") || utility.equalsIgnoreCase("DUKE")){
			if(accountLength==size || accountLength==(size+1)){
				return "success";
			}
			else{
				return "Should be "+size+" / "+(size+1) +"digits";				
			}
		}
		else{
			if(accountLength==size){
				return  "success";
			}
			else{
				return "Should be "+size+" digits";
			}			
		}
		
	}
	
	
	//temporarily	
	public HoveyUserDto getUserByUserNamefromDao(String username) throws Exception{
		log.info("inside getUserByUserNameFromDao()");
		HoveyUser user=this.dealSheetDao.getUserByUsername(username);
		HoveyUserDto userDto=HoveyUserDto.populateHoveyUserDto(user);
		return userDto;
	}
	
	
	
	//for saving utility..
	public int saveUtilityToDB(String utilityName,int length)throws Exception{
		log.info("inside saveUtilityToDB()");
		Utility utility=new Utility();
		utility.setUtility(utilityName);
		utility.setAccountLenght(length);
		utility.setEnabled(true);
		int result=this.dealSheetDao.saveUtility(utility);
		return result;
		
	}
	
	
	public int editUtility(String utilityName,int length,int id)throws Exception{
		log.info("inside editUtilityToDB()");
		Utility utility=this.dealSheetDao.getUtilityByID(id);
		
		utility.setUtility(utilityName);
		utility.setAccountLenght(length);
		int result=this.dealSheetDao.saveUtility(utility);
		return result;
	}
	
	
	public int enableDisable(Integer id,String status)throws Exception{
		log.info("inside enableDisableUtility()");
		Utility utility=this.dealSheetDao.getUtilityByID(id);
		if(status.equalsIgnoreCase("enable")){
			utility.setEnabled(true);
		}
		else{
			utility.setEnabled(false);
		}
		int result=this.dealSheetDao.saveUtility(utility);
		return result;
	}
	
	
	
	
	
	//Getting all the Orders.
	public ArrayList<OrdersDto> getOrdersFromDB()throws Exception{
		log.info("inside getOrdersFromDB()");
		ArrayList<Orders> orders=this.dealSheetDao.getOrders();
		ArrayList<OrdersDto> ordersDto=new ArrayList<OrdersDto>();
		if(!orders.isEmpty()){
			for(Orders order:orders){
				OrdersDto orderDto=OrdersDto.populateOrderDto(order);
				ordersDto.add(orderDto);				
			}			
		}
		return ordersDto;
	}
	
	
	
	
	
	public ArrayList<OrdersDto> getOrdersofAgentFromDB(String agentNumber) throws Exception{
		log.info("inside getOrdersofAgentFromDB()");
		//modified according to display group by
		ArrayList<Orders> orders=this.dealSheetDao.getOrdersOfAgent(agentNumber);			
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
	    if(!orders.isEmpty()){
	    	for(Orders order:orders){
	    		
	    		OrdersDto orderDto=OrdersDto.populateOrderDto(order);
	    		orderDtos.add(orderDto);
	    	}
	    }
	    return orderDtos;
		
	}
	
	//gets Orders of a n Agent..
	public ArrayList<OrdersDto> getDealSheetsofAgentFromDB(String agentNumber) throws Exception{
		log.info("inside getDealSheetsofAgentFromDB()");
		
		
		ArrayList<Orders> orders=this.dealSheetDao.getAgentOrdersGroupByTransaction(agentNumber);
		
		
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
	    if(!orders.isEmpty()){
	    	for(Orders order:orders){
	    		
	    		OrdersDto orderDto=OrdersDto.populateOrderDto(order);
	    		orderDtos.add(orderDto);
	    	}
	    }
	    return orderDtos;
		
	}
	
	
	//for Getting Orders of a Transaction
	public ArrayList<OrdersDto> getDealSheetByTransactionIdFromDB(int transactionId)throws Exception{
		log.info("inside getDealSheetByTransactionIdFromDB()");
		ArrayList<Orders> orders=this.dealSheetDao.getOrdersofATrandsaction(transactionId);
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
	    if(!orders.isEmpty()){
	    	for(Orders order:orders){
	    		
	    		OrdersDto orderDto=OrdersDto.populateOrderDto(order);
	    		orderDtos.add(orderDto);
	    	}
	    }
	    return orderDtos;
	}
	
	
	
	/**
	 * Created by Jeevan on November 19, 2014
	 * Method to get Non rescinded Deal Sheet
	 * @param transactionId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<OrdersDto> getNonRescindedDealSheetByTransactionIdFromDB(int transactionId)throws Exception{
		log.info("inside getNonRescindedDealSheetByTransactionIdFromDB()");
		ArrayList<Orders> orders=this.dealSheetDao.getNonRescindedOrdersofATrandsaction(transactionId);
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
	    if(!orders.isEmpty()){
	    	for(Orders order:orders){
	    		
	    		OrdersDto orderDto=OrdersDto.populateOrderDto(order);
	    		orderDtos.add(orderDto);
	    	}
	    }
	    return orderDtos;
	}
	
	
	
	
	
	
	// Getting OrderBy OrderId
	public OrdersDto getOrderByOrderIdFromDB(int orderId)throws Exception{
		log.info("insidxe getOrderBOrderIdFromDB()");
		Orders order=this.dealSheetDao.getOrderByOrderId(orderId);
		OrdersDto orderDto=OrdersDto.populateOrderDto(order);
		return orderDto;
	}
	
	
	//gets DealSheets from Dao.
		public ArrayList<OrdersDto> getDealSheetsFromDB() throws Exception{
			log.info("inside getDealSheetsofAgentFromDB()");			
			ArrayList<Orders> orders=this.dealSheetDao.getDealSheetsGroupByTransaction();			
			ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		    if(!orders.isEmpty()){
		    	for(Orders order:orders){		    		
		    		OrdersDto orderDto=OrdersDto.populateOrderDto(order);
		    		orderDtos.add(orderDto);
		    	}
		    }
		    return orderDtos;
			
		}
		
		
		
		//Gets Order By Account Number
		public OrdersDto getOrderByAccountNumberFromDao(String accountNumber)throws Exception{
			log.info("inside getOrdersByAccountNumberFromDao()");
			Orders order=this.dealSheetDao.getOrderByAccountNumber(accountNumber);
			OrdersDto orderDto=OrdersDto.populateOrderDto(order);
			return orderDto;
		}
	
	    
		
		public boolean findOrderTypeByAccountNumber(String accountNumber,Date orderDate)throws Exception{
			boolean result=false;
			log.info("inside findOrderTypeByAccountNumber()");
			Orders order=this.dealSheetDao.getOrderByAccountNumber(accountNumber);
			Calendar cal=Calendar.getInstance();
			cal.setTime(order.getDealStartDate());
			
			int term=Integer.parseInt(order.getTerm());
			cal.add(Calendar.MONTH, term-1);
			cal.add(Calendar.DATE, -1);
			Date exipiryDate=cal.getTime();
			if(orderDate.after(exipiryDate)){
				result=true;
			}
			return result;
			
		}
		
		/*
		 * Added by Jeevan on October 16,2013 to fine tune DB interactions and allow user to have account with same account# even before order renewal
		 */
		public boolean findOrderTypeByAccountNumber(OrdersDto orderDto,Date orderDate)throws Exception{
			boolean result=false;
			log.info("inside findOrderTypeByAccountNumber()");			
			Calendar cal=Calendar.getInstance();
			cal.setTime(orderDto.getDealStartDate());			
			int term=Integer.parseInt(orderDto.getTerm());
			cal.add(Calendar.MONTH, term-1);
			cal.add(Calendar.DATE, -1);
			Date exipiryDate=cal.getTime();
			if(orderDate.after(exipiryDate)){
				result=true;
			}
			return result;			
		}
		
		
		
		
		
		//Retreives states from DAO.
		public ArrayList<StateDto> getStatesFromDao() throws Exception{
			log.info("inside getStatesFromDao()");
			ArrayList<State> states=this.dealSheetDao.getStatesFromDB();
			ArrayList<StateDto> stateDtos=new ArrayList<StateDto>();
			for(State state:states){
				StateDto stateDto=new StateDto();
				stateDto.setId(state.getId());
				stateDto.setState(state.getState());
				stateDtos.add(stateDto);
			}
			return stateDtos;			
		}
		
		
		/*
		 * 
		 * Added by Jeevan on October 04, 2013.
		 * Method to fetch Billinh States
		 */		
		public ArrayList<BillingStateDto> getBillingStatesFromDao()throws StateNotFoundException{
			log.info("inside getBillingStatesFromDao()");
			ArrayList<BillingState> states=this.dealSheetDao.getBillingStatesFromDB();
			ArrayList<BillingStateDto> stateDtos=new ArrayList<BillingStateDto>();
			for(BillingState state: states){
				BillingStateDto stateDto=new BillingStateDto();
				stateDto.setId(state.getId());
				stateDto.setState(state.getState());
				stateDtos.add(stateDto);
			}
			return stateDtos;			
		}
		
		
		
		
		
	
		//gets Utility names based on utility namre
		public UtilityDto getUtilityByName(String utilityName)throws Exception{
			log.info("inside getUtilityByname()");
			Utility util=this.dealSheetDao.getUtilityByName(utilityName);
			UtilityDto utilDto=UtilityDto.populateUtility(util);
			return utilDto;
		}
		
		public UtilityDto getUtilityByID(Integer id)throws Exception{
			log.info("inside getUtilityByIDe()");
			Utility util=this.dealSheetDao.getUtilityByID(id);
			UtilityDto utilDto=UtilityDto.populateUtility(util);
			return utilDto;
		}
		
		/*
		 * Added by Jeevan on July 19,2013..
		 * 
		 * A method to get All the Customers Satisfied by Dynamic search condition and converts it into Dto
		 * 
		 * modified on Septemnber 23, to display Business Name instead of Customer Name
		 */
		public ArrayList<CustomerDto> getCustomersSatisfiedBySearchConditionsFfromDao(CustomerSearchDto search)throws Exception{
			log.info("inside getCustomersSatisfiedBySearchConditionsFfromDao()");
			ArrayList<Customer> customers=this.dealSheetDao.getCustomersSatisfiedBySearch(search);
			ArrayList<CustomerDto> customerDtos=new ArrayList<CustomerDto>();
			for(Customer customer:customers){
				
				CustomerDto customerDto=new CustomerDto();
				customerDto.setCustomerId(customer.getCustomerId());
				try{
					if(null!=search.getBusinessName() && search.getBusinessName()!=""){
						System.out.println(search.getBusinessName());
						customerDto.setFirstName(search.getBusinessName());
					}
					else{					
						customerDto.setFirstName(this.dealSheetDao.getBusinessNameOfACustomerFromCustomerId(customer));
					}
				}
				catch(Exception e){
					log.error("Fails to Get BusinessNAme()" +e.toString());
				}
				
				customerDto.setTaxId(customer.getTaxId());
				customerDtos.add(customerDto);
			}
			return customerDtos;
		}
		
		
		
		/*
		 * Created by Jeevan on October 16 2013. a method to get Total No of Accounts of Acc n Start Date
		 */		
		public Integer getNoOfOrdersBetweenDealStartDateAccount(String accountNumber,Date dealStartDate) throws Exception{
			log.info("inside getNoOfOrdersBetweenDealStartDateAccount()");
			Integer orders=this.dealSheetDao.getNoOfOrdersWithAccountNoAndStartDate(accountNumber, dealStartDate);
			return orders;
		}
		
		

		/*
		 * Created by Jeevan on October 16 2013. a method to get Total No of Accounts of Acc n Start Date
		 */		
		public String getOrdersBetweenDealStartDateAccount(String accountNumber,Date dealStartDate,Integer orderId) throws Exception{
			log.info("inside getNoOfOrdersBetweenDealStartDateAccount()");
			ArrayList<Integer> orders=this.dealSheetDao.getOrdersWithAccountNoandStartDate(accountNumber, dealStartDate);
			String result="success";
			for(Integer order:orders){				
				if(!orderId.equals(order)){
					result="fail";
				}
			}			
			return result;
		}
		
		
		/*
		 * Added by Jeevan on October 31st, 2013.
		 * 
		 * 1. Get Orders of a Deal
		 * 2. Get Rescinded Orders having same Orders
		 * 3. Get Agent Commissions with the Orders..
		 * 4. Delete Res Orders
		 * 5. Delete Agent Commissions
		 * 6. Delete Orders..
		 * 
		 */
		public boolean processDeleteDealSheet  (Integer transactionId)throws Exception{
			log.info("inside processDeleteDealSheet()");
			boolean deleteStatus=false;
			ArrayList<Orders> orders=this.dealSheetDao.getOrdersofATrandsaction(transactionId);
			if(!orders.isEmpty()){
				
				try{
					ArrayList<RescindedOrders> resOrders =this.adminDao.getRescindedOrdersOfOrders(orders);
					this.adminDao.deleteAllRescindedOrdersOfOrder(resOrders);
				}
				catch(Exception e){
					log.info("NO RESINCED ORDERS AMONG CURRENT ORDERS");
				}
				
				try{
					ArrayList<AgentCommissions> commissions=this.adminDao.getAgentCommissionsOfOrders(orders);
					this.adminDao.deleteAgentCommissionsOfOrders(commissions);
				}
				catch(Exception e){
					log.info("NO AGENT COMMISSIONS AMONG CURRENT ORDERS");
				}
				
				Integer i=this.dealSheetDao.deleteAllORders(orders);
				
				if(i>0){
					deleteStatus=true;					
				}	
			}
			return deleteStatus;			
		}
		
		
		
		/*
		 * Added on November 08,2013.
		 */
		public String removeORderFromDealToaNewDeal(Integer orderId)throws Exception{
			log.info("inside removeORderFromDealToaNewDeal()");
			String status="fail";
			Orders order=this.dealSheetDao.getOrderByOrderId(orderId);
			Transactions trans=new Transactions();
			  Transactions transactionId=this.dealSheetDao.addTransaction(trans);
			  order.setTransactionId(transactionId);
			  int result=this.dealSheetDao.saveDealSheetToDB(order);
			  if(result>0){
				  status="success";
			  }
			  return status;
		}
	
		
						
					
					
}
