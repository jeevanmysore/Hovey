/** 
 *  ** $Header:  DealSheetDaoImpl.java, M Jeevan Kumar, 18/04/2013
* Copyright (c) KNS Technologies Pvt. Ltd. 2013 All Rights Reserved
*
* NAME
* DealSheetDaoImpl.java
* USAGE
* Implementation of DealSheetDao, it contains all the methods to interact with database to manipulate Deal Sheet Operations..
* 
* DESCRIPTION
* It is used to create Deal Sheet details table in Database
* CHANGES (most recent first)
* 
*    1. added getCustomerByTaxId(String taxId)  22/04/2013
*    
*    
* M JEEVAN KUMAR, 22/04/2013 modified
 * */

package com.hovey.backend.agent.dao;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.criteria.Order;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.hovey.backend.agent.exception.CustomersNotFoundException;
import com.hovey.backend.agent.exception.MoreAccountsForAccountDealDateException;
import com.hovey.backend.agent.exception.MoreAccountsForAccountKwhTermException;
import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.agent.exception.OrdersExistForAccountException;
import com.hovey.backend.agent.exception.StateNotFoundException;
import com.hovey.backend.agent.model.BillingState;
import com.hovey.backend.agent.model.Customer;
import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.agent.model.State;
import com.hovey.backend.agent.model.Transactions;
import com.hovey.backend.agent.model.Utility;
import com.hovey.backend.user.model.HoveyUser;
import com.hovey.frontend.agent.dto.CustomerSearchDto;

@Transactional
@Repository("dealSheetDao")
public class DealSheetDaoImpl implements DealSheetDao {

	/**
	 * @author JEEVAN
	 */
	
