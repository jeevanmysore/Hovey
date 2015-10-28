package com.hovey.frontend.admin.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hovey.backend.admin.dao.AdminDao;
import com.hovey.backend.agent.dao.AgentDao;
import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.agent.model.ContractTypes;
import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.agent.model.RescindedOrders;
import com.hovey.backend.agent.model.State;
import com.hovey.backend.agent.model.Transactions;
import com.hovey.backend.agent.model.Utility;
import com.hovey.backend.supplier.exception.SupplierReportsNotFoundException;
import com.hovey.backend.user.model.HoveyUser;
import com.hovey.frontend.admin.dto.AgentOrderDto;
import com.hovey.frontend.admin.dto.ChartOrderDataDto;
import com.hovey.frontend.admin.dto.LineChartDto;
import com.hovey.frontend.admin.dto.PipelineSearchDto;
import com.hovey.frontend.admin.dto.UtilityChartDto;
import com.hovey.frontend.agent.dto.ContractTypeDto;
import com.hovey.frontend.agent.dto.OrdersDto;
import com.hovey.frontend.agent.dto.StateDto;
import com.hovey.frontend.agent.dto.UtilityDto;
import com.hovey.frontend.agent.service.DealSheetService;
import com.hovey.frontend.supplier.dto.SupplierDto;
import com.hovey.frontend.supplier.service.SupplierService;
import com.hovey.frontend.user.dto.HoveyUserDto;

/**
 * 
 * @author JEEVAN
 *
 */
