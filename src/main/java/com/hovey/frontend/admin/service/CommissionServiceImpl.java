package com.hovey.frontend.admin.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.transform.ToListResultTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hovey.backend.admin.dao.AdminDao;
import com.hovey.backend.admin.dao.CustomerDao;
import com.hovey.backend.admin.exception.AgentCommissionNotFoundException;
import com.hovey.backend.agent.dao.DealSheetDao;
import com.hovey.backend.agent.model.AgentCommissions;
import com.hovey.backend.agent.model.Customer;
import com.hovey.backend.agent.model.KwhLimit;
import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.agent.model.RescindedOrders;
import com.hovey.backend.user.model.HoveyUser;
import com.hovey.frontend.agent.dto.AgentCommissionsDto;
import com.hovey.frontend.agent.dto.OrdersDto;
import com.hovey.frontend.agent.dto.RescindedOrdersDto;

/*
 * created by Jeevan on July 23,2013 in order to handle Commission Reconciliation
 * 
 */

@Service("commissionService")
public class CommissionServiceImpl implements CommissionService {

	@Resource(name="adminDao")
	private AdminDao adminDao;
	
	@Resource(name="customerDao")
	private CustomerDao customerDao; 
	
	@Resource(name="dealSheetDao")
	private DealSheetDao dealSheetDao;
	
	@Resource(name="adminService")
	private AdminService adminService;
	
	

	private static Logger log=Logger.getLogger(CommissionService.class);
	
	/*
	 * Added by Jeevan on July 23, 2013 in order to implement commission reconciliation.
	 * Steps:
	 * 1. Based on OrderId get Order.
	 * 2. Update Order with new commission values,
	 * 3. Based on OrderId, get Customer,
	 * 4. Get all the ORders of a Customer.
	 * 5. find out the aggregate sum of Commission , upfromt commission, store it in Map and send to Controller.
	 */
	public Map<String, Double> reconcileCommissions(Double commission,Double upfrontCommission,Integer orderId,Double commissionRate)throws Exception{
		log.info("inside reconcileCommissions()");
		Orders order=this.adminDao.getOrderByOrderId(orderId);//1
		order.setCommission(commission);
		order.setCommissionRate(commissionRate);
		//modified by bhagya on may 27th,2014
		this.adminService.handleUpfrontCommissions(order, upfrontCommission, null);
		Integer result=this.adminDao.editPipelineData(order);//2,it also has a functionality of updating Order
		Map<String, Double>commissionsMap=null;
		if(result>1){
			Customer customer=order.getTaxId();//3 ,4
			commissionsMap=this.getAggregateCommissionsOfaCustomer(customer);
		}
		return commissionsMap;		
	}
	
	
	