	private static Logger log =Logger.getLogger(DealSheetDaoImpl.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	
	// Saving Deal Sheets....
	/* 
	 * has two steps.
	 * 
	 * 1. saves(new) or updates(Existing) customer
	 * 
	 * 2. saves deal sheet
	 * */
	
	public int saveDealSheetToDB(Orders order)throws Exception{	
		log.info("inside saveDealSheetToDb()");
		hibernateTemplate.saveOrUpdate(order);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();	
		return order.getOrderId();
	}
	
	//Saves Collection of Accounts in the DealSheet..
	public void saveDealSheetOrdersToDB(ArrayList<Orders> orders)throws Exception{
		log.info("inside saveDealSheetOrdersToDb()");
		hibernateTemplate.saveOrUpdateAll(orders);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();		
	}
	
	
	/*
	 * Added by Jeevan on 25 July,2013..
	 */
	@SuppressWarnings("unchecked")
	public State getStateByStateName(String stateName)throws StateNotFoundException{
		log.info("inside getStateByStateName()");
		ArrayList<State> states=(ArrayList<State>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(State.class)
				.add(Restrictions.eq("state", stateName).ignoreCase()).list();
		if(!states.isEmpty()){
			return states.get(0);
		}
		else{
			throw new StateNotFoundException();
		}				
	}
	
	
	/*
	 * Added by Jeevan on October 04,2013..
	 */
	@SuppressWarnings("unchecked")
	public BillingState getBillingStateByStateName(String stateName)throws StateNotFoundException{
		log.info("inside getStateByStateName()");
		ArrayList<BillingState> states=(ArrayList<BillingState>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(BillingState.class)
				.add(Restrictions.eq("state", stateName).ignoreCase()).list();
		if(!states.isEmpty()){
			return states.get(0);
		}
		else{
			throw new StateNotFoundException();
		}				
	}
	
	
	
	
	//saves Customer to DB.
	public int saveCustomerToDB(Customer customer)throws Exception{		
		log.info("inside saveCusotmerToDB()");
		hibernateTemplate.saveOrUpdate(customer);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		return customer.getCustomerId();		
	}
	
	
	//Saves Collection of Accounts in the DealSheet..
		public Integer editDealSheetOrdersToDB(ArrayList<Orders> orders)throws Exception{
			log.info("inside saveDealSheetOrdersToDb()");
			hibernateTemplate.saveOrUpdateAll(orders);
			hibernateTemplate.getSessionFactory().getCurrentSession().flush();	
			return orders.get(0).getOrderId();
		}
	
	
		/*
		 * Added by Jeevan on Aug 16,2013, Keeping in View of Editing Deal Sheet.
		 */
		@SuppressWarnings("unchecked")
		public Transactions getTransactionById(int transactionId)throws Exception{
			log.info("inside getTransactionByID()");
			ArrayList<Transactions> trans=(ArrayList<Transactions>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Transactions.class)
					.add(Restrictions.eq("id", transactionId)).list();
			if(!trans.isEmpty()){
				return trans.get(0);
			}
			else{
				throw new Exception();
			}			
		}
	
	
	//Retreives all the Customers registered by a particular Agent..
	@SuppressWarnings("unchecked")
	public ArrayList<Customer> getCustomersOfAgent(String agentNumber)throws Exception{
		log.info("inside getCustomersOfAgent()");
		ArrayList<Customer> customers =(ArrayList<Customer>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Customer.class)
				.add(Restrictions.eq("agent.agentNumber", agentNumber)).list();
		if(customers.isEmpty()){
			throw new CustomersNotFoundException();
		}
		else{
			return customers;
		}		
	}
	
	
	//Retreives all the Customers r
		@SuppressWarnings("unchecked")
		public ArrayList<Customer> getCustomers()throws Exception{
			log.info("inside getCustomersOfAgent()");
			ArrayList<Customer> customers =(ArrayList<Customer>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Customer.class)
					.list();
			if(customers.isEmpty()){
				throw new CustomersNotFoundException();
			}
			else{
				return customers;
			}		
		}
	
	
	
	// fetches customer based on taxID
	public Customer getCustomerByTaxId(String taxId)throws Exception{
		log.info("inside getCustomerByTaxId");
		Customer customer=(Customer) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Customer.class)
				.add(Restrictions.eq("taxId",taxId)).list().get(0);
		if(null!=customer)
			return customer;
		else
			throw new CustomersNotFoundException();
	}
	
	//gets User by Agent Number
	public HoveyUser getUserByAgentNumber(String agentNumber)throws Exception{
		log.info("inside getUserByAgentNumber()");
		HoveyUser user=(HoveyUser) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class)
				.add(Restrictions.eq("agentNumber",agentNumber)).list().get(0);
		return user;
	}
	
	
	//gets Utility by UtilityName;
	public Utility getUtilityByName(String utility)throws Exception{
		log.info("inside getUtilityByName()");
		Utility util=(Utility) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Utility.class)
				.add(Restrictions.eq("utility",utility)).list().get(0);
		return util;
		
	}
	
	
	//getting all utilities
	public ArrayList<Utility> getUtilities(){
		log.info("inside getUtilities()");
		try{
			@SuppressWarnings("unchecked")
			ArrayList<Utility> utilties=(ArrayList<Utility>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Utility.class)
													.add(Restrictions.eq("isEnabled", true))
													.list();
			return utilties;
		}
		catch(Exception e){
			log.error("Error in getting utilities");
			return null;
		}
		
	}
	
	
	public ArrayList<Utility> getAllUtilities(){
		log.info("inside getUtilities()");
		try{
			@SuppressWarnings("unchecked")
			ArrayList<Utility> utilties=(ArrayList<Utility>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Utility.class)
													
													.list();
			return utilties;
		}
		catch(Exception e){
			log.error("Error in getting utilities");
			return null;
		}
		
	}
	
	
	//gets User Based on username.
	