@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService{

	private static Logger log=Logger.getLogger(AdminServiceImpl.class);
	
	@Resource(name="adminDao")
	private AdminDao adminDao;
	
	@Resource(name="agentDao")
	private AgentDao agentDao;

	@Resource(name="dealSheetService")
	private DealSheetService dealSheetService;
	
	@Resource(name="supplierService")
	private SupplierService supplierService;
	
	@Resource(name="adminService")
	private AdminService adminService;
	
	private Map<String, Object>summaryMap=new HashMap<String, Object>();
	
	
	
	
	
	
	//returns total no of OderPages..
	public int findTotalNoOfOrderPages(int pageSize) throws Exception{
		log.info("inside findTotalNoOfOrderPages()");
		int totalRecords=this.adminDao.getTotalOrders();
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
	
	
	
	/* **************Added by Jeevan on July 19,2013 to facilitate pagination with Orders Count
	 *   The Coding could be a lot better but as it is added in the mid way, did not find the better way than this.
	 *   
	 *   Will be refactored if found a better way..
	 * 
	 * ***********************************/
		public int getToalOrderRecords()throws Exception{
			log.info("inside getToalOrdrRecorde()");
			int totalOrders=this.adminDao.getTotalOrders();
			return totalOrders;		
		}
	/*  *******End of Modification**************/
	
	
		
		
	//gets Orders Sorted By OrderName;
	public ArrayList<OrdersDto> getOrdersFromDAO(int pageNo,String orderProperty,int pageSize)throws Exception{
		log.info("inside getOrdersFromDAO() ");
		ArrayList<Orders> orders=this.adminDao.getOrdersFromDB(pageNo, orderProperty,pageSize);		
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		if(!orders.isEmpty()){
			for(Orders order:orders){
				//modified by bhagya on may 22nd,2014
				OrdersDto orderDto=this.populateOrderDtoWithAnnualUpfrontCommissionsAndUpfrontPaidDate(order);
				orderDtos.add(orderDto);
			}		
		}
		return orderDtos;		
	}
	
	
	/* Added by Jeevan on  June 11, 2014 Method to get all the Orders as it is */
	public ArrayList<OrdersDto> getOrdersFromDAOForReports() throws Exception{
		ArrayList<Orders> orders=this.adminDao.getOrdersFromDB(0, "orderId", 0);
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		for(Orders order: orders){
			OrdersDto orderDto=OrdersDto.populateOrderDto(order);
			orderDtos.add(orderDto);
		}
		return orderDtos;		
	}
	
	
	public ArrayList<OrdersDto> getAllOrdersFromDao()throws Exception{
		log.info("inside getAllORdersFromDao()");
		ArrayList<Orders> orders=this.adminDao.getCompleteOrders();
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		if(!orders.isEmpty()){
			for(Orders order:orders){
				OrdersDto orderDto=OrdersDto.populateOrderDtoForDashBoard(order);
				orderDtos.add(orderDto);
			}		
		}
		return orderDtos;	
	}
	
	
	
	
	
	/*
	 * Added by Jeevan on 23 July , 2013 to give pagination with count for Deals.
	 */
	public int getTotalDealSheetsCount(String searchBy)throws Exception{
		log.info("inside getTotalDealSheetsCount()");
		int totalDeals=this.adminDao.getTotalNoofDealSheets(searchBy);
		return totalDeals;
	}
	
	
	
	//returns total no of OderPages..
		public int findTotalNoOfDealPages(int pageSize,String searchBy) throws Exception{
			log.info("inside findTotalNoOfOrderPages()");
			int totalRecords=this.adminDao.getTotalNoofDealSheets(searchBy);
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
	
	
	// Gets ArrayList og OrderDtos according to the Transaction Id.
	/*
	 * Optimixzed by Jeevan, doing everything in Service Layer..
	 */	
	public ArrayList<OrdersDto> getDealSheetsofAgentsFromDao(int pageNo,int pageSize,String sortBy,String searchBy) throws Exception{
		log.info("inside findTotalNoOfOrderPages()");
		ArrayList<Transactions> transactions=this.adminDao.getTotalDealSheets(pageNo,pageSize,sortBy,searchBy);
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		if(!transactions.isEmpty()){
			ArrayList<OrdersDto> transactionOrders=this.dealSheetService.getOrdersFromDB();
			
			for(Transactions transaction:transactions){	
				ArrayList<OrdersDto> tempOrders=new ArrayList<OrdersDto>();
				Long totalKwh=0L;
				for(OrdersDto order:transactionOrders){
					if(transaction.getId().equals(order.getTransDto().getTransactionId())){						
						tempOrders.add(order);		
						totalKwh+=order.getKwh();
					}			
				}				
				OrdersDto ordersDto=tempOrders.get(0);
				ordersDto.setTotalAccounts(tempOrders.size());	
				ordersDto.setTotalKwh(totalKwh);							
				orderDtos.add(ordersDto);
			}	
		}		
		return orderDtos;
	}
	
	
	
	
	//returns total nof of orderPAges of a Filtered Condition..
	
	public <T> int getTotalFilteredOrderPages(int pageNo,String propertyName,T propertyValue,String fieldType,String sortElement,int range)throws Exception{
		log.info("inside getTotalFilteredOrderPages()");		
		 int totalRecords=this.adminDao.getTotalNoOfOrdersOfaFilter(propertyName, propertyValue, pageNo, sortElement, range);
		 int result=totalRecords/range;
		int pagesNeeded;
		if(totalRecords%range>0){
			pagesNeeded=result+1;
		}
		else{
			pagesNeeded=result;
		}
		return pagesNeeded;
	}
	
	
	/* **************Added by Jeevan on July 19,2013 to facilitate pagination with Orders Count
	 *   The Coding could be a lot better but as it is added in the mid way, did not find the better way than this.
	 *   
	 *   Will be refactored if found a better way..
	 * 
	 * ***********************************/
		public <T>int getTotalFilterOrderRecords(String propertyName,T propertyValue, int pageNo,String sortElement,int range)throws Exception{
			log.info("inside getTotalFilterOrderRecords()");
			int totalOrders=this.adminDao.getTotalNoOfOrdersOfaFilter(propertyName, propertyValue, pageNo, sortElement, range);
			return totalOrders;		
		}
	/*  *******End of Modification**************/
	
	
	
	
	
	//returns Orders Based on Filter Condition, Properties..
	
	public <T> ArrayList<OrdersDto> getOrdersFilteredFromDao(int pageNo,String filterName,T filterValue,String fieldType,String sortElement, int range)throws Exception{
		log.info("inside getOrdersFilteredFromDao()");
		/**
		 *  Everything is handled in Dao by Hibernate Criteria.
		 * */		
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		
		ArrayList<Orders> orders=this.adminDao.getOrdersFilteredByProperty(filterName, filterValue, pageNo, sortElement, range);		
		for(Orders order:orders){
			//modified by bhagya on may 22nd,2014
			OrdersDto orderDto=this.populateOrderDtoWithAnnualUpfrontCommissionsAndUpfrontPaidDate(order);
			orderDtos.add(orderDto);
		}
		
		return orderDtos;		
	}
	
	
	//Added by Jeevan on June 11, 2014
	public <T> ArrayList<OrdersDto> getOrdersFilteredFromDaoForReports(int pageNo,String filterName,T filterValue,String fieldType,String sortElement, int range)throws Exception{
		log.info("inside getOrdersFilteredFromDao()");
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		ArrayList<Orders> orders=this.adminDao.getOrdersFilteredByProperty(filterName, filterValue, pageNo, sortElement, range);		
		for(Orders order:orders){			
			OrdersDto orderDto=OrdersDto.populateOrderDto(order);
			orderDtos.add(orderDto);
		}		
		return orderDtos;		
	}
	
	
	
	
	
	public ArrayList<OrdersDto> getMultiSearchResultsFromDao(PipelineSearchDto search,int pageSize,int range,String sortElement)throws Exception{
		log.info("inside getMultiSearchResultsFromDao()" );		
		if(null!= search.getAgentName() && search.getAgentName()!=""){
			String name[]=search.getAgentName().split(" ");
			ArrayList<HoveyUser> agents;
			if(name.length>1){
				String firstName=name[0];String lastName=name[1];
				agents=this.agentDao.getUserByAgentName(firstName, lastName);
			}
			else{
				agents=this.agentDao.getUserByFirstName(name[0]);
			}
			search.setAgents(agents);
		}
		 /*added by bhagya on april 30,2014  for getting the resAgent object*/
		if(null!= search.getResAgentName() && search.getResAgentName()!=""){
			String name[]=search.getResAgentName().split(" ");
			ArrayList<HoveyUser> resAgents;
			if(name.length>1){
				String firstName=name[0];String lastName=name[1];
				resAgents=this.agentDao.getUserByAgentName(firstName, lastName);
			}
			else{
				resAgents=this.agentDao.getUserByFirstName(name[0]);
			}
			search.setResAgents(resAgents);
		}
		ArrayList<Orders> orders=this.adminDao.getMultiSearchResults(search, pageSize, range, sortElement);
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		if(!orders.isEmpty()){
			for(Orders order:orders){
				// modified by bhagya on may 22nd,2014
				OrdersDto orderDto=null;
				if(range==0){		
					orderDto=OrdersDto.populateOrderDto(order);								
				}else{
					orderDto=this.populateOrderDtoWithAnnualUpfrontCommissionsAndUpfrontPaidDate(order);
				}
				orderDtos.add(orderDto);
			}
		}
		return orderDtos;
	}
	
	
	//Determines pages Needed..
	public int getTotalMultiSearchResultsFromDao(PipelineSearchDto search,int pageNo,int range,String sortElement)throws Exception{
		log.info("inside getTotalMultiSearchResultsFromDao()");
		if(null!= search.getAgentName() && search.getAgentName()!=""){
			String name[]=search.getAgentName().split(" ");
			ArrayList<HoveyUser> agents;
			if(name.length>1){
				String firstName=name[0];String lastName=name[1];
				agents=this.agentDao.getUserByAgentName(firstName, lastName);
			}
			else{
				agents=this.agentDao.getUserByFirstName(name[0]);
			}
			search.setAgents(agents);
		}
		int totalRecords=this.adminDao.getTotalMultiSearchResults(search, pageNo, range, sortElement);
		System.out.println(totalRecords);
		 int result=totalRecords/range;
			int pagesNeeded;
			if(totalRecords%range>0){
				pagesNeeded=result+1;
			}
			else{
				pagesNeeded=result;
			}
			return pagesNeeded;
	}
	
	
	
	/* **************Added by Jeevan on July 19,2013 to facilitate pagination with Orders Count
	 *   The Coding could be a lot better but as it is added in the mid way, did not find the better way than this.
	 *   
	 *   Will be refactored if found a better way..
	 * 
	 * ***********************************/
		public <T>int getTotalMultiSearchOrderRecords(PipelineSearchDto search,int pageNo,int range,String sortElement)throws Exception{
			log.info("inside getTotalMultiSearchOrderRecords()");
			int totalOrders=this.adminDao.getTotalMultiSearchResults(search, pageNo, range, sortElement);
			return totalOrders;		
		}
	/*  *******End of Modification**************/
	
	
	
	
	
	
	// Getting Elements for Dynamic Search..
	
	public ArrayList<UtilityDto> getSuppliersFromDao()throws Exception
	{
		log.info("inside getSuppliersFromDao()" );
		ArrayList<Utility> suppliers=this.adminDao.getSuppliers();
		ArrayList<UtilityDto> suppliersDto=new ArrayList<UtilityDto>();
		if(!suppliers.isEmpty()){
			for(Utility util:suppliers){
				UtilityDto utilDto=UtilityDto.populateUtility(util);
				suppliersDto.add(utilDto);
				
			}
		}
		
		return suppliersDto;
	}
	
	
	
	public ArrayList<HoveyUserDto> getAgentsFromDao() throws Exception {
		log.info("inside getAgentsFromDao()");
		ArrayList<HoveyUser> agents=this.adminDao.getAgents();
		ArrayList<HoveyUserDto> agentsDto=new ArrayList<HoveyUserDto>();
		if(!agents.isEmpty()){
			for(HoveyUser agent:agents){
				HoveyUserDto agentDto=HoveyUserDto.populateHoveyUserDto(agent);
				agentsDto.add(agentDto);
			}
		}
		return agentsDto;
	}
	
	
	public ArrayList<String> getBusinessNames()throws Exception{
		log.info("inside getBusinessNames()");
		ArrayList<String> businessNames=this.adminDao.getBusinessNames();
		return businessNames;
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////
	
	
	//Editing the Pipeline..	
	public Integer editPipilineDataSenttoDAO(Integer orderId,Date sentToSupplier,/* modified */Date dealStartDate,Double commission,Date upfrontPaidDate,
			Double upfrontCommission,String notes,String status, String term,Double commissionRate,Boolean faxReceived,Boolean QA, String contractType,String resAgent) throws Exception{		
		log.info("inside editPipelineDataSenttoDAO()");
		
		Orders order=this.adminDao.getOrderByOrderId(orderId);
		
		if(order.getStatus().equalsIgnoreCase("agent") && null!=sentToSupplier){
			order.setStatus("under review");
		}
		else if(order.getStatus().equalsIgnoreCase("rescinded") && ! status.equalsIgnoreCase("rescinded")){
			RescindedOrders resOrder=this.adminDao.getRescindedORderByOrderID(order.getOrderId());
			this.adminDao.deleteRescindedOrder(resOrder);
			order.setStatus(status);
		
		}
		else{
			order.setStatus(status);
		}
		
		/*
		 * Added by Jeevan on August 19,2013 to save Rescinded Accnts oin a seperate Table...
		 */
		if(status.equalsIgnoreCase("rescinded")){
			RescindedOrders resOrder;
			try{
				resOrder=this.adminDao.getRescindedORderByOrderID(order.getOrderId());
				/*resOrder.setOrderId(order);
				resOrder.setRefundStatus(false);*/				
			}
			catch(OrderNotFoundException e){
				resOrder=new RescindedOrders();	
				resOrder.setRefundStatus(false);
			}	
			resOrder.setOrderId(order);
			
			this.adminDao.saveOrUpdateRescindedAccount(resOrder);
		}
		
		/* 
		 * Modified by Jeevan on 22 July,2013. Deal Start Date is obtained directly instead of Start Date..
		 * */
		//Date dealStartDate=this.dealSheetService.populateStartDate(startDate);		
		//modified by bhagya on may 26th,2014,getting the annual upfrontcommission and upfrontpaiddate by using handleUpfrontCommission method
		order.setSentToSupplier(sentToSupplier);
		order.setDealStartDate(dealStartDate);
		order.setCommissionRate(commissionRate);
		order.setCommission(commission);
		/*order.setUpfrontPaidDate(upfrontPaidDate);
		order.setUpfrontCommission(upfrontCommission);*/
		order.setNotes(notes);
		order.setTerm(term);
		
		this.handleUpfrontCommissions(order, upfrontCommission, upfrontPaidDate);
		
		//added by bhagya on april 16th, 2014
		
		if(null!=resAgent && resAgent.trim().length()>0){
			HoveyUser resOrderAgent=this.agentDao.getUserByAgentNumber(resAgent);
			order.setResAgent(resOrderAgent);
		}
		
		if(null!=dealStartDate){
			Date dealEndDate=this.dealSheetService.getEndDateFromStartDateandTerm(dealStartDate, term);
			order.setDealEndDate(dealEndDate);
		}
		
		//added on September 19,2013
		order.setFaxReceived(faxReceived);
		//added on September 23,2013
		order.setQA(QA);
		//added on September 25, 2013 
		order.setContractType(contractType);
		Integer result=this.adminDao.editPipelineData(order);
		return result;
	}
	
	
	
	
	

	
	
	public Map<String, Object> getPieChartsForDashBoard()throws Exception{
		log.info("getPieChartsForDashBoard()");		
		ArrayList<OrdersDto> orders=this.getAllOrdersFromDao();
		ArrayList<UtilityChartDto> utilityChartDto=this.getOrdersByUtilits(orders );
		ArrayList<UtilityChartDto> stateChartDto=this.getOrdersByState( orders);
		ArrayList<UtilityChartDto> supplierChartDto=this.getOrdersBySupplier(orders);
		
		Map<String, Object> chartsMap=new HashMap<String, Object>();
		chartsMap.put("utils", utilityChartDto);
		chartsMap.put("states", stateChartDto);
		chartsMap.put("suppliers", supplierChartDto);
		
		return chartsMap;
		
	}
	
	
	
	@SuppressWarnings("finally")
	private ArrayList<UtilityChartDto> getOrdersByUtilits(ArrayList<OrdersDto> orders)throws Exception{
		log.info("inside get Orders by Utility()");
		ArrayList<UtilityChartDto> utilityChartDtos=new ArrayList<UtilityChartDto>();
		try{
		   ArrayList<UtilityDto> utils=this.dealSheetService.getUtilitiesFromDb();	
		
			
			for(UtilityDto utilDto:utils){
				UtilityChartDto chartDto=new UtilityChartDto();
				String property=utilDto.getUtility();
				chartDto.setProperty(property);
				
				int no=0;
				for(OrdersDto order:orders){
					if(property.equalsIgnoreCase(order.getUtility().getUtility())){
						no+=1;
					}				
				}
				chartDto.setNoOfOrders(no);
				utilityChartDtos.add(chartDto);
			}			
	   }
	   catch(Exception e){
		  log.error("Error while Getting Utility Chart"); 
	   }
	   finally{
		   return utilityChartDtos;
	   }		
	}
	
	
	
	

	
	@SuppressWarnings("finally")
	private ArrayList<UtilityChartDto> getOrdersByState(ArrayList<OrdersDto> orders)throws Exception{
		log.info("inside getOrderByState()");
		ArrayList<UtilityChartDto> stateChartDtos=new ArrayList<UtilityChartDto>();
		try{
			ArrayList<StateDto> states=this.dealSheetService.getStatesFromDao();
			
			
			for(StateDto state: states){
				UtilityChartDto chartDto=new UtilityChartDto();
				String property=state.getState();
				chartDto.setProperty(property);			
				int no=0;
				for(OrdersDto order:orders){
					if(property.equalsIgnoreCase(order.getServiceState().getState())){
						no+=1;
					}
				}
				chartDto.setNoOfOrders(no);
				stateChartDtos.add(chartDto);
			}
		}
		catch(Exception e){
			log.error("Error while Getting States Chart"); 
		}
		finally{
			return stateChartDtos;
		}
	}
	
	
	
	
	@SuppressWarnings("finally")
	private ArrayList<UtilityChartDto> getOrdersBySupplier(ArrayList<OrdersDto> orders)throws Exception{
		log.info("inside getOrdersBySupplier()");
		ArrayList<UtilityChartDto> supplierChartDtos=new ArrayList<UtilityChartDto>();
		try{
			ArrayList<SupplierDto> suppliers=this.supplierService.getSuppliersFromDAO();		
			for(SupplierDto supplier:suppliers){
				UtilityChartDto chartDto=new UtilityChartDto();
				String property=supplier.getSupplierName();
				chartDto.setProperty(property);			
				int no=0;
				for(OrdersDto order:orders){
					if(property.equalsIgnoreCase(order.getSupplierName().getSupplierName())){
						no+=1;
					}
				}
				chartDto.setNoOfOrders(no);
				supplierChartDtos.add(chartDto);
			}		
		}
		catch(Exception e){
			e.printStackTrace();
			log.error("Error in getting Supplier Charts");
		}
		finally{
			return supplierChartDtos;	
		}
	}
	
	
	
	
	/*
	 * For BarChart.. 1 year Sales..
	 * 
	 * 
	 * 	 * 	 */
	public ArrayList<LineChartDto> getWeeksSalesSummary(){
		log.info("inside getWeeksSalesSummary()");
		ArrayList<LineChartDto> charts=new ArrayList<LineChartDto>();
		Date today=new Date();
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.setTime(today);
		cal.set(Calendar.AM_PM,Calendar.AM);cal.set(Calendar.HOUR, 0);cal.set(Calendar.MINUTE, 0);cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.DATE, 1);
		Date presentDay=cal.getTime();
		
		cal.add(Calendar.DATE, -7);
		Date startDay=cal.getTime();
		
		cal.clear();
		//settting lastYear Parameters..
		cal.setTime(startDay);
		cal.add(Calendar.YEAR, -1);
		Date lastYearStartDate=cal.getTime();
		
		cal.clear();
		cal.setTime(presentDay);
		cal.add(Calendar.YEAR, -1);
		Date lastYearPresentDate=cal.getTime();
		
		
		ArrayList<Orders> orders=null;
		ArrayList<Orders> lastYearOrders=null;
		
		try{
		 orders=this.adminDao.getActiveOrdersBetweenDays(startDay, presentDay);
		
		}
		catch(OrderNotFoundException e){
			log.error("No Orders Found between these Days "+e.toString());
			
		}
		
		try{
			lastYearOrders=this.adminDao.getActiveOrdersBetweenDays(lastYearStartDate, lastYearPresentDate);
			
		}
		catch(Exception e){
			log.error("no Orders Found");
		}
			//getOrders of Each Day...
		   
		    cal.clear();
		    cal.setTime(startDay);
		    cal.add(Calendar.DATE, -1);
		
			
			charts=this.getLineChart(orders,startDay, presentDay,lastYearOrders,lastYearStartDate,lastYearPresentDate,"week");		
			Integer totalKwh=0;
			Double totalCommission=0.0;
			Integer totalLastYearKwh=0;
			Double totalLastYearCommission=0.0;
			
			for(LineChartDto line:charts){
				totalKwh+=line.getTotalKwh();
				totalCommission+=line.getTotalCommission();
				totalLastYearKwh+=line.getLastYearTotalKwh();
				totalLastYearCommission+=line.getLastYearTotalCommission();
			}
			
			summaryMap.put("weekKwh", totalKwh);
			summaryMap.put("lastYearWeekKwh", totalLastYearKwh);
			summaryMap.put("weekCommission", totalCommission);
			summaryMap.put("lastYearWeekCommission", totalLastYearCommission);
			
		return charts;
	}
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.hovey.frontend.admin.service.AdminService#getMonthsSalesSummary()
	 * Getting Monthly Report..
	 */
	
	public ArrayList<LineChartDto> getMonthsSalesSummary(){
		log.info("inside getWeeksSalesSummary()");
		ArrayList<LineChartDto> charts=new ArrayList<LineChartDto>();
		Date today=new Date();
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.setTime(today);
		cal.set(Calendar.HOUR, 0);cal.set(Calendar.MINUTE, 0);cal.set(Calendar.SECOND, 0);cal.set(Calendar.AM_PM,Calendar.AM);
		cal.add(Calendar.DATE, 1);
		Date presentDay=cal.getTime();
		cal.add(Calendar.MONTH, -1);
		Date startDay=cal.getTime();
		
		cal.clear();
		//settting lastYear Parameters..
		cal.setTime(startDay);
		cal.add(Calendar.YEAR, -1);
		Date lastYearStartDate=cal.getTime();
		
		cal.clear();
		cal.setTime(presentDay);
		cal.add(Calendar.YEAR, -1);
		Date lastYearPresentDate=cal.getTime();
		
		
		ArrayList<Orders> orders=null;
		ArrayList<Orders> lastYearOrders=null;
		
		try{
		 orders=this.adminDao.getActiveOrdersBetweenDays(startDay, presentDay);
		
		}
		catch(OrderNotFoundException e){
			log.error("No Orders Found between these Days "+e.toString());
			e.printStackTrace();
		}
		
		try{
			lastYearOrders=this.adminDao.getActiveOrdersBetweenDays(lastYearStartDate, lastYearPresentDate);
			
		}
		catch(Exception e){
			log.error("no Orders Found");
		}
		//getOrders of Each Day...
			
			charts=this.getLineChart(orders, startDay, presentDay,lastYearOrders,lastYearStartDate,lastYearPresentDate,"month");		
			
			Integer totalKwh=0;
			Double totalCommission=0.0;
			Integer totalLastYearKwh=0;
			Double totalLastYearCommission=0.0;
			
			for(LineChartDto line:charts){
				totalKwh+=line.getTotalKwh();
				totalCommission+=line.getTotalCommission();
				totalLastYearKwh+=line.getLastYearTotalKwh();
				totalLastYearCommission+=line.getLastYearTotalCommission();
			}
			
			summaryMap.put("monthKwh", totalKwh);
			summaryMap.put("lastYearMonthKwh", totalLastYearKwh);
			summaryMap.put("monthCommission", totalCommission);
			summaryMap.put("lastYearMonthCommission", totalLastYearCommission);				
		return charts;
	}
	
	
	public  ArrayList<ChartOrderDataDto> getYearSalesSummary()throws Exception{
		log.info("inside getYearSalesSummary()");
		ArrayList<ChartOrderDataDto> chartDtos=new ArrayList<ChartOrderDataDto>();
		ArrayList<ChartOrderDataDto> tempChartDtos=new ArrayList<ChartOrderDataDto>();
		ArrayList<ChartOrderDataDto> resChartDtos=new ArrayList<ChartOrderDataDto>();
		
		Date today=new Date();
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.setTime(today);
		cal.set(Calendar.DATE,1);cal.set(Calendar.AM_PM,Calendar.AM);cal.set(Calendar.HOUR, 0);cal.set(Calendar.MINUTE, 0);cal.set(Calendar.SECOND, 0);
		//cal.add(Calendar.DATE, 1);
		Date presentDay=cal.getTime();
		cal.add(Calendar.YEAR, -1);
		Date startDay=cal.getTime();
		
		ArrayList<Orders> orders=null;		
		
		try{
		// orders=this.adminDao.getActiveOrdersBetweenDays(startDay, presentDay);
			orders=this.adminDao.getOrdersBetweenDays(startDay, presentDay);
		}
		catch(OrderNotFoundException e){
			log.error("No Orders Found between these Days "+e.toString());			
		}
		
		/* Added by Jeevan on October 28, 2013 *
		 * 
		 * Populate Rescinded Orders,
		 * 2. Remove Rescinded Orders from Normal Orders.
		 * 3. Calculate sum og kwh.,
		 * Commission for bith
		 * */
		
		ArrayList<Orders> rescindedOrders=new ArrayList<Orders>();
		for(Orders order:orders){
			if(order.getStatus().equalsIgnoreCase("rescinded")){
				rescindedOrders.add(order);
			}
		}
		orders.removeAll(rescindedOrders);		
		
		chartDtos=this.getChartDtos(orders, startDay, presentDay);
		Integer totalKwh=0;
		Double totalCommission=0.0;
		for(ChartOrderDataDto chartDto:chartDtos){
			totalKwh+=chartDto.getTotalKwh();
			totalCommission+=chartDto.getTotalCommission();
		}
		Integer ordersCount=orders.size();
	    double avgSales=totalKwh/ordersCount;
		
	    
	    tempChartDtos=this.getChartDtos(rescindedOrders, startDay, presentDay);
	    
	    for(ChartOrderDataDto chart:tempChartDtos){
	    	ChartOrderDataDto chartDto=new ChartOrderDataDto();
	    	chartDto.setDate(chart.getDate());
	    	chartDto.setTotalResKwh(chart.getTotalKwh());
	    	chartDto.setTotalResCommission(chart.getTotalCommission());
	    	chartDto.setTotalKwh(0);
	    	chartDto.setTotalCommission(0.0);
	    	resChartDtos.add(chartDto);
	    }  
	    
	   // chartDtos.addAll(resChartDtos);
	    
	    
		Integer totalResKwh=0;
		Double totalResCommission=0.0;
		for(ChartOrderDataDto chartDto:resChartDtos){
			totalResKwh+=chartDto.getTotalResKwh();
			totalResCommission+=chartDto.getTotalResCommission();
		}
		
		Integer resOrdersCount=rescindedOrders.size();
	    double avgResSales=totalResKwh/resOrdersCount;   
	    summaryMap.put("yearKwh", totalKwh);
	    summaryMap.put("yearCommission", totalCommission);
	    summaryMap.put("totalOrders", ordersCount);
	    summaryMap.put("avgKwh", avgSales);	 
	    
	    summaryMap.put("yearResKwh", totalResKwh);
	    summaryMap.put("yearResCommission", totalResCommission);
	    summaryMap.put("totalResOrders", resOrdersCount);
	    summaryMap.put("avgResKwh", avgResSales);	 
	      
		return chartDtos;		
	}
	
	
	
	
	
	
	
	/*
	 * Support Functions
	 */
	
	
	public ArrayList<LineChartDto> getLineChart(ArrayList<Orders> orders, Date startDate, Date presentDate,
			ArrayList<Orders> lastYearOrders,Date lastYearStartDate,Date lastYearPresentDate,String type){
		log.info("inside getLineChart");
		ArrayList<LineChartDto> charts=new ArrayList<LineChartDto>();
			
			Date minDate=null;
			Date maxDate=null;
			Date lMinDate=null;
			Date lMaxDate=null;
			while(startDate.before(presentDate) && lastYearStartDate.before(lastYearPresentDate)){
				minDate=startDate;
				Calendar cal=Calendar.getInstance();
				cal.setTime(startDate);
				cal.add(Calendar.DATE, 1);
				maxDate=cal.getTime();
				startDate=maxDate;
				
				lMinDate=lastYearStartDate;
				Calendar cal2=Calendar.getInstance();
				cal2.clear();
				cal2.setTime(lastYearStartDate);
				cal2.add(Calendar.DATE, 1);
				lastYearStartDate=cal2.getTime();
				lMaxDate=lastYearStartDate;
			
				cal.setTime(minDate);
				cal.add(Calendar.DATE, -1);
				minDate=cal.getTime();
				
				cal.clear();
				cal.setTime(maxDate);
				cal.add(Calendar.DATE, -1);
				maxDate=cal.getTime();
				
				cal.clear();
				cal.setTime(lMinDate);
				cal.add(Calendar.DATE, -1);
				lMinDate=cal.getTime();
				
				cal.clear();
				cal.setTime(lMaxDate);
				cal.add(Calendar.DATE, -1);
				lMaxDate=cal.getTime();
				
				
		/* recently added   */		
				charts.add(this.populateChart(orders, minDate, maxDate,lastYearOrders,lMinDate,lMaxDate));
			}		
		return charts;		
	}
	
	
	
	
	
	public LineChartDto populateChart(ArrayList<Orders> orders, Date minDate, Date maxDate,
			ArrayList<Orders> lastYearOrders,Date lMinDate,Date lMaxDate){
		log.info("inside populateChart()");
		LineChartDto chartDto=new LineChartDto();
		Integer totalkwh=0;
		Integer lastYearKwh=0;
		Double totalCommission=0.0;
		Double lastYearTotalCommission=0.0;
		
			if(null!=orders ){			
				for(Orders order:orders){
					
					if(order.getOrderDate().after(minDate) && order.getOrderDate().before(maxDate)){
						if(null!=order.getKwh()){
							totalkwh+=order.getKwh();
						}	
						if(null!=order.getCommission()){
							totalCommission+=order.getCommission();
						}
					}				
				}
			}
			
			if(null!=lastYearOrders){
				for(Orders order:lastYearOrders){
					if(order.getOrderDate().after(lMinDate) && order.getOrderDate().before(lMaxDate)){
						if(null!=order.getKwh()){
							lastYearKwh+=order.getKwh();
						}
						if(null!=order.getCommission()){
							lastYearTotalCommission+=order.getCommission();
						}
					}				
				}
			}
			
		
		SimpleDateFormat format=new SimpleDateFormat("MMM-dd");
		String date=format.format(maxDate);
		
		chartDto.setDate(date);
		chartDto.setTotalKwh(totalkwh);
		chartDto.setLastYearTotalKwh(lastYearKwh);
		chartDto.setTotalCommission(totalCommission);
		chartDto.setLastYearTotalCommission(lastYearTotalCommission);		
		return chartDto;
	}
	
	
	
	public ArrayList<ChartOrderDataDto> getChartDtos(ArrayList<Orders> orders, Date startDate,Date presentDate){
		log.info("inside getChartDtos()");
		ArrayList<ChartOrderDataDto> chartDtos=new ArrayList<ChartOrderDataDto>();
		Date minDate=null;
		Date maxDate=null;
		
		while(startDate.before(presentDate) ){
			minDate=startDate;
			Calendar cal=Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.MONTH, 1);
			maxDate=cal.getTime();
			startDate=maxDate;
			chartDtos.add(this.populateChartDto(orders, minDate, maxDate));	
		}		
		return chartDtos;
	}
	
	
	public ChartOrderDataDto populateChartDto(ArrayList<Orders> orders,Date minDate,Date maxDate){
		log.info("inside populateChartDto()");
		
		ChartOrderDataDto chartDto=new ChartOrderDataDto();
		Integer totalKwh=0;
		Double totalCommission=0.0;		
		if(null!=orders ){
			for(Orders order:orders){
				if(order.getOrderDate().after(minDate) && order.getOrderDate().before(maxDate)){
					if(null!=order.getKwh()){
						totalKwh+=order.getKwh();
					}	
					if(null!=order.getCommission()){
						totalCommission+=order.getCommission();
					}
				}				
			}
		}
		SimpleDateFormat format=new SimpleDateFormat("MMM-yy");
		String date=format.format(minDate);
		chartDto.setDate(date);
		chartDto.setTotalCommission(totalCommission);
		chartDto.setTotalKwh(totalKwh);		
		chartDto.setTotalResCommission(0.0);
		chartDto.setTotalResKwh(0);
		return chartDto;
		
	}
	
	
	
	
	
	public Map<String,Object> getSummaryDetails()
	{
		return summaryMap;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	/** Code from PRaga    */
	
	@Override
	public ArrayList<ChartOrderDataDto> getWeeklyOrders() throws Exception {
		// TODO get week orders from today date
		
		Calendar today=Calendar.getInstance();
		Date date=new Date();
		today.setTime(date);
		
		Calendar anotherDate=Calendar.getInstance();
		anotherDate.setTime(today.getTime());
		
		today.roll(Calendar.DATE, 1);
		anotherDate.roll(Calendar.DATE, -7);
		
		//Formart: yyyy-mm-dd
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String startdate=dateFormat.format(anotherDate.getTime());
		String endDate=dateFormat.format(today.getTime());
		
		ArrayList<Orders> orders=this.adminDao.getOrdersBetweenDays(startdate, endDate);
			
		ArrayList<ChartOrderDataDto> chartOrderDataDtos=this.setChartOrderData(orders,"week",anotherDate,today);
		return chartOrderDataDtos;
	}

	@Override
	public ArrayList<ChartOrderDataDto> getMonthlyOrders() throws Exception {
		// TODO get month orders from today date
		
		Calendar today=Calendar.getInstance();
		Date date=new Date();
		today.setTime(date);
		
		Calendar anotherDate=Calendar.getInstance();
		anotherDate.setTime(today.getTime());
		today.roll(Calendar.DATE, 1);
		anotherDate.roll(Calendar.MONTH, -1);
		
		//Formart: yyyy-mm-dd
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String startdate=dateFormat.format(anotherDate.getTime());
		String endDate=dateFormat.format(today.getTime());
		
		ArrayList<Orders> orders=this.adminDao.getOrdersBetweenDays(startdate, endDate);
		//Reduce one day to make it as current date
		today.roll(Calendar.DATE, -1);	
		ArrayList<ChartOrderDataDto> chartOrderDataDtos=this.setChartOrderData(orders,"month",anotherDate,today);
		
		return chartOrderDataDtos;
	}

	@Override
	public ArrayList<ChartOrderDataDto> getYearlyOrders() throws Exception {
		// TODO get year orders from today date
		
		Calendar today=Calendar.getInstance();
		Date date=new Date();
		today.setTime(date);
		
		Calendar anotherDate=Calendar.getInstance();
		anotherDate.setTime(today.getTime());
		
		today.roll(Calendar.DATE, 1);
		anotherDate.roll(Calendar.YEAR, -1);
		
		//Formart: yyyy-mm-dd
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String startdate=dateFormat.format(anotherDate.getTime());
		String endDate=dateFormat.format(today.getTime());
		
		ArrayList<Orders> orders=this.adminDao.getOrdersBetweenDays(startdate, endDate);		
		
		/*For checking purpose we increase one more month and reduce date as Today. Previously, we add one to get values until current time.
		 * Example. Today is 16-May then it increase as 17-May to get values until 16-May 
		 */
		today.roll(Calendar.DATE, -1);	
		today.roll(Calendar.MONTH, 1);
		ArrayList<ChartOrderDataDto> chartOrderDataDtos=this.setChartOrderData(orders,"year",anotherDate,today);
		return chartOrderDataDtos;
	}
	
	//Set chart data
	private ArrayList<ChartOrderDataDto> setChartOrderData(ArrayList<Orders> orders,String type,Calendar anotherDate,Calendar today){		
		
		ArrayList<ChartOrderDataDto> chartOrderDataDtos=new ArrayList<ChartOrderDataDto>();
			
	
			Collections.sort(orders, new Comparator<Orders>() {

				@Override
				public int compare(Orders o1, Orders o2) {
					// TODO sort based on dealStartDate
					return o1.getDealStartDate().compareTo(o2.getDealStartDate());
				}		
				
			});
		
		String startDate=null; 
		String tillDate=null;
		while(anotherDate.compareTo(today) < 0){
			if(type.equalsIgnoreCase("week")){
				tillDate=this.setDateformat(anotherDate.getTime());
				anotherDate.add(Calendar.DATE, 1);
				
			}else if(type.equalsIgnoreCase("year")){
				tillDate=this.setYearformat(anotherDate.getTime());
				anotherDate.add(Calendar.MONTH, 1);
				
			}else if(type.equalsIgnoreCase("month")){
				startDate=this.setDateformat(anotherDate.getTime());
				anotherDate.add(Calendar.DATE, 10);				
				tillDate=this.setDateformat(anotherDate.getTime());
			}
			
			chartOrderDataDtos.add(this.setChartOrderDataDto(orders, type, startDate,tillDate));
		}
		return chartOrderDataDtos;
	}
	
	//Set order for common format
	private ChartOrderDataDto setChartOrderDataDto(ArrayList<Orders> orders,String type,String startDate,String tillDateFormat){
		
		Integer totalKwh=0;
		Double totalCommission=0.0;
		ChartOrderDataDto chartOrderDataDto=new ChartOrderDataDto();
		String curDate=null;
		for (Orders order : orders) {
			
			if(!type.equalsIgnoreCase("month")){
				if(type.equalsIgnoreCase("week")){
					curDate=this.setDateformat(order.getDealStartDate());
				}else if(type.equalsIgnoreCase("year")){
					curDate=this.setYearformat(order.getDealStartDate());
				}
				
				if(curDate.equalsIgnoreCase(tillDateFormat)){
					totalKwh+=order.getKwh().intValue();
					if(order.getCommission() != null)
						totalCommission+=order.getCommission();
				}
			}else{
				curDate=this.setDateformat(order.getDealStartDate());
				
				if(curDate.compareTo(startDate) >= 1 && curDate.compareTo(tillDateFormat) < 1 ){
					
					totalKwh+=order.getKwh().intValue();
					if(order.getCommission() != null)
						totalCommission+=order.getCommission();
				}
			}
		}
		if(!type.equalsIgnoreCase("month")){
			chartOrderDataDto.setDate(tillDateFormat);
		}else{
			chartOrderDataDto.setDate(startDate+"-"+tillDateFormat);
		}
		
		chartOrderDataDto.setTotalCommission(totalCommission);
		chartOrderDataDto.setTotalKwh(totalKwh);
		return chartOrderDataDto;
	}
	
	private String setDateformat(Date formatDate){
		
		SimpleDateFormat formatter=new SimpleDateFormat("MMM-dd");
		String formattedDate=formatter.format(formatDate);
		
		return formattedDate;
	}
	
	private String setYearformat(Date formatDate){
		
		SimpleDateFormat formatter=new SimpleDateFormat("MMM`yy");
		String formattedDate=formatter.format(formatDate);
		
		return formattedDate;
	}



	@Override
	public Map<String, Object> getAvgSaleOfMonthinLastYear() throws Exception {
		
		Calendar today=Calendar.getInstance();
		Date date=new Date();
		today.setTime(date);
		
		Calendar anotherDate=Calendar.getInstance();
		anotherDate.setTime(today.getTime());
		today.roll(Calendar.DATE, 1);
		today.roll(Calendar.YEAR, -1);
		anotherDate.roll(Calendar.MONTH, -1);
		anotherDate.roll(Calendar.YEAR, -1);
		
		//Formart: yyyy-mm-dd
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String startdate=dateFormat.format(anotherDate.getTime());
		String endDate=dateFormat.format(today.getTime());
		
		ArrayList<Orders> orders=this.adminDao.getOrdersBetweenDays(startdate, endDate);
		
		Integer totalKwh=0;
		for (Orders order : orders) {
			totalKwh+=order.getKwh();
		}
		Double avgKwh=0.0;
		if(orders.size() != 0)
			avgKwh=(double)totalKwh/orders.size();
		
		today.roll(Calendar.DATE, -1);
		String dateRange=this.setDateformat(anotherDate.getTime())+" to "+this.setDateformat(today.getTime());
		Map<String, Object> model=new HashMap<String, Object>();
		
		model.put("month", dateRange);
		model.put("avgKwh", avgKwh);
		model.put("totalSales",orders.size());
		
		return model;
	}

	
	
	
	
	
	/***** JEEVAN                   **/
	//deleting state
	public void deleteStateinDao(int id)throws Exception{
		log.info("inside deleteState");
		State state=new State();
		state.setId(id);
		this.adminDao.deleteState(state);
	}
	
	
	public void saveStateinDao(String statename)throws Exception{
		log.info("inside saveState");
		State state=new State();
		state.setState(statename);
		this.adminDao.addState(state);
	}
	
	
	
   /* * 
    *    * For Reports
    *    *
    */
	/*
	 * (non-Javadoc) Added by Jeevan on )8,August, method to get Orders by term...
	 * @see com.hovey.frontend.admin.service.AdminService#getOrdersByTerm(java.lang.String)
	 */
	
	public ArrayList<OrdersDto> getOrdersByTerm(String term)throws OrderNotFoundException{
		log.info("inside getOrdersByTerm()");
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR, 0);cal.set(Calendar.MINUTE, 0);cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.DATE, 1);
		Date endDate=cal.getTime();
		Date startDate=null;
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		ArrayList<Orders> orders=null;
		if(term.equalsIgnoreCase("week")){
			cal.add(Calendar.DATE, -7);
			
			startDate=cal.getTime();			
		}
		else if(term.equalsIgnoreCase("month")){
			cal.add(Calendar.MONTH,-1);
			startDate=cal.getTime();			
		}
		orders=this.adminDao.getOrdersBetweenDays(startDate, endDate);
		if(!orders.isEmpty()){
			for(Orders order:orders){
				OrdersDto orderDto=OrdersDto.populateOrderDto(order);
				orderDto.setRstartDate(startDate);
				orderDto.setRendDate(new Date());
				orderDtos.add(orderDto);
			}
		}
		return orderDtos;
	}
	
	
	/*
	 *Added by Jeevan on 09, August method to get Orders within the Date Range..
	 */
	public ArrayList<OrdersDto> getOrdersByRange(Date startDate,Date endDate)throws Exception{
		log.info("getOrdersByRange()");
		ArrayList<Orders> orders=null;
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		
			orders=this.adminDao.getOrdersBetweenDays(startDate, endDate);
	
		if(null!=orders && !orders.isEmpty()){
			for(Orders order:orders){
				OrdersDto orderDto=OrdersDto.populateOrderDto(order);
				orderDto.setRstartDate(startDate);
				orderDto.setRendDate(endDate);
				orderDtos.add(orderDto);
			}
		}		
		return orderDtos;
	}
	
	
	
	/*
	 * Added by Jeevan on November 21, 2013 to get Orders based ion UpfontDate
	 */
	public ArrayList<OrdersDto> getOrdersByPaidDateinDateRange(Date startDate,Date endDate)throws Exception{
		log.info("getOrdersByRange()");
		ArrayList<Orders> orders=null;
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		
			orders=this.adminDao.getOrdersByPaidDateBetweenDays(startDate, endDate);
	
		if(null!=orders && !orders.isEmpty()){
			for(Orders order:orders){
				OrdersDto orderDto=this.populateOrderDtoWithAnnualUpfrontCommissionsAndUpfrontPaidDate(order);
				orderDto.setRstartDate(startDate);
				orderDto.setRendDate(endDate);
				orderDtos.add(orderDto);
			}
		}		
		return orderDtos;
	}
	
	
	
	/*
	 * Added by Jeevan on Aug 09,2013... to get Agents Order Info.....
	 */
	
	public ArrayList<AgentOrderDto> getAgentReporsByTerm(String term)throws Exception{
		log.info("inside getAgentReportsByTerm()");
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR, 0);cal.set(Calendar.MINUTE, 0);cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.DATE, 1);
		Date endDate=cal.getTime();
		ArrayList<OrdersDto> orderDtos=this.getOrdersByTerm(term);
		ArrayList<AgentOrderDto> agents=new ArrayList<AgentOrderDto>();
		ArrayList<HoveyUserDto> agentDtos=this.getAgentsFromDao();
		if(!agentDtos.isEmpty() && !orderDtos.isEmpty()){
			for(HoveyUserDto agentDto:agentDtos){ // iterating agents
				AgentOrderDto agent=new AgentOrderDto();
				agent.setAgentName(agentDto.getFirstName()+" "+agentDto.getLastName());
				agent.setAgentNumber(agentDto);
				Integer kWh=0; Double expCom=0.0; Double recCom=0.0;
				int i=0;
				for(OrdersDto orderDto:orderDtos){ // iterating Orders to get Agent of that ORder..
					if(orderDto.getCreatedAgent().getAgentNumber().equals(agentDto.getAgentNumber())){
						kWh+=orderDto.getKwh();
						expCom+=orderDto.getCommission();
						recCom+=orderDto.getUpfrontCommission();
						i+=1;
					}
					//added by bhagya on april 14th 2014,setting order status,sent to supplier date to agent
					agent.setOrderStatus(orderDto.getStatus());
					agent.setSentToSupplier(orderDto.getSentToSupplier());
				}
				agent.setTotalOrders(i);
				agent.setTotalKwh(kWh);
				agent.setTotalExpectedCommission(expCom);
				agent.setTotalReceivedCommission(recCom);
				
				if(term.equalsIgnoreCase("month")){
					cal.add(Calendar.MONTH, -1);
					agent.setRstartDate(cal.getTime());
				}
				else{
					cal.add(Calendar.DATE, -7);
					agent.setRstartDate(cal.getTime());
				}
				agent.setRendDate(new Date());
				agents.add(agent);
			}			
		}
		return agents;
	}
	
	
	
	/*
	 * getting Agent agg Orders ans Commissions by Range..
	 */
	public ArrayList<AgentOrderDto> getAgentReporsByRange(Date startDate,Date endDate)throws Exception{
		log.info("inside getAgentReportsByTerm()");
		ArrayList<OrdersDto> orderDtos=this.getOrdersByRange(startDate, endDate);
		ArrayList<AgentOrderDto> agents=new ArrayList<AgentOrderDto>();
		ArrayList<HoveyUserDto> agentDtos=this.getAgentsFromDao();
		if(!agentDtos.isEmpty() && !orderDtos.isEmpty()){
			for(HoveyUserDto agentDto:agentDtos){ // iterating agents
				AgentOrderDto agent=new AgentOrderDto();
				agent.setAgentName(agentDto.getFirstName()+" "+agentDto.getLastName());
				agent.setAgentNumber(agentDto);
				Integer kWh=0; Double expCom=0.0; Double recCom=0.0; 
				int i=0;
				for(OrdersDto orderDto:orderDtos){ // iterating Orders to get Agent of that ORder..
					if(orderDto.getCreatedAgent().getAgentNumber().equals(agentDto.getAgentNumber())){
						kWh+=orderDto.getKwh();
						expCom+=orderDto.getCommission();
						recCom+=orderDto.getUpfrontCommission();
						i+=1;
					}
					//added by bhagya on april 14th 2014,setting order status,sent to supplier date to agent
					agent.setOrderStatus(orderDto.getStatus());
					agent.setSentToSupplier(orderDto.getSentToSupplier());
				}
				agent.setTotalOrders(i);
				agent.setTotalKwh(kWh);
				agent.setTotalExpectedCommission(expCom);
				agent.setTotalReceivedCommission(recCom);
				agent.setRstartDate(startDate);
				agent.setRendDate(endDate);
				
				agents.add(agent);
			}			
		}
		return agents;
	}
	
	
	/*
	 * get Orders of an Individual Agent within a Range..
	 */
	public ArrayList<OrdersDto> getOrdersByRangeOfAgent(Date startDate,Date endDate,String agent)throws Exception{
		log.info("inside getOrdersByRangeOfAgent()");
		ArrayList<OrdersDto> ordersDto=this.getOrdersByRange(startDate, endDate);
		ArrayList<OrdersDto> agentOrders=new ArrayList<OrdersDto>();
		for(OrdersDto order:ordersDto){
			if(order.getCreatedAgent().getAgentNumber().equalsIgnoreCase(agent)){
				order.setRstartDate(startDate);
				order.setRendDate(endDate);
				agentOrders.add(order);
			}
			
		}
		
		if(!agentOrders.isEmpty()){
			return agentOrders;			
		}
		else{
			throw new OrderNotFoundException();
		}	
	}
	
	/*Added by bhagya on May 07th,2014
	* for getting Contract types from Dao
	*/
	public ArrayList<ContractTypeDto> getContractTypesFromDao() throws Exception{
		log.info("inside getContractTypesFromDao()");
		ArrayList<ContractTypes> contractTypes=this.adminDao.getAllContractType();
		ArrayList<ContractTypeDto> contractTypeDtos=new ArrayList<ContractTypeDto>();
		if(! contractTypes.isEmpty()){
			for(ContractTypes contractType:contractTypes){
				ContractTypeDto contractTypeDto=ContractTypeDto.populateContractTypeDto(contractType);
				contractTypeDtos.add(contractTypeDto);
				 }
			}
		return contractTypeDtos;
					
	}
	
	/*	Added by bhagya on may 07th,2014
	 * Methods for Saving the Contract Type and deleting the contract type
	 * */

	public void saveContractTypeinDao(String contractType)throws Exception{
		log.info("inside saveContractType");
		ContractTypes contractTypes=new ContractTypes();
		contractTypes.setContractType(contractType);
		this.adminDao.saveOrUpdateContractType(contractTypes);
	}
	//deleting contract type
		public void deleteContractTypeinDao(int id)throws Exception{
			log.info("inside deleteContractType");
			ContractTypes contractType=new ContractTypes();
			contractType.setId(id);
			this.adminDao.deleteContractType(contractType);
		}

		
		
		
