package com.hovey.backend.admin.dao;

import java.util.ArrayList;

import java.util.Date;







import com.hovey.backend.admin.exception.AgentCommissionNotFoundException;
import com.hovey.backend.agent.exception.ContractTypeNotFoundException;
import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.agent.model.AgentCommissions;
import com.hovey.backend.agent.model.ContractTypes;
import com.hovey.backend.agent.model.KwhLimit;
import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.agent.model.RescindedOrders;
import com.hovey.backend.agent.model.State;
import com.hovey.backend.agent.model.Transactions;
import com.hovey.backend.agent.model.Utility;
import com.hovey.backend.user.model.HoveyUser;
import com.hovey.frontend.admin.dto.PipelineSearchDto;

public interface AdminDao {

	public int getTotalOrders()throws Exception;
	public ArrayList<Orders> getOrdersFromDB(int pageNo,String orderProperty,int range)throws Exception;
	public ArrayList<Orders> getCompleteOrders()throws Exception;
	public ArrayList<Orders> getCompleteOrdersForDashboard()throws Exception;
	
	
	
	public int getTotalNoofDealSheets(String searchBy)throws Exception;
	public ArrayList<Transactions> getTotalDealSheets(int pageNo,int pageSize,String sortBy,String searchBy)throws Exception;
	public Orders getFirstOrderOfaTransaction(int transactionId)throws Exception;
	public <T> int getTotalNoOfOrdersOfaFilter(String propertyName,T propertyValue,int pageNo,String orderProperty, int range)throws Exception;
	public <T> ArrayList<Orders> getOrdersFilteredByProperty(String propertyName,T propertyValue,int pageNo,String orderProperty,int range)throws Exception;
	public Integer getTotlOrdersofaDealByDealId(Integer transactionId)throws OrderNotFoundException;
	public ArrayList<Orders> getMultiSearchResults(PipelineSearchDto search,int pageNo,int range,String sortElement)throws Exception;
	public int getTotalMultiSearchResults(PipelineSearchDto search,int pageNo,int range,String sortElement)throws Exception;


 /// For Dynamic Search....
	public ArrayList<Utility> getSuppliers() throws Exception;
	public ArrayList<HoveyUser> getAgents()throws Exception;
	public ArrayList<String> getBusinessNames()throws Exception;
	public Orders getOrderByOrderId(int orderId) throws Exception;
	public Integer editPipelineData(Orders order) throws Exception;
	
	/** FRom Praga*/
	public ArrayList<Orders> getOrdersBetweenDays(String startDate,
			String endDate) throws Exception;

	
	 public void deleteState(State state)throws Exception;
	 public void addState(State state)throws Exception;
	 public ArrayList<Orders> getOrdersByUtility(String utility)throws OrderNotFoundException;
	 public ArrayList<Orders> getOrdersByState(String state)throws OrderNotFoundException;
	 public ArrayList<Orders> getOrdersOfaSupplier(String supplierName)throws OrderNotFoundException;
	 public ArrayList<Orders> getOrdersBetweenDays(Date startDate,Date endDate)throws OrderNotFoundException;
	 public ArrayList<Orders> getActiveOrdersBetweenDays(Date startDate,Date endDate)throws OrderNotFoundException;
	 public ArrayList<Orders> getOrdersByPaidDateBetweenDays(Date startDate,Date endDate)throws OrderNotFoundException;
	 //Rescinded Accounts..
	 public Integer saveOrUpdateRescindedAccount(RescindedOrders orders)throws Exception;
	 public Integer deleteRescindedOrder(RescindedOrders order) throws Exception;
	 public ArrayList<RescindedOrders> getAllRescindedOrders()throws OrderNotFoundException;
	 public RescindedOrders getRescindedORderByID(Integer orderId)throws OrderNotFoundException;
	 public RescindedOrders getRescindedORderByOrderID(Integer orderId)throws OrderNotFoundException;
	 public ArrayList<RescindedOrders> getAllRescindedOrdersSatisfyingCriteria(boolean refundStatus,ArrayList<HoveyUser> agents)throws OrderNotFoundException;
	 public ArrayList<RescindedOrders> getRescindedOrdersOfOrders(ArrayList<Orders> orders)throws OrderNotFoundException;
	 public Integer deleteAllRescindedOrdersOfOrder(ArrayList<RescindedOrders> resOrders)throws Exception;
		public Integer updateKwhLimit(KwhLimit kwh)throws Exception;
		public  KwhLimit getKwhLimit()throws Exception;
				
	//agent Commissions
	 public Integer saveOrUpdateAgentCommissions(AgentCommissions commissions)throws Exception;
	 public Integer saveOrUpdateAgentCommissions(ArrayList<AgentCommissions> commissions)throws Exception;
	 public ArrayList<AgentCommissions> getAgentCommissions()throws AgentCommissionNotFoundException;
	 public ArrayList<AgentCommissions> getAgentCommissionsOfRescindedAccounts(ArrayList<Orders> orders)throws AgentCommissionNotFoundException;
	 public ArrayList<AgentCommissions> getAgentCommissionsByWeek(String agentNumber,Integer week,Integer year)throws AgentCommissionNotFoundException;
	 public ArrayList<AgentCommissions> getAgentCommissionsByWeekYear(Integer week,Integer year) throws AgentCommissionNotFoundException;
	 public ArrayList<AgentCommissions> getAgentCommissionsOfOrders(ArrayList<Orders> orders)throws AgentCommissionNotFoundException;
	 public Integer deleteAgentCommissionsOfOrders(ArrayList<AgentCommissions> commissions)throws Exception;	 
	 public ArrayList<AgentCommissions> getCommissionsBetweenDaysWithCriteria(Integer week,String agent,Integer year)throws AgentCommissionNotFoundException;
	 public ArrayList<AgentCommissions> getAgentCommissionsByAgentNumber(String agentNumber)throws AgentCommissionNotFoundException;
	public ArrayList<Orders> getOrdersForAgentCommissions(Date startDate,Date endDate,String agent)throws OrderNotFoundException;
	public ArrayList<Orders> getOrdersEligibleForCommissions(Date startDate,Date endDate,String agent,Boolean filter) throws OrderNotFoundException;
	
	//For ContractType
	public Integer saveOrUpdateContractType(ContractTypes contractType) throws Exception;
	public Integer deleteContractType(ContractTypes contractType) throws Exception;
	public ArrayList<ContractTypes> getAllContractType()throws ContractTypeNotFoundException;
	
	//Anniv Payments
	public ArrayList<Orders> getAllAnniversaryPaymentOrders(Integer page,Integer range)throws OrderNotFoundException;
	public ArrayList<Orders> getMultiSearchResultsOFAnniversaryPayments(PipelineSearchDto search,Integer pageNo,Integer range,String sortElement)throws Exception;
	public <T> ArrayList<Orders> getOrdersFilteredByPropertyForAnniversaryPayments(String propertyName,T propertyValue,Integer pageNo,String orderProperty,Integer range)throws Exception;
}