	public Map<String,Double> getAggregateCommissionsOfaCustomer(Customer customer)throws Exception{
		log.info("inside getAggregateCommissionsOfaCustomer()");
		ArrayList<Orders> orders=this.customerDao.getOrdersofaCustomer(customer.getCustomerId());//4
		Double totalCommission=0.0;
		Double totalUpfrontCommission=0.0;
		Double totalTermCommission=0.0;
		if(!orders.isEmpty()){
			for(Orders order:orders){
				totalCommission=totalCommission+order.getCommission();
				//added by jeevn on May 30, 2014
				totalTermCommission=totalTermCommission+this.adminService.populateOrderDtoWithAnnualUpfrontCommissionsAndUpfrontPaidDate(order).getTermCommission();
				//modified by bhagya on may 28th,2014  ,using the AppropriateCommission method for getting exact commission value on that year
				totalUpfrontCommission=totalUpfrontCommission+this.getAppropriateUpfrontCommissions(order); //5
			}
		}
		Map<String, Double>commissionsMap=new HashMap<String, Double>();
		commissionsMap.put("totalCommission",totalCommission);
		commissionsMap.put("totalUpfront", totalUpfrontCommission);
		commissionsMap.put("totalTermCommission", totalTermCommission);
		return commissionsMap;
		
	}
	
	
	
/*****************************************For Agent Commissions *?///////////////////////////////////////////////////////////////////////////////////////////
 *  *  * @throws Exception */
	
	
	//get all orders satisfying commission Criteria
	public ArrayList<OrdersDto> getAllEligbleOrdersforAgentCommissions(Date startDate,Date endDate,String agentNumber,Boolean filter)throws Exception{
		log.info("inside getAllEligibleOrdersForAgentCommissions()");
		/*Steps:
		 * 1. Get all Users
		 * 2. Get Orders Betwween dates && approved Status.
		 * 3. Iterate through 1 &2, find out total kWh and add Orders of that to kWh..
		 * 
		 */
		Calendar cal=Calendar.getInstance();
		cal.setTime(endDate);
		cal.set(Calendar.HOUR, 0);cal.set(Calendar.AM_PM,Calendar.AM);cal.set(Calendar.MINUTE, 0);cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.DATE, 1);
		endDate=cal.getTime();
		ArrayList<Orders> eligibleOrders=new ArrayList<Orders>();
		ArrayList<OrdersDto> eligibleOrderDtos=new ArrayList<OrdersDto>();		
		ArrayList<HoveyUser> agents=this.adminDao.getAgents();		
	//	ArrayList<Orders> orders=this.adminDao.getOrdersForAgentCommissions(startDate, endDate,agentNumber);
		ArrayList<Orders> orders=this.adminDao.getOrdersEligibleForCommissions(startDate, endDate, agentNumber,filter);
		Integer kwhRange=this.adminDao.getKwhLimit().getkWhLimit();		
		Long eligibleTotalKwh=0L; 	
		Long agentsKwh=0L;
		Map<String, Long> agentCommissionsMap=new HashMap<String, Long>();
		for(HoveyUser agent:agents){  
			ArrayList<Orders> tempOrders=new ArrayList<Orders>();
			Long totalKwh=0L;
			for(Orders order:orders){				
				if(order.getAgent().getAgentNumber().equalsIgnoreCase(agent.getAgentNumber())){
					if(!order.getSentToSupplier().before(startDate)){
						totalKwh+=order.getKwh();						
					}
					tempOrders.add(order);						
				}				
			}//end of agents for					
			if(totalKwh>=kwhRange){	
				System.out.println("agent "+agent.getFirstName()+" kwh "+totalKwh);
				agentsKwh+=totalKwh;				
				eligibleOrders.addAll(tempOrders);
				agentCommissionsMap.put(agent.getFirstName()+" "+agent.getLastName(), totalKwh);		
			}		
		}//end of for Orders		
		
