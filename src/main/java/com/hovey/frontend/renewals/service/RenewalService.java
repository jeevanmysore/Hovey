package com.hovey.frontend.renewals.service;

import java.util.ArrayList;
import java.util.Date;

import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.frontend.agent.dto.OrdersDto;

public interface RenewalService {
	public ArrayList<OrdersDto> getRenewalOrders(Date startDate,Date endDate,Integer pageSize,Integer pageNo, String supplier,String status,String contractType,String serviceType) throws OrderNotFoundException;
	public Integer getTotalNoofRenewalPages(Date startDate,Date endDate,int pageSize,int pageNo, String supplier,String status,String contractType,String serviceType)throws Exception;
	public ArrayList<Integer> getRenewalsCountInfo(Date startDate,Date endDate,int pageSize,int pageNo, String supplier,String status,String contractType,String serviceType)throws Exception;
	
}
