package com.hovey.frontend.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.hovey.backend.admin.exception.AgentCommissionNotFoundException;
import com.hovey.backend.agent.model.KwhLimit;
import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.agent.model.RescindedOrders;
import com.hovey.frontend.agent.dto.AgentCommissionsDto;
import com.hovey.frontend.agent.dto.OrdersDto;
import com.hovey.frontend.agent.dto.RescindedOrdersDto;

public interface CommissionService {

	public Map<String, Double> reconcileCommissions(Double commission,Double upfrontCommission,Integer orderId,Double commissionRate)throws Exception;
	public ArrayList<OrdersDto> getAllEligbleOrdersforAgentCommissions(Date startDate,Date endDate,String agent, Boolean filter)throws Exception;
	public ArrayList<RescindedOrders> getRescindedOrdersoFAgents(ArrayList<OrdersDto> orderDtos)throws Exception;
	public ArrayList<AgentCommissionsDto> getAgentCommissionsOfRescindedAccounts(ArrayList<OrdersDto> orderDtos)throws Exception;
	public Integer saveOrUpdateAgentCommission(Integer id,Integer orderId,Double agentCommission,Double commissionRate,
			Boolean commissionPayable,Integer week,Integer year)throws Exception;
	public ArrayList<AgentCommissionsDto> getAgentCommissions(String agent,Integer week,Integer month)throws Exception;
	public Integer saveOrUpdateAgentCommission(Integer id,Integer orderId,Double agentCommission,Double commissionRate,
			Boolean commissionPayable,Boolean refund,Integer week,Integer year)throws Exception;
	public ArrayList<AgentCommissionsDto> getAgentCommissionsOfaWeekByAgent(Integer week,String agent,Integer year)throws AgentCommissionNotFoundException;
	public KwhLimit getKwhLimit()throws Exception;
	public Integer updatekWhLimit(Integer kWhLimit)throws Exception;
	//for upfront Commission
	public Double getAppropriateUpfrontCommissions(Orders order);
}
