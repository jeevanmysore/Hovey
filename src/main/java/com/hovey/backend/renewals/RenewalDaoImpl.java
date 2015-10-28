package com.hovey.backend.renewals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.persistence.criteria.Expression;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.supplier.exception.SupplierReportsNotFoundException;
import com.hovey.backend.supplier.model.SupplierReports;


@Repository("renewalDao")
@Transactional
public class RenewalDaoImpl implements RenewalDao {

	private static Logger log=Logger.getLogger(RenewalDaoImpl.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	private Integer totalRenewalOrders=0;
	
	
	//Finding Out the Orders which are Closed to Renewals
	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getOrdersCloseToRenewal(Date startDate,Date endDate,Integer pageSize,Integer pageNo, String supplier,String status,String contractType,String serviceType)throws OrderNotFoundException{
		log.info("inside getOrdersCloseToRenewal()");		
		ArrayList<Orders> orders=null;
		ArrayList<Orders> nonRenewableOrders=new ArrayList<Orders>();
	  	Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
		criteria.add(Restrictions.ne("status", "rescinded"));
		criteria.add(Restrictions.between("dealEndDate",startDate,endDate));
		criteria.add(Restrictions.isNotNull("dealEndDate"));
		//sentToSupplier restriction added by bhagya on may 09th,2014
		criteria.add(Restrictions.isNotNull("sentToSupplier"));
		criteria.addOrder(Order.asc("dealEndDate"));
		
		
		if(null!=supplier && supplier!=""){				 
			  criteria.add(Restrictions.eq("supplierName.supplierName", supplier));
		}
		if(null!=status && status!=""){				 
			  criteria.add(Restrictions.eq("status",status));
		}
		if(null!=contractType && contractType!=""){
			criteria.add(Restrictions.eq("contractType", contractType));
		}
		/**
		 * Added Service Type filtering or restriction By Bhagya on October 27th,2014,
		 * Following the client requirement
		 */
		if(null!=serviceType && serviceType!=""){
			criteria.createCriteria("taxId").add(Restrictions.eq("type", serviceType));
		}
		if(!criteria.list().isEmpty()){
			orders=(ArrayList<Orders>) criteria.list();
			
			for(Orders order:orders){		
				Date endDates=determineIfRenewalOrderisRenewed(order,startDate,endDate);
				if(endDates.after(endDate)){
					nonRenewableOrders.add(order);
				}
				
			}
			orders.removeAll(nonRenewableOrders);	
			totalRenewalOrders=orders.size();			
			return orders;	
		}
		else{
			throw new OrderNotFoundException();
		}		
	}
	
	
	//for getting TotalRenewalOrders
	public Integer getTotalRenewalOrders(Date startDate,Date endDate,Integer pageSize,Integer pageNo,String supplier,String status,String contractType,String serviceType) throws Exception{
		log.info("inside getTotalRenewalOrders()");
		getOrdersCloseToRenewal(startDate,endDate, pageSize, null, supplier,status,contractType,serviceType);
		return totalRenewalOrders;
	}
	
	
	
	/*
	 * Added by Jeevan on November 26,2013
	 * Method to check whether the Deal is Renewed.
	 */
	
	public Date determineIfRenewalOrderisRenewed(Orders order,Date startDate,Date endDate){
		log.info("inside determineIfRenewalOrderisRenewed()");
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
		criteria.add(Restrictions.eq("accountNumber", order.getAccountNumber()));
		criteria.add(Restrictions.isNotNull("dealEndDate"));	
		criteria.setProjection(Projections.property("dealEndDate"));			
		ArrayList<Date> endDates=(ArrayList<Date>) criteria.list();
		if(endDates.size()>1)
			Collections.sort(endDates);		
		return endDates.get(endDates.size()-1);
	}
	
	
	//calculating dealEndDate based on StartDate and Term...	
	public Date getEndDateFromStartDateandTerm(Date dealStartDate,String term){
			Calendar calendar=Calendar.getInstance();
			calendar.clear();
			calendar.setTime(dealStartDate);
			calendar.add(Calendar.MONTH, Integer.parseInt(term));
						/* *****Modified by Jeevan on July 18,2013 to considered Deal End Date to include exclusion Date too******
			 *   calendar.add(Calendar.DATE, -1);  //No Need to Reduce a day for Deal End as deal will be from 01/01/xx to 01/01/xy
			 *   
			 * *******/		      
			Date dealEndDate=calendar.getTime();

			return dealEndDate;
		}
	 
		
}