		for(Orders order:eligibleOrders){
			/*if(order.getAgentCommissionStatus().equalsIgnoreCase("unpaid")){*/
					OrdersDto orderDto=OrdersDto.populateOrderDto(order);				
					eligibleTotalKwh+=order.getKwh();	
					orderDto.setAgentCommissionRate(order.getAgentCommissionRate());
					/* Added by Jeevan on August 29,2013 to display week n year for Agent commissions */
					  Calendar orderCal=Calendar.getInstance();
					 // orderCal.setTime(order.getOrderDate());
					  orderCal.setTime(endDate);
					  orderDto.setWeek(orderCal.get(Calendar.WEEK_OF_YEAR));
					  orderDto.setYear(orderCal.get(Calendar.YEAR));
					/*    End of Modification        */
				eligibleOrderDtos.add(orderDto);
				
			/*}*/
		}
		if(null!=eligibleOrderDtos && !eligibleOrderDtos.isEmpty() && eligibleOrderDtos.size()>0){			
			eligibleOrderDtos.get(0).setAgentCommissionsMap(agentCommissionsMap);
			eligibleOrderDtos.get(0).setWeekskWh(agentsKwh);
			eligibleOrderDtos.get(0).setTotalKwh(eligibleTotalKwh);
		}
		return eligibleOrderDtos;		
	}
	
	
	
	
	//for Getting Rescinded Orders of an Agent with no Refund Status..
	public ArrayList<RescindedOrders> getRescindedOrdersoFAgents(ArrayList<OrdersDto> orderDtos)throws Exception{
		ArrayList<HoveyUser> agents=new ArrayList<HoveyUser>();
		for(OrdersDto orderDto:orderDtos){
			HoveyUser agent=this.dealSheetDao.getUserByAgentNumber(orderDto.getCreatedAgent().getAgentNumber());
			agents.add(agent);
		}
		Set<HoveyUser> agentsSet=new HashSet<HoveyUser>();
		agentsSet.addAll(agents);
		agents.clear();
		agents.addAll(agentsSet);   //for removing Duplicates..
		ArrayList<RescindedOrders> resOrders=this.adminDao.getAllRescindedOrdersSatisfyingCriteria(false, agents);		
		return resOrders;
	}
	
	
	
	/*
	 * Gets Agent Commissions Of All Rescinded Accounts where Refund is not made..
	 */
	public ArrayList<AgentCommissionsDto> getAgentCommissionsOfRescindedAccounts(ArrayList<OrdersDto> orderDtos)throws Exception{
		log.info("inside getAgentCommissionsOfRescindedAccounts()");
		ArrayList<RescindedOrders> resOrders=this.getRescindedOrdersoFAgents(orderDtos);	
		ArrayList<Orders> orders=new ArrayList<Orders>();
		Long totalkWh=0L;
		for(RescindedOrders resOrder:resOrders){
			orders.add(resOrder.getOrderId());
		}		
		ArrayList<AgentCommissions> commissions=this.adminDao.getAgentCommissionsOfRescindedAccounts(orders);	
		ArrayList<AgentCommissionsDto> commissionDtos=new ArrayList<AgentCommissionsDto>();
		for(AgentCommissions commission:commissions){
			AgentCommissionsDto commissionsDto=AgentCommissionsDto.populateAgentCommissions(commission);
			totalkWh+=commission.getOrder().getKwh();
			commissionsDto.setTotalKwh(totalkWh);
			commissionDtos.add(commissionsDto);
			
		}		
		return commissionDtos;	
	}
	
	
	
	
	/*
	 * for Editing Comission Agents
	 */	
	public ArrayList<AgentCommissionsDto> getAgentCommissions(String agent,Integer week,Integer year)throws Exception{
		log.info("inside getAgentCommissions()");		
		ArrayList<AgentCommissions> commissions=this.adminDao.getAgentCommissionsByWeek(agent, week,year);
		ArrayList<AgentCommissionsDto> commissionDtos=new ArrayList<AgentCommissionsDto>();
		for(AgentCommissions commission:commissions){
			AgentCommissionsDto commissionsDto=AgentCommissionsDto.populateAgentCommissions(commission); 
			commissionDtos.add(commissionsDto);
		}		
		return commissionDtos;	
	}
	
	
	
	//for handling kWh limit Operations
	public KwhLimit getKwhLimit()throws Exception{
		log.info("inside getKwhLimit()");
		KwhLimit limit=this.adminDao.getKwhLimit();
		return limit;
	}
	
	//for handling kWh Limit Operations......
	public Integer updatekWhLimit(Integer kWhLimit)throws Exception{
		log.info("inside getKwhLimit()");
		KwhLimit limit=this.adminDao.getKwhLimit();
		limit.setkWhLimit(kWhLimit);
		Integer result=this.adminDao.updateKwhLimit(limit);
		return result;
	}
	
		
   /*****************************************For Agent Commissions *?///////////////////////////////////////////////////////////////////////////////////////////*/	
	/*
	 * for Saving/ Updating Agent Commissions.
	 */
	@Transactional
	public Integer saveOrUpdateAgentCommission(Integer id,Integer orderId,Double agentCommission,Double commissionRate,
			Boolean commissionPayable,Integer week,Integer year)throws Exception{
		log.info("inside saveOrUpdateAgentCommission()");
		
		Orders order=this.adminDao.getOrderByOrderId(orderId);
		Integer result=0;
		AgentCommissions commission=new AgentCommissions(); 
		if(null!=id && id>0){                                            /* handling Updates */
			commission.setId(id);
		}	
		
		if(null==commissionPayable){
			order.setAgentCommissionStatus("unpaid");
			order.setAgentCommissionRate(commissionRate);
		}
		else if(!commissionPayable){
			commission.setCommissionPayable(commissionPayable);
			order.setAgentCommissionStatus("paid");		
		}
		else{
			if(commissionRate>0){
				order.setAgentCommissionStatus("paid");		
			}
			else{
				order.setAgentCommissionStatus("unpaid");		
			}
			commission.setCommissionPayable(commissionPayable);
		}		
		Date orderDate=order.getOrderDate();
		Calendar cal=Calendar.getInstance();
		cal.setTime(orderDate);
		commission.setAgent(order.getAgent());
		commission.setAgentCommission(agentCommission);		
		commission.setCommissionRate(commissionRate);
		commission.setOrder(order);
		commission.setSupplierName(order.getSupplierName());
		commission.setWeek(week);	
		commission.setYear(year);		
		this.dealSheetDao.saveDealSheetToDB(order);  	
		
		if(Double.isNaN(agentCommission)){
			result=1; //no need to save if there is nothing to save
		}
		else{
			result = this.adminDao.saveOrUpdateAgentCommissions(commission);
		}
			//Updating Order with Paid Status if Eligilbe		
		return result;
	}
	
	
	
	
	
	public Integer saveOrUpdateAgentCommission(Integer id,Integer orderId,Double agentCommission,Double commissionRate,
			Boolean commissionPayable,Boolean refund,Integer week,Integer year)throws Exception{
		Integer result=this.saveOrUpdateAgentCommission(id, orderId, agentCommission, commissionRate, commissionPayable,week,year);
		Orders order=this.adminDao.getOrderByOrderId(orderId);
		order.setAgentCommissionStatus("paid");
		RescindedOrders resOrder=this.adminDao.getRescindedORderByOrderID(orderId);
		resOrder.setRefundStatus(true);
		this.adminDao.saveOrUpdateRescindedAccount(resOrder);
		this.dealSheetDao.saveDealSheetToDB(order);				
		return result;
	}
	
	
	
  /*****************************************For Agent Commissions *?///////////////////////////////////////////////////////////////////////////////////////////*/	

	
	
	
  /*
   * Reports om Agent COmmmissions ********************************************   *    */
	
	public ArrayList<AgentCommissionsDto> getAgentCommissionsOfaWeekByAgent(Integer week,String agent,Integer year)throws AgentCommissionNotFoundException{
		log.info("inside getAgentCommissionsOfaWeekByAgent()");
		ArrayList<AgentCommissions> commissions=this.adminDao.getCommissionsBetweenDaysWithCriteria(week, agent,year);
		ArrayList<AgentCommissionsDto> commissionDtos=new ArrayList<AgentCommissionsDto>();
		for(AgentCommissions commission:commissions){
			AgentCommissionsDto commissionDto=AgentCommissionsDto.populateAgentCommissions(commission);
			commissionDtos.add(commissionDto);
		}	
		if(commissionDtos.isEmpty() || commissionDtos.size()==0){
			throw new AgentCommissionNotFoundException();
		}
		else{
			return commissionDtos;
		}
	}
	
	/***************************************************************************************************************/

	/*Added by bhagya on may 28th,20-14
	 * this method for getting appropriate commissions
	 */
	public Double getAppropriateUpfrontCommissions(Orders order){
		log.info("inside getAppropriate Upfront Commissions");
		Double appropriateCommission=0.0;
		if(null!=order.getDealStartDate()){
			Integer dealMonths=this.adminService.getNumberOfMonthsBetweenStartdateAndCurrentDate(order.getDealStartDate());					
			Integer year=(dealMonths/12)+1;
			if(year==1){
				appropriateCommission=order.getUpfrontCommission();
			}
			else if(year==2){
				appropriateCommission=order.getUpfrontCommission2();
			}
			else if(year==3){
				appropriateCommission=order.getUpfrontCommission3();
			}
			}
		return appropriateCommission;
	}
}



	