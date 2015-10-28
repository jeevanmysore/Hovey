/** 
 *  ** $Header:  DealSheetDao.java, M Jeevan Kumar, 18/04/2013
* Copyright (c) KNS Technologies Pvt. Ltd. 2013 All Rights Reserved
*
* NAME
* DealSheetDao.java
* USAGE
* Interface of DealSheetDao, it contains all the methods to interact with database to manipulate Deal Sheet Operations..
* 
* DESCRIPTION
* It is used to create Deal Sheet details table in Database
* CHANGES (most recent first)
* M JEEVAN KUMAR, 18/04/2013
 * */


package com.hovey.backend.agent.dao;

import java.util.ArrayList;
import java.util.Date;

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

public interface DealSheetDao {
	/**
	 * @author JEEVAN
	 */
	public int saveDealSheetToDB(Orders order)throws Exception;
	public int saveCustomerToDB(Customer customer)throws Exception;
	public ArrayList<Customer> getCustomersOfAgent(String agentNumber)throws Exception;
	public Customer getCustomerByTaxId(String taxId)throws Exception;
	public HoveyUser getUserByAgentNumber(String agentNumber)throws Exception;
	public Utility getUtilityByName(String utility)throws Exception;
	public ArrayList<Utility> getUtilities()throws Exception;
	public ArrayList<Utility> getAllUtilities();
	public void saveDealSheetOrdersToDB(ArrayList<Orders> orders)throws Exception;
	public HoveyUser getUserByUsername(String username)throws Exception;
	public int saveUtility(Utility utility)throws Exception;
	public Utility getUtilityByID(Integer id)throws Exception;
	public ArrayList<Orders> getOrders()throws Exception;
	public ArrayList<Orders> getOrdersofCustomers(Customer taxId) throws Exception;
	public ArrayList<Customer> getCustomers()throws Exception;
	public Transactions addTransaction(Transactions trans)throws Exception;
	public ArrayList<Orders> getOrdersOfAgent(String agentNumber)throws Exception;
	public ArrayList<Orders> getOrdersGroupByTransaction()throws Exception;
	public ArrayList<Orders> getOrdersofATrandsaction(int transactionId)throws Exception;
	public ArrayList<Orders> getAgentOrdersGroupByTransaction(String agentNumber)throws Exception;
	public Orders getOrderByOrderId(int orderId) throws Exception;
	public ArrayList<Orders> getDealSheetsGroupByTransaction()throws Exception;
	public Orders getOrderByAccountNumber(String accountNumber) throws Exception;
	public ArrayList<State> getStatesFromDB()throws Exception;
	public ArrayList<BillingState> getBillingStatesFromDB() throws StateNotFoundException;
	public Integer editDealSheetOrdersToDB(ArrayList<Orders> orders)throws Exception;
	public ArrayList<Customer> getCustomersSatisfiedBySearch(CustomerSearchDto search)throws Exception;
	public Orders getOrderByAccountNumberandStartDate(String accountNumber, Date dealStartDate) throws OrderNotFoundException;
	public State getStateByStateName(String stateName)throws StateNotFoundException;
	public BillingState getBillingStateByStateName(String stateName)throws StateNotFoundException;
	 public Integer getNoOfOrdersWithAccountNoAndStartDate(String accountNumber,Date dealStartDate)throws OrderNotFoundException;
	 public ArrayList<Integer> getOrdersWithAccountNoandStartDate(String accountNumber,Date dealStartDate)throws OrderNotFoundException;
	
	public Transactions getTransactionById(int transactionId)throws Exception;
	
	public String getBusinessNameOfACustomerFromCustomerId(Customer taxId)throws OrderNotFoundException;
	
	//for testing
	public Orders getOrderByAccountNumberBusinessNameorTerm(String accountNumber,String businessName,String term)throws OrderNotFoundException;
	
	
	public Integer deleteAllORders(ArrayList<Orders> orders) throws Exception;
	
	
	public Integer getAccountsCountByAccountNo(String accountNo) throws OrderNotFoundException;
	public Integer getAccountsCountByAccountNoDealDate(String accountNo, Date dealStartDate) throws OrderNotFoundException;
	public Integer getAccountsCountByKwHTerm(String accountNo,Integer kWh,String term) throws OrderNotFoundException;
	public Orders getOrderByAccountNoKwhandTerm(String accountNo,Integer kwh,String term) throws OrderNotFoundException;
	
	//added on November 19, 2014
	public ArrayList<Orders> getNonRescindedOrdersofATrandsaction(int transactionId)throws Exception;
	
}