/** *******************************ANNIVERSARY PAYMENTS*******************************************************************************/		
		
		
	//added by bhagya on may22nd,2014 For Annual UpfrontCommission and UpfrontPaidDate
		
	public Integer getNumberOfMonthsBetweenStartdateAndCurrentDate(Date startDate){
			Long start=startDate.getTime();
			Long end=new Date().getTime();
			Long diff=end-start;
			double days=(diff/(1000*60*60*24));
			int months= (int) Math.round((days/30));
			return months;		
		}
	
	
	/*
	 * Support Method for Handling UpfrontCommission on the Basis of its Anniversary Year
	 */
	public OrdersDto populateOrderDtoWithAnnualUpfrontCommissionsAndUpfrontPaidDate(Orders order){
		OrdersDto orderDto=OrdersDto.populateOrderDto(order);
		if(null!=order.getDealStartDate()){
			Integer dealMonths=this.getNumberOfMonthsBetweenStartdateAndCurrentDate(order.getDealStartDate());		
			Integer year=(dealMonths/12)+1;	
		 /* Modified by Jeevan as prev implementation is not constructive */			
			Integer remainingMonths=0;
			Integer term=Integer.valueOf(order.getTerm());
			
			if(year==1){
				if(term%12!=0)
					remainingMonths=term%12;	
				else
					remainingMonths=12;
			}						
			double temp1=(double)remainingMonths/12;
			Double commission1=order.getCommission();
		// To give Term Commission , If Deal Start Date not started, actual commission will be sent	
			if(dealMonths>=0)
				orderDto.setTermCommission(commission1*temp1);
			else
				orderDto.setTermCommission(order.getCommission());
			/* end of modifications */	
			if(year==2){						
				orderDto.setUpfrontCommission(order.getUpfrontCommission2());
				orderDto.setUpfrontPaidDate(order.getUpfrontPaidDate2());
				if(term!=24)
					remainingMonths=term%12;	
				else
					remainingMonths=12;
				
				double temp=(double)remainingMonths/12;
				Double commission=order.getCommission();			
				if(remainingMonths!=0){
					commission=commission*temp;					
				}	
				if(dealMonths>=0)
					orderDto.setTermCommission(commission);					
			}
			else if(year==3){				
				orderDto.setUpfrontCommission(order.getUpfrontCommission3());
				orderDto.setUpfrontPaidDate(order.getUpfrontPaidDate3());
				if(term!=36)
					remainingMonths=term%12;	
				else
					remainingMonths=12;				
				double temp=(double)remainingMonths/12;
				Double commission=order.getCommission();
				if(remainingMonths!=0){
					commission=commission*temp;					
				}	
				if(dealMonths>=0)
					orderDto.setTermCommission(commission);	
				/*orderDto.setOrderYear(year);*/
			}
			orderDto.setOrderYear(year);
			orderDto.setTermMonths(remainingMonths);
			//added on June 02, 2014
			
			orderDto.setTotalUpfrontCommission(order.getUpfrontCommission()+order.getUpfrontCommission2()+order.getUpfrontCommission3());
		}
		return orderDto;		
	}
	
	
	
	
	
	
	/*Added by bhagya on may 27th,2014
	 * this method is used for edit pipeline page,get the annual commissions and paid dates
	 */
	
	public Orders handleUpfrontCommissions(Orders order,Double upfrontCommission,Date upfrontPaidDate) {
		if(null!=order.getDealStartDate()){
			Integer dealMonths=this.adminService.getNumberOfMonthsBetweenStartdateAndCurrentDate(order.getDealStartDate());					
			Integer year=(dealMonths/12)+1;
			
			switch(year){
			case 1: order.setUpfrontCommission(upfrontCommission);
					order.setUpfrontPaidDate(upfrontPaidDate);
			break;
			case 2: order.setUpfrontCommission2(upfrontCommission);
					order.setUpfrontPaidDate2(upfrontPaidDate);
			break;
			case 3: order.setUpfrontCommission3(upfrontCommission);
					order.setUpfrontPaidDate3(upfrontPaidDate);
			break;
			default:
				 order.setUpfrontCommission(upfrontCommission);
					order.setUpfrontPaidDate(upfrontPaidDate);
				break;
			}
			}
		return order;
	}
	
	
	
	/*
	 * Created by Jeevan on May 29, 2014
	 * Method to get all Anniv Payment Orders
	 */
	public ArrayList<OrdersDto> getAllAnniversaryPayments()throws Exception{
		log.info("inside getAllAnniversaryPayments()");
		ArrayList<Orders> orders=this.adminDao.getAllAnniversaryPaymentOrders(null,null);
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		for(Orders order:orders){
			OrdersDto ordersDto=this.populateOrderDtoWithAnnualUpfrontCommissionsAndUpfrontPaidDate(order);
			System.out.println("YEAE "+ordersDto.getOrderYear());
			orderDtos.add(ordersDto);
		}
		return orderDtos;
	}
	
	
	
	
	
	/*
	 * Created by Jeevan on May 30, 2014
	 * Method to get all Anniversary Payments Which are Due to pay amount.
	 *
	 *Steps:
	 * 1. Get All Anniversary Payment Orders
	 * 2. For Each Orders,
	 *                find out its year
	 *                 check if Upfrontcommissiomn ofthat year < term Commission
	 *                if so add to list of deficien ordres
	 * 3. Apply pagination if needed
	 *
	 */
	public ArrayList<OrdersDto> getAllAnniversaryPaymentsDuetoPay(Integer pageNo,Integer pageSize) throws Exception{
		log.info("inside getAllAnniversaryPaymentsDueToPay()");
		ArrayList<OrdersDto> orderDtos=this.getAllAnniversaryPayments();	
		ArrayList<OrdersDto> deficientOrders=new ArrayList<OrdersDto>();
	
		ArrayList<OrdersDto> filteredOrders=new ArrayList<OrdersDto>();         //used for pagination
		for(OrdersDto order:orderDtos){
			if(order.getUpfrontCommission()+1<order.getTermCommission()){                 //+1 is consider to avoid decimal points
				deficientOrders.add(order);                                             //2 Gathering all deficient orders
			}
		}	
		if(null!=pageSize){
			for(int i=pageNo*pageSize;i<(pageNo*pageSize)+pageSize;i++){				
				if(i<deficientOrders.size()){
					deficientOrders.get(i).setTotalResults(deficientOrders.size());
					filteredOrders.add(deficientOrders.get(i));		
				}
			}
		}
		else{
			filteredOrders.addAll(deficientOrders);
		}
		
		if(filteredOrders.isEmpty()){
			throw new OrderNotFoundException();
		}
		else{
			return filteredOrders;
		}
	}
	
	
	
	/*\
	 *
	 *Added by Jeevan on June 13, 2014 for Reports
	 */
	public ArrayList<OrdersDto> getAllAnniversaryPaymentsDuetoPayForPipelineReports() throws Exception{
		log.info("inside getAllAnniversaryPaymentsDueToPay()");
		ArrayList<Orders> orders=this.adminDao.getAllAnniversaryPaymentOrders(null,null);
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		for(Orders order: orders){
			OrdersDto ordersDto=OrdersDto.populateOrderDto(order);
			OrdersDto annivOrderDto=this.populateOrderDtoWithAnnualUpfrontCommissionsAndUpfrontPaidDate(order);
			if(annivOrderDto.getUpfrontCommission()+1<annivOrderDto.getTermCommission()){
				orderDtos.add(ordersDto);
			}			
		}
	    if(!orderDtos.isEmpty()){
	    	return orderDtos;
	    }
	    else{
	    	throw new OrderNotFoundException();
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Anniversary Pay *************************************************************************************************************************
	 * 
	 */
	//gets Orders Sorted By OrderName;
		public ArrayList<OrdersDto> getAnniversaryOrdersFromDAO(Integer pageNo,String orderProperty,Integer pageSize)throws Exception{
			log.info("inside getOrdersFromDAO() ");
			ArrayList<Orders> orders=this.adminDao.getAllAnniversaryPaymentOrders(pageNo, pageSize);
			ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
			if(!orders.isEmpty()){
				for(Orders order:orders){
					//modified by bhagya on may 22nd,2014
					OrdersDto orderDto=null;
					if(null!=pageSize){
						orderDto=this.populateOrderDtoWithAnnualUpfrontCommissionsAndUpfrontPaidDate(order);
					}else{
						orderDto=OrdersDto.populateOrderDto(order);
					}					
					orderDtos.add(orderDto);
				}		
			}
			return orderDtos;		
		}
	
		
		/*
		 * created by Jeeva on May 30, 2014
		 * Method to get Filtered Anniv Pay Orders
		 */
		public <T> ArrayList<OrdersDto> getAnnivPayOrdersFilteredFromDao(Integer pageNo,String filterName,T filterValue,String fieldType,String sortElement, Integer range)throws Exception{
			log.info("inside getOrdersFilteredFromDao()");
			/**
			 *  Everything is handled in Dao by Hibernate Criteria.
			 * */		
			ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
			
			ArrayList<Orders> orders=this.adminDao.getOrdersFilteredByPropertyForAnniversaryPayments(filterName, filterValue, pageNo, sortElement, range);		
			for(Orders order:orders){
				//modified by bhagya on may 22nd,2014
				OrdersDto orderDto=null;
				if(null!=range){
					orderDto=this.populateOrderDtoWithAnnualUpfrontCommissionsAndUpfrontPaidDate(order);
				}else{
					orderDto=OrdersDto.populateOrderDto(order);
				}	
				orderDtos.add(orderDto);
			}			
			return orderDtos;		
		}
		
		
		/*
		 * Created by Jeevan on May 30, 2014
		 * Method to perform Multi Search for Anniversary Payments
		 */
		public ArrayList<OrdersDto> getMultiSearchResultsofAnnivPaysFromDao(PipelineSearchDto search,Integer pageSize,Integer range,String sortElement)throws Exception{
			log.info("inside getMultiSearchResultsFromDao()" );		
			if(null!= search.getAgentName() && search.getAgentName()!=""){
				String name[]=search.getAgentName().split(" ");
				ArrayList<HoveyUser> agents;
				if(name.length>1){
					String firstName=name[0];String lastName=name[1];
					agents=this.agentDao.getUserByAgentName(firstName, lastName);
				}
				else{
					agents=this.agentDao.getUserByFirstName(name[0]);
				}
				search.setAgents(agents);
			}
			 /*added by bhagya on april 30,2014  for getting the resAgent object*/
			if(null!= search.getResAgentName() && search.getResAgentName()!=""){
				String name[]=search.getResAgentName().split(" ");
				ArrayList<HoveyUser> resAgents;
				if(name.length>1){
					String firstName=name[0];String lastName=name[1];
					resAgents=this.agentDao.getUserByAgentName(firstName, lastName);
				}
				else{
					resAgents=this.agentDao.getUserByFirstName(name[0]);
				}
				search.setResAgents(resAgents);
			}
			ArrayList<Orders> orders=this.adminDao.getMultiSearchResultsOFAnniversaryPayments(search, pageSize, range, sortElement);
			ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
			if(!orders.isEmpty()){
				for(Orders order:orders){
					// modified by bhagya on may 22nd,2014
					OrdersDto orderDto=null;
					if(null!=range){
						orderDto=this.populateOrderDtoWithAnnualUpfrontCommissionsAndUpfrontPaidDate(order);
					}else{
						orderDto=OrdersDto.populateOrderDto(order);
					}	
					orderDtos.add(orderDto);
				}
			}
			return orderDtos;
		}
}


