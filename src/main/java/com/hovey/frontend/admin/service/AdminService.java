package com.hovey.frontend.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.agent.model.Orders;
import com.hovey.frontend.admin.dto.AgentOrderDto;
import com.hovey.frontend.admin.dto.ChartOrderDataDto;
import com.hovey.frontend.admin.dto.LineChartDto;
import com.hovey.frontend.admin.dto.PipelineSearchDto;
import com.hovey.frontend.admin.dto.UtilityChartDto;
import com.hovey.frontend.agent.dto.ContractTypeDto;
import com.hovey.frontend.agent.dto.OrdersDto;
import com.hovey.frontend.agent.dto.UtilityDto;
import com.hovey.frontend.user.dto.HoveyUserDto;

public interface AdminService {

	public int findTotalNoOfOrderPages(int pageSize) throws Exception;
	public ArrayList<OrdersDto> getOrdersFromDAO(int pageNo,String orderProperty,int pageSize)throws Exception;
	public ArrayList<OrdersDto> getAllOrdersFromDao()throws Exception;
	public ArrayList<OrdersDto> getDealSheetsofAgentsFromDao(int pageNo,int pageSize,String sortBy,String searchBy) throws Exception;
	public int findTotalNoOfDealPages(int pageSize,String searchBy) throws Exception;
	public <T> int getTotalFilteredOrderPages(int pageNo,String propertyName,T propertyValue,String fieldType,String sortElement,int range)throws Exception;
	public <T> ArrayList<OrdersDto> getOrdersFilteredFromDao(int pageNo,String filterName,T	filterValue,String fieldType,String sortElement,int range)throws Exception;
	public <T> ArrayList<OrdersDto> getOrdersFilteredFromDaoForReports(int pageNo,String filterName,T filterValue,String fieldType,String sortElement, int range)throws Exception;
	public ArrayList<OrdersDto> getMultiSearchResultsFromDao(PipelineSearchDto search,int pageSize,int range,String sortElement)throws Exception;
	public int getTotalMultiSearchResultsFromDao(PipelineSearchDto search,int pageNo,int range,String sortElement)throws Exception;
	public int getTotalDealSheetsCount(String searchBy)throws Exception;
	// for Searchhh
	public ArrayList<UtilityDto> getSuppliersFromDao()throws Exception;
	public ArrayList<HoveyUserDto> getAgentsFromDao() throws Exception;
	public ArrayList<String> getBusinessNames()throws Exception;
	public Integer editPipilineDataSenttoDAO(Integer orderId,Date sentToSupplier,/* modifeid */Date dealStartDate,Double commission,Date upfrontPaidDate,
			Double upfrontCommission,String notes,String status, String term,Double commissionRate,Boolean faxReceived,Boolean QA, String contractType,String resAgent) throws Exception;
	public ArrayList<OrdersDto> getOrdersFromDAOForReports() throws Exception;
	public void deleteStateinDao(int id)throws Exception;
	public void saveStateinDao(String statename)throws Exception;
	public int getToalOrderRecords()throws Exception;
	public <T>int getTotalFilterOrderRecords(String propertyName,T propertyValue, int pageNo,String sortElement,int range)throws Exception;
	public <T>int getTotalMultiSearchOrderRecords(PipelineSearchDto search,int pageNo,int range,String sortElement)throws Exception;
/*	public ArrayList<OrdersDto> getOrdersByUtility(String utility)throws OrderNotFoundException;*/
	/*public ArrayList<UtilityChartDto> getOrdersByUtilits()throws Exception;*/
	/*public ArrayList<OrdersDto> getOrdersByState(String state)throws OrderNotFoundException;
	public ArrayList<OrdersDto> getOrdersBySupplier(String supplierName)throws OrderNotFoundException;*/
	public ArrayList<ChartOrderDataDto> getYearSalesSummary()throws Exception;
	public Map<String,Object> getSummaryDetails();
	public ArrayList<OrdersDto> getOrdersByTerm(String term)throws OrderNotFoundException;
	public ArrayList<OrdersDto> getOrdersByRange(Date startDate,Date endDate)throws Exception;
	public ArrayList<AgentOrderDto> getAgentReporsByTerm(String term)throws Exception;
	public ArrayList<AgentOrderDto> getAgentReporsByRange(Date startDate,Date endDate)throws Exception;
	public ArrayList<OrdersDto> getOrdersByRangeOfAgent(Date startDate,Date endDate,String agent)throws Exception;
	public ArrayList<OrdersDto> getOrdersByPaidDateinDateRange(Date startDate,Date endDate)throws Exception;
	public ArrayList<LineChartDto> getWeeksSalesSummary();
	public ArrayList<LineChartDto> getMonthsSalesSummary();
	
	
	public Map<String, Object> getPieChartsForDashBoard()throws Exception;
	
	
	
	//Code added by Praga
		public ArrayList<ChartOrderDataDto> getWeeklyOrders() throws Exception;
		public ArrayList<ChartOrderDataDto> getMonthlyOrders() throws Exception;
		public ArrayList<ChartOrderDataDto> getYearlyOrders() throws Exception;		
		public Map<String, Object> getAvgSaleOfMonthinLastYear() throws Exception;
	
		// For Contract Type
		public ArrayList<ContractTypeDto> getContractTypesFromDao() throws Exception;
		public void saveContractTypeinDao(String contractType)throws Exception;
		public void deleteContractTypeinDao(int id)throws Exception;
		
		//for upfront commission
		public Integer getNumberOfMonthsBetweenStartdateAndCurrentDate(Date startDate);
		public OrdersDto populateOrderDtoWithAnnualUpfrontCommissionsAndUpfrontPaidDate(Orders order);
		public Orders handleUpfrontCommissions(Orders order,Double upfrontCommission,Date upfrontPaidDate);
		
		public ArrayList<OrdersDto> getAllAnniversaryPayments()throws Exception;
		
		
		
		
		//For Anniversary Payments
		public ArrayList<OrdersDto> getAnniversaryOrdersFromDAO(Integer pageNo,String orderProperty,Integer pageSize)throws Exception;
		public <T> ArrayList<OrdersDto> getAnnivPayOrdersFilteredFromDao(Integer pageNo,String filterName,T filterValue,String fieldType,String sortElement, Integer range)throws Exception;
		public ArrayList<OrdersDto> getMultiSearchResultsofAnnivPaysFromDao(PipelineSearchDto search,Integer pageSize,Integer range,String sortElement)throws Exception;
		public ArrayList<OrdersDto> getAllAnniversaryPaymentsDuetoPay(Integer pageNo,Integer pageSize) throws Exception;
		public ArrayList<OrdersDto> getAllAnniversaryPaymentsDuetoPayForPipelineReports() throws Exception;
}
