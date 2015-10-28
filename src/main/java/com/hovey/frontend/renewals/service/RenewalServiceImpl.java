package com.hovey.frontend.renewals.service;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.renewals.RenewalDao;
import com.hovey.backend.supplier.exception.SupplierReportsNotFoundException;
import com.hovey.backend.supplier.model.SupplierReports;
import com.hovey.frontend.agent.dto.OrdersDto;
import com.hovey.frontend.supplier.dto.SupplierReportsDto;


@Service("renewalService")
public class RenewalServiceImpl implements RenewalService {
	
	private static Logger log=Logger.getLogger(RenewalService.class);
	
	
	@Resource(name="renewalDao")
	private RenewalDao renewalDao;
	
	
	/*
	 * Modified by Jeevan on Novemebr 26,2013 as per clients requiremnet
	 * ]
	 * Steps;
	 * 1. get all orders close to renewlas.
	 * 2. compare each n every order with that of databaset o check if it is already renewed and based on new deal start date calculate renewal
	 * 3. if end is after renewal months ignore/remove
	 * 
	 * (non-Javadoc)
	 * @see com.hovey.frontend.renewals.service.RenewalService#getRenewalOrders(int, java.lang.Integer, java.lang.Integer)
	 */
	public ArrayList<OrdersDto> getRenewalOrders(Date startDate,Date endDate,Integer pageSize,Integer pageNo,String supplier,String status,String contractType,String serviceType) throws OrderNotFoundException{	
		log.info("inside getRenewalOrders()");
		ArrayList<Orders> orders=this.renewalDao.getOrdersCloseToRenewal(startDate,endDate, pageSize, pageNo, supplier,status,contractType,serviceType);
		
		ArrayList<Orders> filteredOrders=new ArrayList<Orders>();			
		if(null!=pageSize){
			for(int i=pageNo*pageSize;i<(pageNo*pageSize)+pageSize;i++){				
				if(i<orders.size())
					filteredOrders.add(orders.get(i));	
			}
		}
		else{
			filteredOrders.addAll(orders);
		}
		ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
		for(Orders order:filteredOrders){
			OrdersDto orderDto=OrdersDto.populateOrderDto(order);
			orderDto.setTotalResults(orders.size());
			orderDtos.add(orderDto);
		}
		if(!orderDtos.isEmpty()){
			return orderDtos;
		}
		else{
			throw new OrderNotFoundException();
		}
	}
	
	
	public Integer getTotalNoofRenewalPages(Date startDate,Date endDate,int pageSize,int pageNo, String supplier,String status,String contractType,String serviceType)throws Exception{
		log.info("inside getTotalNoofRenewalPages()");
		int totalRecords=this.renewalDao.getTotalRenewalOrders(startDate,endDate, pageSize, pageNo, supplier,status,contractType,serviceType);
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
	
	
	
	public ArrayList<Integer> getRenewalsCountInfo(Date startDate,Date endDate,int pageSize,int pageNo, String supplier,String status,String contractType,String serviceType)throws Exception{
		log.info("inside getRenewalsCountInfo()");
		int totalRecords=this.renewalDao.getTotalRenewalOrders(startDate,endDate, null, pageNo, supplier,status,contractType,serviceType);
		int result=totalRecords/pageSize;
		int pagesNeeded=0;
		if(totalRecords%pageSize>0){			
			pagesNeeded=result+1;
		}
		else{
			pagesNeeded=result;
		}
		ArrayList<Integer> renewalsCount=new ArrayList<Integer>();
		renewalsCount.add(totalRecords);
		renewalsCount.add(pagesNeeded);		
		
		return renewalsCount;
		
	}
	
	
	
	}

	
	
	
	