	public HoveyUser getUserByUsername(String username)throws Exception{
		log.info("inside getUserByUsername()");
		HoveyUser user=(HoveyUser) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class)
				.add(Restrictions.eq("username",username)).list().get(0);
		return user;
	}
	
	
	public int saveUtility(Utility utility)throws Exception{
		log.info("inside saveUtility()");
		Assert.notNull(utility);
		hibernateTemplate.saveOrUpdate(utility);
		return utility.getId();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Utility getUtilityByID(Integer id)throws Exception{
		log.info("inside getUtilityByID()");
		
		ArrayList<Utility> utilities =(ArrayList<Utility>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Utility.class)
				.add(Restrictions.eq("id", id))
				.list();
		
		if(!utilities.isEmpty()){
			return utilities.get(0);
		}
		else{
			throw new Exception();
		}
		
	}
	
	//gets Orders
	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getOrders()throws Exception{
		log.info("inside getOrders()");
		ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				.list();
		return orders;
	}
	
	
	//gets Orders by Agent Number
		@SuppressWarnings("unchecked")
		public ArrayList<Orders> getOrdersofCustomers(Customer taxId) throws Exception{
			log.info("inside getOrdersofAgent()");
			Assert.notNull(taxId);
			ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
					.add(Restrictions.eq("taxId",taxId)).list();
			if(orders.isEmpty()){
				throw new OrderNotFoundException();
			}
			else{
				return orders;
			}
		}
		
		//gets order by order Id.
		public Orders getOrderById(int orderId)throws Exception{
		     log.info("inside getOrderById()");
		     Orders order=(Orders) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Order.class)
		    		 .add(Restrictions.eq("orderId",orderId)).list().get(0);
		     return order;
			
		}
		
		//add transaction number each time..
		public Transactions addTransaction(Transactions trans)throws Exception{
			log.info("inside addTransaction()");
			hibernateTemplate.save(trans);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		return trans;
		}
	
		
		
		//getting orders by agentNumber
		@SuppressWarnings("unchecked")
		public ArrayList<Orders> getOrdersOfAgent(String agentNumber)throws Exception{
			log.info("inside getOrdersofAgent()");
			ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
					.add(Restrictions.eq("agent.agentNumber", agentNumber)).list();
			if(orders.isEmpty()){
				throw new OrderNotFoundException();
			}
			else{
				return orders;
			}			
		}
		
		//retreives all the Orders groupby Transaction
		@SuppressWarnings("unchecked")
		public ArrayList<Orders> getOrdersGroupByTransaction()throws Exception{
			log.info("inside getOrdersGroupByTransaction()");
			ArrayList<Transactions> transactions= (ArrayList<Transactions>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
			.setProjection(Projections.projectionList().add(Projections.groupProperty("transactionId"))).list();
			
			ArrayList<Orders> orders=new ArrayList<Orders>();
			for(Transactions trans:transactions){
				ArrayList<Orders> custOrders=this.getOrdersofATrandsaction(trans.getId());
				orders.add(custOrders.get(0));
			}
			return orders;
		}
		
		
		//fetches Orders of a particular transaction Id;
		@SuppressWarnings("unchecked")
		public ArrayList<Orders> getOrdersofATrandsaction(int transactionId)throws Exception{
			log.info("inside getOrdersOfATransaction()");
			ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
					.add(Restrictions.eq("transactionId.id", transactionId)).list();
			if(orders.isEmpty()){
				throw new OrderNotFoundException();
			}
			else{
				return orders;
			}
		}
		
		
		/**
		 * Created by Jeevan on Nov 19, 2014
		 * Method to get Non Rescinded Orders of a Deal..
		 */
		@SuppressWarnings("unchecked")
		public ArrayList<Orders> getNonRescindedOrdersofATrandsaction(int transactionId)throws Exception{
			log.info("inside getNonRescindedOrdersofATrandsaction()");
			ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
					.add(Restrictions.eq("transactionId.id", transactionId))
					.add(Restrictions.ne("status", "rescinded"))
					.list();
			if(orders.isEmpty()){
				throw new OrderNotFoundException();
			}
			else{
				return orders;
			}
		}
		
		
		
		
		
		
		
		
		
		//retreives all the Agent Orders groupby Transaction
				@SuppressWarnings("unchecked")
				public ArrayList<Orders> getAgentOrdersGroupByTransaction(String agentNumber)throws Exception{
					log.info("inside getAgentOrdersGroupByTransaction()");
					ArrayList<Transactions> transactions= (ArrayList<Transactions>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
					.add(Restrictions.eq("agent.agentNumber", agentNumber))
					.setProjection(Projections.projectionList().add(Projections.groupProperty("transactionId"))).list();
					
					
					ArrayList<Orders> orders=new ArrayList<Orders>();
					for(Transactions trans:transactions){
						ArrayList<Orders> custOrders=this.getOrdersofATrandsaction(trans.getId());
						orders.add(custOrders.get(0));
						
					}
					return orders;
				}
		
				
	// Getting Order by OrderId
	 public Orders getOrderByOrderId(int orderId) throws Exception{
		 log.info("inside getOrderbyOrderId()");
		 Orders order=(Orders) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				 .add(Restrictions.eq("orderId", orderId)).list().get(0);
		 if(null!=order)
			 return order;
		 else
			throw new OrderNotFoundException();
	 }
	 
	 
	 
	 //gets all the Deal Sheets groupby Transaction
	 @SuppressWarnings("unchecked")
		public ArrayList<Orders> getDealSheetsGroupByTransaction()throws Exception{
			log.info("inside getAgentOrdersGroupByTransaction()");
			ArrayList<Transactions> transactions= (ArrayList<Transactions>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
			.setProjection(Projections.projectionList().add(Projections.groupProperty("transactionId"))).list();
			
			
			ArrayList<Orders> orders=new ArrayList<Orders>();
			
			for(Transactions trans:transactions){
				ArrayList<Orders> custOrders=this.getOrdersofATrandsaction(trans.getId());
				orders.add(custOrders.get(0));
				
			}
			
			return orders;
		}
	 
	 
	 
	 //Returns Orders of a Particular Account Number.....
	 @SuppressWarnings("unchecked")
	public Orders getOrderByAccountNumber(String accountNumber) throws OrderNotFoundException{
		 log.info("inside getOrderByAccountNumber()");
		 ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				 .add(Restrictions.eq("accountNumber", accountNumber)).list();
		 if(orders.isEmpty()){
			 throw new OrderNotFoundException();
		 }
		 else{
			/* Modified on 20 July 2013 in order to handle Multiple Accounts Condition*/ 
			 return orders.get(orders.size()-1);
		 }
		 
	 }
	 
	 /*
	  * 
	  * Added by Jeevan on July 23,2013. to handle muliptle accounts with same acc no..
	  * This Condition is  used while uploading Supplier Reports.. the Acc# n DealStartDate in Supplier Reports must match with details in Pipeline in order for the Records to be loaded.
	  */
	 //Returns Orders of a Particular Account Number.....
	 @SuppressWarnings("unchecked")
	public Orders getOrderByAccountNumberandStartDate(String accountNumber, Date dealStartDate) throws OrderNotFoundException{
		 log.info("inside getOrderByAccountNumber()");
		 ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				 .add(Restrictions.eq("accountNumber", accountNumber))
				 .add(Restrictions.eq("dealStartDate", dealStartDate))
				 .list();
		 if(orders.isEmpty()){
			 throw new OrderNotFoundException();
		 }
		 else{
			/* Modified on 20 July 2013 in order to handle Multiple Accounts Condition*/ 
			 return orders.get(orders.size()-1);
		 }		 
	 }
	 
	 
	 /*
	  * Created by Jeevan on October 16,2013. A method to get No Of Orders between the Account Number and Deal Start Date
	  * 
	  */
	 public Integer getNoOfOrdersWithAccountNoAndStartDate(String accountNumber,Date dealStartDate)throws OrderNotFoundException{
		 log.info("inside getNoOfOrdersWithAccountNoAndStartDate()");
		 
		 Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				 .add(Restrictions.and(Restrictions.eq("accountNumber", accountNumber.trim()),Restrictions.eq("dealStartDate", dealStartDate)));		 
		 criteria.setProjection(Projections.count("orderId"));
		 Integer orders=(Integer) criteria.list().get(0);
		 return orders; 
	 }
	 
	 
	 /*
	  * 
	  * Created by Jeevan on October 28,2013.. for validating Accounts for Deal Edit Case..
	  */	
	 @SuppressWarnings("unchecked")
	public ArrayList<Integer> getOrdersWithAccountNoandStartDate(String accountNumber,Date dealStartDate)throws OrderNotFoundException{
		 log.info("inside getOrdersWithAccountNoandStartDate()");
		 ArrayList<Integer> orderIds=new ArrayList<Integer>();
		 
		 Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
		 criteria.add(Restrictions.and(Restrictions.eq("accountNumber", accountNumber.trim()), Restrictions.eq("dealStartDate", dealStartDate)));
		 criteria.add(Restrictions.ne("status", "rescinded"));
		 criteria.setProjection(Projections.property("orderId"));		
		 
		 orderIds=(ArrayList<Integer>) criteria.list();
		 if(!orderIds.isEmpty()){
			 return orderIds;
	 	}
		 else{
			 throw new OrderNotFoundException();
		 }
	 }
	 
	 
	 
	 
	 
	 
	 
	 // getting List of States.
	 @SuppressWarnings("unchecked")
	public ArrayList<State> getStatesFromDB()throws Exception{
		 log.info("getStatesFromDB()");
		 ArrayList<State> states=(ArrayList<State>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(State.class)
				 .addOrder(org.hibernate.criterion.Order.asc("id")).list();
		 if(!states.isEmpty())
			 return states;
		 else
			 throw new StateNotFoundException();
	 }
	 
	 
	 
	 /*
	  * Added by Jeevan on October 04, 2013.
	  * Method to get Business States..
	  */
	 @SuppressWarnings("unchecked")
	public ArrayList<BillingState> getBillingStatesFromDB() throws StateNotFoundException{
		 log.info("getBilliungStatesFromDB()");
		 ArrayList<BillingState> states=(ArrayList<BillingState>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(BillingState.class)
				 .addOrder(org.hibernate.criterion.Order.asc("id")).list();
		 if(!states.isEmpty())
			 return states;
		 else{
			 throw new StateNotFoundException();
		 }	 
	 }
	 
	 
	 
	 
	 
	 
	
	 /* **
	  * Added by Jeevan on July,19 2013..
	  * 
	  * Performs Search for customer on various fields...
	  * 
	  * *******/
	 @SuppressWarnings("unchecked")
	public ArrayList<Customer> getCustomersSatisfiedBySearch(CustomerSearchDto search)throws Exception{
		 log.info("inside getCustomersSatisfiedBySearch()");
		 Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Customer.class);
		 
		 if(null!=search.getFirstName() && search.getFirstName()!= ""){
			 criteria.add(Restrictions.eq("firstName", search.getFirstName()));
		 }
		 
		 if(null!=search.getLastName() && search.getLastName()!= ""){
			 criteria.add(Restrictions.eq("lastName", search.getLastName()));
		 }
		 
		 if(null!=search.getTaxId() && search.getTaxId()!= ""){
			 criteria.add(Restrictions.eq("taxId", search.getTaxId()));
		 }
		 
		 if(null!=search.getPhoneNo() && search.getPhoneNo()!= ""){
			 criteria.add(Restrictions.eq("phoneNo", search.getPhoneNo()));
		 }
		 
		 if(null!=search.getBusinessName() && search.getBusinessName()!=""){
			  Orders order=this.getOrderByBusinessName(search.getBusinessName());
			  System.out.println("Ordeeer"+order.getOrderId());
			  criteria.add(Restrictions.eq("customerId", order.getTaxId().getCustomerId()));
		 }
		 
		 ArrayList<Customer> customers=(ArrayList<Customer>) criteria.list();
		 if(customers.isEmpty()){
			 throw new CustomersNotFoundException();
		 }
		 else{
			 return customers;
		 }		 
		 
	 }
	 
	 /*
	  * Added by Jeevan on July 19,2013.
	  * 
	  * Gets Order by Business Name
	  * 
	  */
	 @SuppressWarnings("unchecked")
	public Orders getOrderByBusinessName(String businessName)throws OrderNotFoundException{
		 log.info("inside getOrderByBusinessName()");
		 ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				 .add(Restrictions.eq("businessName", businessName)).list();
		 if(!orders.isEmpty()){
			 return orders.get(0);
		 }
		 else{
			 throw new OrderNotFoundException();
		 }
	 }
	 
	
	 /*
	  * 
	  * Added on September 23,2013 by Jeevan
	  * (non-Javadoc)
	  * @see com.hovey.backend.agent.dao.DealSheetDao#getBusinessNameOfACustomerFromCustomerId(com.hovey.backend.agent.model.Customer)
	  */
	 
	@SuppressWarnings("unchecked")
	public String getBusinessNameOfACustomerFromCustomerId(Customer taxId)throws OrderNotFoundException{
		log.info("getBusinessNameOfACustomerFromCustomerId()");
		 String businessName=null;
		ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				.add(Restrictions.eq("taxId", taxId))
				.list();
	 
	  if(!orders.isEmpty()){
		  businessName=orders.get(0).getBusinessName();
		  return businessName;
	  }
	  else{
		  throw new OrderNotFoundException();
	  }	  
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public Orders getOrderByAccountNumberBusinessNameorTerm(String accountNumber,String businessName,String term)throws OrderNotFoundException{
		log.info("inside getOrderByAccountNumberBusinessNameorTerm()" );
		
		Criteria criteria =hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class).add(Restrictions.eq("accountNumber", accountNumber));
		criteria.add(Restrictions.or(Restrictions.eq("businessName", businessName).ignoreCase(), Restrictions.eq("term", term)));		
		ArrayList<Orders> orders=(ArrayList<Orders>) criteria.list();		
		if(!orders.isEmpty()){
			return orders.get(0);
		}
		else{
			throw new OrderNotFoundException();
		}
	}
	
	
	/*
	 * Deleting All Orders
	 * Added on October 31, by Jeevan
	 */
	
	public Integer deleteAllORders(ArrayList<Orders> orders) throws Exception{
		log.info("inisde deleteOrders()");
		hibernateTemplate.deleteAll(orders);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		return orders.get(0).getOrderId();
	}
	
	
	/**
	 * 
	 * Set of ORders Checks Based on Content of Supplier Reports **	
	 ********/
	/*
	 * 1. Method to Get Orders By Account No
	 */
	
	public Integer getAccountsCountByAccountNo(String accountNo) throws OrderNotFoundException{
		log.info("inside getAccountsCountByAccountNo()");		
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
		criteria.add(Restrictions.eq("accountNumber", accountNo.trim()));		
		criteria.setProjection(Projections.count("orderId"));		
		Integer result=(Integer) criteria.list().get(0);		
		if(result<1){
			throw new OrderNotFoundException();
		}
		else{
			return result;
		}
			
	}
	
	
	//2 . Method to Get Accounts By Account# Deal Start Date
	public Integer getAccountsCountByAccountNoDealDate(String accountNo, Date dealStartDate) throws OrderNotFoundException{
		log.info("inside getAccountsCountByAccountNo()");		
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
		criteria.add(Restrictions.eq("accountNumber", accountNo.trim()));	
		criteria.add(Restrictions.eq("dealStartDate", dealStartDate));
		criteria.setProjection(Projections.count("orderId"));		
		Integer result=(Integer) criteria.list().get(0);		
		if(result<1){
			throw new OrderNotFoundException();
		}
		else {
			return result;
		}
			
	}
	
	
	
	//3 . Method to Get Accounts By Account#  Term and kWh
		public Integer getAccountsCountByKwHTerm(String accountNo,Integer kWh,String term) throws OrderNotFoundException{
			log.info("inside getAccountsCountByAccountNo()");		
			Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
			criteria.add(Restrictions.eq("accountNumber", accountNo.trim()));	
			criteria.add(Restrictions.eq("term", term));
			criteria.add(Restrictions.eq("kwh", kWh));
			criteria.setProjection(Projections.count("orderId"));		
			Integer result=(Integer) criteria.list().get(0);		
			if(result<1){
				throw new OrderNotFoundException();
			}
			else {
				return result;
			}
				
		}
		
		
	
	
	  public Orders getOrderByAccountNoKwhandTerm(String accountNo,Integer kwh,String term) throws OrderNotFoundException{
		  log.info("inside getOrderByAccountNoKwhandTerm()");
		  Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
			criteria.add(Restrictions.eq("accountNumber", accountNo.trim()));	
			criteria.add(Restrictions.eq("term", term));
			criteria.add(Restrictions.eq("kwh", kwh));
			ArrayList<Orders> orders=(ArrayList<Orders>) criteria.list();
			if(!orders.isEmpty()){
				return orders.get(0);
			}
			else{
				throw new OrderNotFoundException();
			}			
	  }
		
		
	
	
	
	/************************************************************************************************ *******/
	
	
	
	
	
	
	
	
	

}
