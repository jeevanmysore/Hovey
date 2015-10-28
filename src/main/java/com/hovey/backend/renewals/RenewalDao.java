package com.hovey.backend.renewals;

import java.util.ArrayList;
import java.util.Date;

import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.supplier.exception.SupplierReportsNotFoundException;
import com.hovey.backend.supplier.model.SupplierReports;

public interface RenewalDao {
	
	public ArrayList<Orders> getOrdersCloseToRenewal(Date startDate,Date endDate,Integer pageSize,Integer pageNo, String supplier,String status,String contractType,String serviceType)throws OrderNotFoundException;
	public Integer getTotalRenewalOrders(Date startDate,Date endDate,Integer pageSize,Integer pageNo, String supplier,String status,String contractType,String serviceType) throws Exception;
	public Date determineIfRenewalOrderisRenewed(Orders order,Date startDate,Date endDate);
}
