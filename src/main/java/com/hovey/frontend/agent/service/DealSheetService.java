package com.hovey.frontend.agent.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.hovey.backend.agent.exception.StateNotFoundException;
import com.hovey.frontend.agent.dto.BillingStateDto;
import com.hovey.frontend.agent.dto.ContractTypeDto;
import com.hovey.frontend.agent.dto.CustomerDto;
import com.hovey.frontend.agent.dto.CustomerSearchDto;
import com.hovey.frontend.agent.dto.DealSheetForm;
import com.hovey.frontend.agent.dto.OrdersDto;
import com.hovey.frontend.agent.dto.StateDto;
import com.hovey.frontend.agent.dto.UtilityDto;
import com.hovey.frontend.user.dto.HoveyUserDto;

public interface DealSheetService {

	public ArrayList<CustomerDto> getCustomerByAgentNumberFromDb(String agentNumber)throws Exception;
	public CustomerDto getCustomerByTaxIdFromDao(String taxId) throws Exception;
	public int saveCustomer(int id,String firstName, String lastName, String email,String title,String phoneNo,String taxId,String fax,Boolean taxExempt)throws Exception;
	
	public int saveDealSheet(Date orderDate,long tpv,String rateClass,String utility,double rate,int term,String businessName,String dba,String accuount,
			int kwh,String serviceStreet,String serviceCity,String serviceUnit,String serviceState,String serviceZip,String billingStreet,String billingCity,String billingUnit,
			String billingState,String billingZip,String taxId)throws Exception;
	public ArrayList<UtilityDto> getUtilitiesFromDb()throws Exception;
	public ArrayList<UtilityDto> getAllUtilitiesFromDb()throws Exception;
	public String validateAccountNo(String acc,String utility)throws Exception;
	public  Date formatDate(String dateString) throws ParseException;
	public void saveDealSheetToDB(DealSheetForm dealSheet,String agentNumber)throws Exception;
	public HoveyUserDto getUserByUserNamefromDao(String username) throws Exception;
	public int saveUtilityToDB(String utilityName,int length)throws Exception;
	public ArrayList<OrdersDto> getOrdersFromDB()throws Exception;
	public ArrayList<OrdersDto> getOrdersofAgentFromDB(String agentNumber) throws Exception;
	public ArrayList<CustomerDto> getCustomersFromDb()throws Exception;
	public Map<String, Object> getSortedCustomerDetails(ArrayList<CustomerDto> customerDtos)throws Exception;
	public ArrayList<OrdersDto> getDealSheetsofAgentFromDB(String agentNumber) throws Exception;
	public ArrayList<OrdersDto> getDealSheetByTransactionIdFromDB(int transactionId)throws Exception;
	public OrdersDto getOrderByOrderIdFromDB(int orderId)throws Exception;
	public ArrayList<OrdersDto> getDealSheetsFromDB() throws Exception;
	public OrdersDto getOrderByAccountNumberFromDao(String accountNumber)throws Exception;
	public ArrayList<StateDto> getStatesFromDao() throws Exception;
	public ArrayList<BillingStateDto> getBillingStatesFromDao()throws StateNotFoundException;
	public Date populateStartDate(String startDate);
	public int editDealSheetToDB(DealSheetForm dealSheet)throws Exception;
	public boolean findOrderTypeByAccountNumber(String accountNumber,Date orderDate)throws Exception;
	public UtilityDto getUtilityByName(String utilityName)throws Exception;
	public UtilityDto getUtilityByID(Integer id)throws Exception;
	public int editUtility(String utilityName,int length,int id)throws Exception;
	public int enableDisable(Integer id,String status)throws Exception;
	public Date getEndDateFromStartDateandTerm(Date dealStartDate,String term);
	public ArrayList<CustomerDto> getCustomersSatisfiedBySearchConditionsFfromDao(CustomerSearchDto search)throws Exception;
	public Integer getNoOfOrdersBetweenDealStartDateAccount(String accountNumber,Date dealStartDate) throws Exception;
	public String getOrdersBetweenDealStartDateAccount(String accountNumber,Date dealStartDate,Integer orderId) throws Exception;

	public boolean processDeleteDealSheet(Integer transactionId)throws Exception;
	public String removeORderFromDealToaNewDeal(Integer orderId)throws Exception;
	
	public ArrayList<OrdersDto> getNonRescindedDealSheetByTransactionIdFromDB(int transactionId)throws Exception;
	
	
	
}
