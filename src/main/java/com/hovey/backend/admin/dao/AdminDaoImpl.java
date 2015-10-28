package com.hovey.backend.admin.dao;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.hovey.backend.admin.exception.AgentCommissionNotFoundException;
import com.hovey.backend.agent.dao.AgentDao;
import com.hovey.backend.agent.exception.ContractTypeNotFoundException;
import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.agent.exception.OrdersExistForAccountException;
import com.hovey.backend.agent.model.AgentCommissions;
import com.hovey.backend.agent.model.BillingState;
import com.hovey.backend.agent.model.ContractTypes;
import com.hovey.backend.agent.model.KwhLimit;
import com.hovey.backend.agent.model.OrderBySqlFormula;
import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.agent.model.RescindedOrders;
import com.hovey.backend.agent.model.State;
import com.hovey.backend.agent.model.Transactions;
import com.hovey.backend.agent.model.Utility;
import com.hovey.backend.user.model.HoveyUser;
import com.hovey.frontend.admin.dto.PipelineSearchDto;

/***
 * 
 * @author JEEVAN
 *
 */

@Transactional
@Repository("adminDao")
public class AdminDaoImpl implements AdminDao {

	private static Logger log=Logger.getLogger(AdminDaoImpl.class);
	
	//private  int pageSize=2;
//	private ArrayList<Orders> totalResultOrders;
	private int multiSearchResult=0;
	private int singleSearchResult=0;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	
	@Resource(name="agentDao")
	private AgentDao agentDao;
	
	
/**  Gets List of All Search Fields for Populating Dynamic Search...                                           */
	
		//1. Suplier Name.....
		@SuppressWarnings("unchecked")
		public ArrayList<Utility> getSuppliers() throws Exception{
			log.info("inside getSuppliers()");
			ArrayList<Utility> utilties=(ArrayList<Utility>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Utility.class).list();
			return utilties;			
		}
		
		
		
		//2.. Agent Name...		
		@SuppressWarnings("unchecked")
		public ArrayList<HoveyUser> getAgents()throws Exception{
			log.info("inside getAgents()");
			ArrayList<HoveyUser> agents=(ArrayList<HoveyUser>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class)
					.add(Restrictions.eq("enabled", true)).add(Restrictions.eq("userType", "U").ignoreCase()).addOrder(Order.asc("firstName")).list();
			return agents;
		}
		
		
		
		
		//3. Business Name..		
		@SuppressWarnings("unchecked")
		public ArrayList<String> getBusinessNames()throws Exception{
			log.info("inside getBusinessNames()");
			ArrayList<String> orders=(ArrayList<String>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
					.setProjection(Projections.distinct(Projections.property("businessName"))).addOrder(Order.asc("businessName")).list();				
			return orders;					
		}
		
/**                                           */
		
		
		
/***         Displaying Pipeline(only) with Pagination                                       */		
	//Finds out total no of Orders for PAgination
	
	public int getTotalOrders()throws Exception{
		Integer totalOrders=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				.setProjection(Projections.property("orderId")).list().size();
		return totalOrders;
	}
	
	
	//fetches all the orders of size pageSize
	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getOrdersFromDB(int pageNo,String orderProperty,int range)throws Exception{
		log.info("getOrdersFromDB()");
		int pageSize=range;		
		
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
		
		
		if(orderProperty.equalsIgnoreCase("orderDate") || orderProperty.equalsIgnoreCase("orderId") ||   orderProperty.equalsIgnoreCase("sentToSupplier")
				 || orderProperty.equalsIgnoreCase("dealStartDate") ||  orderProperty.equalsIgnoreCase("upfrontPaidDate")){
			criteria.addOrder(Order.desc(orderProperty));
		}
		else{
			if(orderProperty.equalsIgnoreCase("agent")){
				criteria.createCriteria("agent").addOrder(Order.asc("firstName"));			
			}
			else{
				criteria.addOrder(Order.asc(orderProperty));
			}
		}
		
		if(range>0){
			criteria.setFirstResult(pageNo*pageSize);
			criteria.setMaxResults(pageSize);
		}
		
		
		ArrayList<Orders>orders=(ArrayList<Orders>) criteria.list();		
		if(orders.isEmpty()){
			throw new OrderNotFoundException();
		}
		else{
			return orders;
		}
		
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getCompleteOrders()throws Exception{
		
		log.info("inside getCompleteOrders()");		
		ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				.add(Restrictions.ne("status", "rescinded"))
				.addOrder(Order.desc("orderDate")).list();
		if(!orders.isEmpty()){
			return orders;
		}
		else{
			throw new OrderNotFoundException();
		}		
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getCompleteOrdersForDashboard()throws Exception{
		
		log.info("inside getCompleteOrders()");		
		ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				 .setProjection(Projections.projectionList().add(Projections.property("supplierName")).add(Projections.property("billingState")).add(Projections.property("utility")))
				 
				.addOrder(Order.desc("orderDate")).list();
		if(!orders.isEmpty()){
			return orders;
		}
		else{
			throw new OrderNotFoundException();
		}		
	}
	
	
	
/*** End */
	
	/*@SuppressWarnings("unchecked")
	public ArrayList<Orders> getOrdersFromDBSortByDate(int pageNo,String orderProperty,int range)throws Exception{
		log.info("getOrdersFromDB()");
		pageSize=range;
		ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				.setFirstResult(pageNo*pageSize).setMaxResults(pageSize).addOrder(Order.desc(orderProperty)).list();
		if(orders.isEmpty()){
			throw new OrderNotFoundException();
		}
		else{
			return orders;
		}
	}
	*/
	
	
	
/**        Displaying Deal Sheets                                 */	
	
	//Finds out Total No of Deal Sheets to be used to decide no of pages needed
	
	public int getTotalNoofDealSheets(String searchBy)throws Exception{
		log.info("inside getTotalDealSheets()");	
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("transactionId")));
		//searching
		if(null!=searchBy ){
			criteria.add(Restrictions.ilike("businessName", "%"+searchBy+"%"));
		}				
		//projection..
	
		Integer transactionsCount= criteria.list().size();				
		return transactionsCount;
	}
	
	
	
	
	

	//Returns Total Transactions(Deal sheets) of length pageSize...
	@SuppressWarnings("unchecked")
	public ArrayList<Transactions> getTotalDealSheets(int pageNo,int pageSize,String sortBy,String searchBy)throws Exception{
		log.info("inside getTotalDealSheets()");
	
		ArrayList<Transactions> transactions= new ArrayList<Transactions>();		
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
		
		
		//searching
		if(null!=searchBy ){
			criteria.add(Restrictions.ilike("businessName", "%"+searchBy+"%"));
		}
				
		//projection..
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("transactionId")));
		
		//sorting..
		if(sortBy.equalsIgnoreCase("orderDate") || sortBy.equalsIgnoreCase("dealStartDate") ){
			criteria.addOrder(Order.desc(sortBy));
		}
		else if(sortBy.equalsIgnoreCase("order_id")){
			criteria.addOrder(OrderBySqlFormula.sqlFormula("count(order_id) desc"));
		}
		else if(sortBy.equalsIgnoreCase("agent") || sortBy.equalsIgnoreCase("agentName")){
			if(sortBy.equalsIgnoreCase("agent")){
				criteria.createCriteria("agent").addOrder(Order.asc("agentNumber"));
			}
			else{
				criteria.createCriteria("agent").addOrder(Order.asc("firstName"));
			}
		}	
		else if(sortBy.equalsIgnoreCase("kwh")){
			criteria.addOrder(OrderBySqlFormula.sqlFormula("sum(kwh) desc"));
		}
		else{
			criteria.addOrder(Order.asc(sortBy));
		}		
		
		criteria.setFirstResult(pageNo*pageSize);
		criteria.setMaxResults(pageSize);		
		transactions=(ArrayList<Transactions>) criteria.list();
				
		if(transactions.isEmpty()){
			throw new OrderNotFoundException();
		}
		else{ 
			return transactions;
		}
	}
	
	
	//retreives First Order or Each Deal Sheet...............
	@SuppressWarnings("unchecked")
	public Orders getFirstOrderOfaTransaction(int transactionId)throws Exception{
		log.info("inside getFirstOrderofaTransaction()");
		ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				.add(Restrictions.eq("transactionId.id", transactionId)).addOrder(Order.desc("orderDate")).list();
		if(orders.isEmpty()){
			throw new OrderNotFoundException();
		}
		else{
			return orders.get(0);
		}
	}
	
	
	/* *******Added on July 19,2013 by Jeevan to get Total No of Orders for a Deal************
	 * 
	 *        gets TotalNoOf Accounts for a Deal
	 * 
	 * ********************/
	
	public Integer getTotlOrdersofaDealByDealId(Integer transactionId)throws OrderNotFoundException{
		log.info("inside getTotlOrdersofaDealByDealId( )");
		/*ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				.add(Restrictions.eq("transactionId.id", transactionId)).addOrder(Order.desc("orderDate")).list();*/
		Integer totalOrders= hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				.add(Restrictions.eq("transactionId.id", transactionId)).addOrder(Order.desc("orderDate")).list().size();
		return totalOrders;
	}
	
/**   End of Deal Sheet    **/	
	
	

/**             SINGLE RESULT SEARCH                */
	
	//gets Total No.of Single Search Orders..		for pagination..
		public <T> int getTotalNoOfOrdersOfaFilter(String propertyName,T propertyValue,int pageNo,String orderProperty, int range)throws Exception{			
			log.info("inside getTotalNoOfOrdersOfaFilter()");		
			this.getOrdersFilteredByProperty(propertyName, propertyValue, pageNo, orderProperty, range);
			return singleSearchResult;			
		}
			
	
	
	
	//Retreives Orders of a Single Result Search...
		@SuppressWarnings("unchecked")
		public <T> ArrayList<Orders> getOrdersFilteredByProperty(String propertyName,T propertyValue,int pageNo,String orderProperty,int range)throws Exception{
			log.info("inside getOrdersFilteredByProperty2()");
			int pageSize=range;				
			Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
			
			// Handling for Date Search
			if(propertyName.equalsIgnoreCase("orderDate")){
				SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
				Date date=format.parse(propertyValue.toString());
				
				Calendar c=Calendar.getInstance();
				c.setTime(date);
				c.add(Calendar.DATE, 1);	
				Date nDate=c.getTime();
				criteria.add(Restrictions.between(propertyName, date, nDate));
			}  // Handling Condition for Agents..
			else if(propertyName.equalsIgnoreCase("agent")){
				String name[]=propertyValue.toString().split(" ");
				ArrayList<HoveyUser> agents;
				if(name.length>1){
					String firstName=name[0];
					String lastName=name[1];
					agents=this.agentDao.getUserByAgentName(firstName, lastName);
				}
				else{
					agents=this.agentDao.getUserByFirstName(name[0]);
				}
				if(!agents.isEmpty()){
					 criteria.add(Restrictions.in("agent",agents));
				 }				
			}
			else if(propertyName.equalsIgnoreCase("taxId")){				
				criteria.createCriteria("taxId").add(Restrictions.ilike("taxId",  "%"+propertyValue.toString()+"%"));
			}			
			else if(propertyName.equalsIgnoreCase("customer")){			
				criteria.createCriteria("taxId").add(Restrictions.or(Restrictions.ilike("firstName", "%"+propertyValue+"%"), (Restrictions.ilike("lastName", "%"+propertyValue+"%"))));
			}	
			else if(propertyName.equalsIgnoreCase("phoneNo")){					
				criteria.createCriteria("taxId").add(Restrictions.ilike("phoneNo",  "%"+propertyValue.toString()+"%"));
			}
			else{	
				//modified acc to mail dated 30-05.			
				criteria.add(Restrictions.ilike(propertyName, "%"+propertyValue.toString()+"%"));
			}
			
						
			// Handling Sorting Based on Different Fields..
			if(orderProperty.equalsIgnoreCase("orderDate") || orderProperty.equalsIgnoreCase("sentToSupplier") || orderProperty.equalsIgnoreCase("dealStartDate") ||
					orderProperty.equalsIgnoreCase("upfrontPaidDate")){
				criteria.addOrder(Order.desc(orderProperty));
			}				
			else if(orderProperty.equalsIgnoreCase("agent")){
				criteria.createCriteria("agent").addOrder(Order.asc("firstName"));			
			}
			else{
				criteria.addOrder(Order.asc(orderProperty));
			}
			
			//getting total Orders...			
			ArrayList<Orders> totalOrders=(ArrayList<Orders>) criteria.list();
			if(!totalOrders.isEmpty()){
				singleSearchResult=totalOrders.size();
			}			
			
			//Adding Pagination Conditions...
			if(range>0){
				criteria.setFirstResult(pageNo*pageSize);
				criteria.setMaxResults(pageSize);
			}
			ArrayList<Orders>orders=(ArrayList<Orders>) criteria.list();		
			
			
			if(orders.isEmpty()){
				throw new OrderNotFoundException();
			}
			else{
				return orders;
			}		
		}	
	
	
		
/** END OF SINGLE RESULT SEARCH    **/
		
		
		

/**             MULTI RESULT SEARCH, DYNAMIC SEARCH         */		
		
	// Gets Total No of Dynamic Search Results...
	public int getTotalMultiSearchResults(PipelineSearchDto search,int pageNo,int range,String sortElement)throws Exception{
		log.info("inside getTotalMultiSearchResults()");		
		this.getMultiSearchResults(search, pageNo, range, sortElement);
		return multiSearchResult;		
	}
	
	
	
	//perfoming Dynamic Search
	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getMultiSearchResults(PipelineSearchDto search,int pageNo,int range,String sortElement)throws Exception{
		log.info("inside performMultiSearchResults");
		
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
		
		if(null!=search.getStartDate() && null!=search.getEndDate()){
			Calendar c=Calendar.getInstance();
			c.setTime(search.getEndDate());
			c.add(Calendar.DATE, 1);	
			Date endDate=c.getTime();
			criteria.add(Restrictions.between("orderDate", search.getStartDate(), endDate));
			
		}
		
		if(null!= search.getSupplierName() && search.getSupplierName()!="" ){
			criteria.add(Restrictions.eq("supplierName.supplierName", search.getSupplierName()).ignoreCase());
		}
		
		if(null!=search.getBusinessName() && search.getBusinessName()!=""){
			criteria.add(Restrictions.ilike("businessName", '%'+search.getBusinessName()+'%'));
		}
		
		
		if(null!=search.getContractType() && search.getContractType()!=""){
			criteria.add(Restrictions.eq("contractType", search.getContractType()).ignoreCase());
		}
		
		if(null!=search.getKwh1() &&  null!=search.getKwh2()){
			criteria.add(Restrictions.between("kwh",search.getKwh1(),search.getKwh2()));
		}
		
		if(null!=search.getAgents() && search.getAgents().size()>0){
			criteria.add(Restrictions.in("agent", search.getAgents()));
		}
		// added resAgent,by bhagya on April 30,2014
		
		if(null!=search.getResAgents() && search.getResAgents().size()>0){
			criteria.add(Restrictions.in("resAgent", search.getResAgents()));
		}
		if(null!=search.getStatus() && search.getStatus()!=""){
			if(search.getStatus().contains(",")){				
				String[] status=search.getStatus().split(",");
				if(status.length>1){
					ArrayList<String> statusList=new ArrayList<String>();					
					for(String stat:status){
						statusList.add(stat);
					}
					criteria.add(Restrictions.in("status", statusList));					
				}				
			}
			else{
				criteria.add(Restrictions.eq("status", search.getStatus()).ignoreCase());
			}
		}
		
		if(null!=search.getCommission1() && null!=search.getCommission2()){			
			criteria.add(Restrictions.between("commission", search.getCommission1(), search.getCommission2()));
		}		
		if(null!=search.getSentToSupplier1() && null!=search.getSentToSupplier2()){
			Calendar c=Calendar.getInstance();
			c.setTime(search.getSentToSupplier2());
			c.add(Calendar.DATE, 1);	
			Date endDate=c.getTime();
			criteria.add(Restrictions.between("sentToSupplier", search.getSentToSupplier1(), endDate));
		}		
		if(null!=search.getDealStartDate1() && null!=search.getDealStartDate2()){
			
			Calendar c=Calendar.getInstance();
			c.setTime(search.getDealStartDate2());
			c.add(Calendar.DATE, 1);	
			Date endDate=c.getTime();
			criteria.add(Restrictions.between("dealStartDate", search.getDealStartDate1(), endDate));
		}		
		if(null!=search.getUpfrontCommission1() && null!=search.getUpfrontCommission2()){
			criteria.add(Restrictions.between("upfrontCommission", search.getUpfrontCommission1(), search.getUpfrontCommission2()));
		}
		//added recently
		if(null!=search.getCommissionStatus() && search.getCommissionStatus()!=""){			
			if(search.getCommissionStatus().equalsIgnoreCase("paid")){
				criteria.add(Restrictions.gt("upfrontCommission", 0.0));
			}
			else if(search.getCommissionStatus().equalsIgnoreCase("unpaid")){
				criteria.add(Restrictions.and(Restrictions.ne("status", "rescinded"), Restrictions.eq("upfrontCommission", 0.0)));
			}
			else if(search.getCommissionStatus().equalsIgnoreCase("less")){
				criteria.add(Restrictions.ltProperty("upfrontCommission","commission"));
			}
			else if(search.getCommissionStatus().equalsIgnoreCase("greater")){
				criteria.add(Restrictions.gtProperty("upfrontCommission","commission"));
			}
			else {
				criteria.add(Restrictions.eqProperty("upfrontCommission","commission"));
			}
		}	
		
		if(null!=search.getUpfrontPaidDate1() && null!=search.getUpfrontPaidDate2()){
			
			Calendar c=Calendar.getInstance();
			c.setTime(search.getUpfrontPaidDate2());
			c.add(Calendar.DATE, 1);	
			Date endDate=c.getTime();
			criteria.add(Restrictions.between("upfrontPaidDate", search.getUpfrontPaidDate1(), endDate));
		}
		
		
		if(null!=search.getTerm1() && null!=search.getTerm2()){
			criteria.add(Restrictions.between("term", search.getTerm1().toString(), search.getTerm2().toString()));
			
		}
		
		
		if(null!=search.getNotes() && search.getNotes()!=""){
			criteria.add(Restrictions.ilike("notes", '%'+search.getNotes()+'%'));
		}
		
		
		if(null!=search.getAccountNumber() && search.getAccountNumber()!=""){
			criteria.add(Restrictions.ilike("accountNumber", "%"+search.getAccountNumber()+"%"));
		}
		
	/* *******Added on July 18,2013 to enable States and Utility on Search*********************/	
		if(null!=search.getState()&&  search.getState()!=""){
			criteria.add(Restrictions.or(Restrictions.eq("billingState.state",search.getState()).ignoreCase(),Restrictions.eq("serviceState.state", search.getState()).ignoreCase() ));
		}
		
		if(null!=search.getUtility()&&  search.getUtility()!=""){
			if(search.getUtility().contains(",")){				
				String[] util=search.getUtility().split(",");
				if(util.length>1){
					ArrayList<String>utilList=new ArrayList<String>();					
					for(String utility:util){
						utilList.add(utility);
					}
					criteria.add(Restrictions.in("utility.utility", utilList));					
				}				
			}
			else{
				criteria.add(Restrictions.eq("utility.utility", search.getUtility()).ignoreCase());
			}			
		}	
		
	/* ******End of Modifications*****************/	
		/****Added  service type restriction By Bhagya on Dec 11th,2014 ,as per clients need****/
		if(null!=search.getServiceType() && search.getServiceType()!=""){
			criteria.createCriteria("taxId").add(Restrictions.eq("type", search.getServiceType()));
			
			}
	
		ArrayList<Orders> totalOrders=(ArrayList<Orders>) criteria.list();
		multiSearchResult=totalOrders.size();
		if(sortElement.equalsIgnoreCase("orderId") || sortElement.equalsIgnoreCase("orderDate") || sortElement.equalsIgnoreCase("dealStartDate") || sortElement.equalsIgnoreCase("upfrontPaidDate")){
			criteria.addOrder(Order.desc(sortElement));
		}
		else{			
			if(sortElement.equalsIgnoreCase("agent")){
				criteria.createCriteria("agent").addOrder(Order.asc("firstName"));			
			}
			else{
				criteria.addOrder(Order.asc(sortElement));
			}			
		}	
		
		if(range>0){
			criteria.setFirstResult(pageNo*range);
			criteria.setMaxResults(range).list();
		}
		ArrayList<Orders> orders=(ArrayList<Orders>) criteria.list();
		if(orders.isEmpty()){
			throw new OrderNotFoundException();
		}
		else{
			return orders;
		}
	}
	
	
 /**   END of DYNAMIC SEARCH  */	
	

	
	
	

/**         EDITING PIPELINE   *****************************************************************************
 * 
 *      1. fetches Order by Order Id.
 *      2. Populates Order.
 *      3. Edit Order.  
 *            
 *            
 *            
 *            
 *            
 *            */
	
	
	
	//1 Getting Order by OrderId
		 public Orders getOrderByOrderId(int orderId) throws Exception{
			 log.info("inside getOrderbyOrderId()");
			 Orders order=(Orders) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
					 .add(Restrictions.eq("orderId", orderId)).list().get(0);
			 if(null!=order)
				 return order;
			 else
				throw new OrderNotFoundException();
		 }
		 
		 
		 
		 
		 //for 2,3 Editing the Pipeline
		 public Integer editPipelineData(Orders order) throws Exception{
			 log.info("inside editPipelineData()");
			 Assert.notNull(order);
			 hibernateTemplate.update(order);
			 hibernateTemplate.getSessionFactory().getCurrentSession().flush();
			 return order.getOrderId();
		 }
		 
		 
/** END OF EDIT PIPELINE           */
		 

	
		 
		

		 
	/*** State MAnipulations           *********************************
	 * 
	 *   1. Adding
	 *   2. Deletion.                      
	 *                         
	 *                         
	 *                         */	 
		 //deleting state
		 public void deleteState(State state)throws Exception{
			 log.info("inside deleteState()");
			 BillingState billState=new BillingState();
			 billState.setId(state.getId());
			 hibernateTemplate.delete(billState);
			 hibernateTemplate.delete(state);	
			 hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		 }
		 
		 
		 //adding State
		 public void addState(State state)throws Exception{
			 log.info("inside addState()");
			 BillingState billState=new BillingState();
			 billState.setState(state.getState());
			 hibernateTemplate.save(state);
			 hibernateTemplate.save(billState);
			 hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		 }
		
		 
	
	/** End of State Manipulation   */	 
		 
		 
	/*
	 * Added by Jeevan on July 30,2013.
	 * methods for Dashboard,
	 * Getting Orders by Utility, State..	 
	 */
		//get Orders of utility
	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getOrdersByUtility(String utility)throws OrderNotFoundException{
		log.info("inside getOrdersByUtility()");
		ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				.add(Restrictions.eq("utility.utility", utility)).list();
		if(orders.isEmpty()){
			throw new OrderNotFoundException();
		}
		else{
			return orders;
		}
	}
	
	
	//By state
	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getOrdersByState(String state)throws OrderNotFoundException{
		log.info("inside getOrdersByUtility()");
		ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				.add(Restrictions.eq("serviceState.state", state)).list();
		if(orders.isEmpty()){
			throw new OrderNotFoundException();
		}
		else{
			return orders;
		}
	}
	
	/*
	 * Added By Jeevan on August 16,2013 as per clients need to have Supplier Orders Pie chart..
	 * 	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getOrdersOfaSupplier(String supplierName)throws OrderNotFoundException{
		log.info("inside getOrdersOfaSupplier()");
		ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				.add(Restrictions.eq("supplierName.supplierName", supplierName)).list();
		if(!orders.isEmpty()){
			return orders;
		}
		else{
			throw new OrderNotFoundException();
		}
	}
	
	
	
	/*
	 * getting Orders within range of days
	 */
		 
	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getOrdersBetweenDays(Date startDate,Date endDate)throws OrderNotFoundException{
		log.info("inside getOrdersBetweenDays()");
		
		ArrayList<Orders> orders=null;
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
		if(null==startDate && null == endDate){
			//dont add any restrictions
		}
		else if(null==startDate){
			criteria.add(Restrictions.le("orderDate", endDate));
		}
		else if(null==endDate){
			criteria.add(Restrictions.ge("orderDate", startDate));
		}
		else{
			Calendar cal=Calendar.getInstance();
			cal.setTime(endDate); cal.add(Calendar.DATE, 1); endDate=cal.getTime();
			criteria.add(Restrictions.between("orderDate", startDate, endDate));
		}	
		criteria.addOrder(Order.desc("orderDate"));
		orders=(ArrayList<Orders>) criteria.list();				
		if(orders.isEmpty()){
			throw new OrderNotFoundException();
		}
		else{
			return orders;
		}		
	}
	
	/*
	 * Added by Jeevan on November 21, 2013
	 */
	
	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getOrdersByPaidDateBetweenDays(Date startDate,Date endDate)throws OrderNotFoundException{
		log.info("inside getOrdersByPaidDateBetweenDays( )");

		ArrayList<Orders> orders=null;
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
		if(null==startDate && null == endDate){
			//dont add any restrictions
		}
		else if(null==startDate){
			criteria.add(Restrictions.le("upfrontPaidDate", endDate));
		}
		else if(null==endDate){
			criteria.add(Restrictions.ge("upfrontPaidDate", startDate));
		}
		else{
			Calendar cal=Calendar.getInstance();
			cal.setTime(endDate); cal.add(Calendar.DATE, 1); endDate=cal.getTime();
			criteria.add(Restrictions.between("upfrontPaidDate", startDate, endDate));
		}	
		criteria.addOrder(Order.desc("upfrontPaidDate"));
		orders=(ArrayList<Orders>) criteria.list();				
		if(orders.isEmpty()){
			throw new OrderNotFoundException();
		}
		else{
			return orders;
		}		
	}
	
	
	
	
	
	
	
	
	/*
	 * Added by Jeevan on October 16,2013 to get Active Orders which are not rescinded
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getActiveOrdersBetweenDays(Date startDate,Date endDate)throws OrderNotFoundException{
		log.info("inside getOrdersBetweenDays()");
		
		ArrayList<Orders> orders=null;
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
		criteria.add(Restrictions.ne("status", "rescinded"));
		if(null==startDate && null == endDate){
			
		}
		else if(null==startDate){
			criteria.add(Restrictions.le("orderDate", endDate));
		}
		else if(null==endDate){
			criteria.add(Restrictions.ge("orderDate", startDate));
		}
		else{
			Calendar cal=Calendar.getInstance();
			cal.setTime(endDate); cal.add(Calendar.DATE, 1); endDate=cal.getTime();
			criteria.add(Restrictions.between("orderDate", startDate, endDate));
		}	
		criteria.addOrder(Order.desc("orderDate"));
		orders=(ArrayList<Orders>) criteria.list();				
		if(orders.isEmpty()){
			throw new OrderNotFoundException();
		}
		else{
			return orders;
		}		
	}
	
	
	
	
 /*
  * Added by Jeevan on August 19,2013.
  * To handle Rescinded Accounts Related Operatrions/...
  */
		 
   public Integer saveOrUpdateRescindedAccount(RescindedOrders orders)throws Exception{
	   log.info("inside saveOrUpdateRescindedAccount()");
	   hibernateTemplate.saveOrUpdate(orders);
	   hibernateTemplate.getSessionFactory().getCurrentSession().flush();
	   return orders.getId();
   }
   
   
   public Integer deleteRescindedOrder(RescindedOrders order) throws Exception{
	   log.info("inside deleteRescindedOrdersById()");
	   hibernateTemplate.delete(order);
	   hibernateTemplate.getSessionFactory().getCurrentSession().flush();
	   return order.getId();
   }
   

   @SuppressWarnings("unchecked")
public ArrayList<RescindedOrders> getAllRescindedOrders()throws OrderNotFoundException{
	   log.info("inside getAllRescindedOrders()");
	   ArrayList<RescindedOrders> orders=(ArrayList<RescindedOrders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(RescindedOrders.class).list();
	   if(!orders.isEmpty()){
		   return orders;
	   }
	   else{
		   throw new OrderNotFoundException();
	   }
   }
		 
   
   @SuppressWarnings("unchecked")
public RescindedOrders getRescindedORderByOrderID(Integer orderId)throws OrderNotFoundException{
	   log.info("inside getREscindedOrdersBYID()");
	   ArrayList<RescindedOrders> orders=(ArrayList<RescindedOrders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(RescindedOrders.class)
			   .add(Restrictions.eq("orderId.orderId",orderId)).list();
	   if(!orders.isEmpty()){
		   return orders.get(0);
	   }
	   else{
		   throw new OrderNotFoundException();
	   }	   
   }
   
   @SuppressWarnings("unchecked")
public RescindedOrders getRescindedORderByID(Integer id)throws OrderNotFoundException{
	   log.info("inside getREscindedOrdersBYID()");
	   ArrayList<RescindedOrders> orders=(ArrayList<RescindedOrders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(RescindedOrders.class)
			   .add(Restrictions.eq("id",id)).list();
	   if(!orders.isEmpty()){
		   return orders.get(0);
	   }
	   else{
		   throw new OrderNotFoundException();
	   }	   
   }
   
   
   //Gets Rescinded Orders OF Agents 
  @SuppressWarnings("unchecked")
public ArrayList<RescindedOrders> getAllRescindedOrdersSatisfyingCriteria(boolean refundStatus,ArrayList<HoveyUser> agents)throws OrderNotFoundException{
	  log.info("inside getAllRescindedOrdersSatisfyingCriteria()");
	  ArrayList<RescindedOrders> orders=null;
	  Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(RescindedOrders.class);
	  criteria.add(Restrictions.eq("refundStatus", refundStatus));
	  criteria.createCriteria("orderId").add(Restrictions.in("agent", agents));
	 // criteria.add(Restrictions.in("orderId.agent", agents));
	  orders=(ArrayList<RescindedOrders>) criteria.list();
	  if(!orders.isEmpty()){	  
		  return orders;
	  }
	  else{
		  throw new OrderNotFoundException();
	  }	  
  }
  
  
  
  /*
   * Added by Jeevan on Oct 31, 2013
   * To get Rescinded Orders of all Orders specified
   */
  
  @SuppressWarnings("unchecked")
public ArrayList<RescindedOrders> getRescindedOrdersOfOrders(ArrayList<Orders> orders)throws OrderNotFoundException{
	  log.info("inside getRescindedOrdersOfOrders()");
	  
	  Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(RescindedOrders.class);
	  criteria.add(Restrictions.in("orderId", orders)).list();
	  
	  ArrayList<RescindedOrders> rescindedOrders=(ArrayList<RescindedOrders>) criteria.list();
	  
	  if(!rescindedOrders.isEmpty()){
		  return rescindedOrders;
	  }
	  else{
		  throw new OrderNotFoundException();
	  }	  
  }
  
  
  
  /*
   * Added by Jeevan on October 31, 2013
   * to deletre list of Rescinded Accounts
   */
  public Integer deleteAllRescindedOrdersOfOrder(ArrayList<RescindedOrders> resOrders)throws Exception{
	  log.info("inside deleteAllRescindedOrdersOfOrder()");
	  hibernateTemplate.deleteAll(resOrders);
	  hibernateTemplate.getSessionFactory().getCurrentSession().flush();
	  return resOrders.get(0).getId();
	  
  }
  
  
  
  
  
  
  /**************************************************************for Agent Commissions *****************************************************************/
	
  
  	//for Getting All the Orders Satisfying the Commission Criteria...
  /*
   * 1. kWh>40000
   * 2. status==approved..
   * 3. within that Time Period..
   * 4. agent_commission_status="unpaid... (unpaid is required to distinguish between paid and upaid Orders within that time Period..
   *
   *Note: Condition 4 is implemented in JSP as implmenting it in DAO will only return orders if unpaid orders kwh >400000 even if sum of paid+unpad is greater
   *implementing it in service is a bit difficult so done in jsp 
   */
  
  	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getOrdersForAgentCommissions(Date startDate,Date endDate,String agent)throws OrderNotFoundException{
  		log.info("inside getOrdersForAgentCommissions()");
  		ArrayList<Orders> orders=null;
  		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);  		
  		criteria.add(Restrictions.between("orderDate", startDate, endDate));
  		//criteria.add(Restrictions.eq("status", "approved"));
  		if(null!=agent && agent!=""){
  			criteria.add(Restrictions.eq("agent.agentNumber", agent)); 
  		}
  		criteria.addOrder(Order.desc("orderDate"));
  		orders=(ArrayList<Orders>) criteria.list();  		
		if(!orders.isEmpty()){
			return orders;
		}
		else{
			throw new OrderNotFoundException();
		}  		
  	} 
  	
  	
  	/*
  	 *Modified by Jeevan.
  	 *Dont apply agent Unpaid Restriction here . can apply at service layer
  	 */
  	
  	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getOrdersEligibleForCommissions(Date startDate,Date endDate,String agent,Boolean filter) throws OrderNotFoundException{
  		log.info("inside getOrdersEligibleForCommissions()");  		
  		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
  		criteria.add(Restrictions.le("sentToSupplier", endDate));
  		//criteria.add(Restrictions.between("upfrontPaidDate", startDate, endDate));
  		if(filter==true){
  			criteria.add(Restrictions.ge("sentToSupplier", startDate));
  		}
  		//criteria.add(Restrictions.eq("agentCommissionStatus", "unpaid"));
  		if(null!=agent && agent!=""){
  			criteria.add(Restrictions.eq("agent.agentNumber", agent)); 
  		}
  		criteria.addOrder(Order.desc("orderDate"));
  		ArrayList<Orders> orders=(ArrayList<Orders>) criteria.list();  		
		if(!orders.isEmpty()){
			return orders;
		}
		else{
			throw new OrderNotFoundException();
		}  	
  	}
  	
  	
  	
  	
  	
  	
  	
  	
  /**************************************************************for Agent Commissions *****************************************************************/
  	
  	
  	
  	
  	
 /***********************KWH LIMIT**************************************/
  	public Integer updateKwhLimit(KwhLimit kwh)throws Exception{
  		hibernateTemplate.update(kwh);
  		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
  		return kwh.getId();
  	}
  	
  	
  	@SuppressWarnings("unchecked")
	public  KwhLimit getKwhLimit()throws Exception{
  		ArrayList<KwhLimit> kwhs=(ArrayList<KwhLimit>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(KwhLimit.class)
  				.add(Restrictions.eq("id", 1)).list();
  		return kwhs.get(0);
  	}
  	

 /***********************KWH LIMIT**************************************/
  
  	
 /**********************************************for Agent Commissions************************************************************************************/
  public Integer saveOrUpdateAgentCommissions(AgentCommissions commissions)throws Exception{
	  log.info("inside saveOrUpdateAgentCommissions()");
	  hibernateTemplate.saveOrUpdate(commissions);
	  hibernateTemplate.getSessionFactory().getCurrentSession().flush();
	  return commissions.getId();
  }
  
  
  public Integer saveOrUpdateAgentCommissions(ArrayList<AgentCommissions> commissions)throws Exception{
	  log.info("inside saveOrUpdateAgentCommissions()");
	  hibernateTemplate.saveOrUpdate(commissions);
	  hibernateTemplate.getSessionFactory().getCurrentSession().flush();
	  return commissions.get(0).getId();
  }
  
  //gets all Agent Commissions
  @SuppressWarnings("unchecked")
public ArrayList<AgentCommissions> getAgentCommissions()throws AgentCommissionNotFoundException{
	  log.info("inside getAgentCommissions()");
	  Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(AgentCommissions.class);
	  ArrayList<AgentCommissions> commissions=null;
	  commissions=(ArrayList<AgentCommissions>) criteria.list();
	  if(!commissions.isEmpty()){
		 return commissions;
	  }
	  else{
		  throw new AgentCommissionNotFoundException();
	  }	  
  }
  	
  
  //Getting Agent Commissions Of All Rescinded- non Refunded Orders..
  @SuppressWarnings("unchecked")
public ArrayList<AgentCommissions> getAgentCommissionsOfRescindedAccounts(ArrayList<Orders> orders)throws AgentCommissionNotFoundException{
	  log.info("inside getAgentCommissionsOfRescindedAccounts()");
	  Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(AgentCommissions.class);
	  criteria.add(Restrictions.in("order",orders ));
	  Double i=0.0;
	  criteria.add(Restrictions.gt("agentCommission", i));
	  ArrayList<AgentCommissions> agentCommissions=null;
	  agentCommissions=(ArrayList<AgentCommissions>) criteria.list();
	  if(!agentCommissions.isEmpty()){
		  return agentCommissions;
	  }
	  else{
		  throw new AgentCommissionNotFoundException();
	  }
	  
  }
  
  
  //Getting Agent Commissions Of All Rescinded- non Refunded Orders..
  @SuppressWarnings("unchecked")
public ArrayList<AgentCommissions> getAgentCommissionsByWeek(String agentNumber,Integer week,Integer year)throws AgentCommissionNotFoundException{
	  log.info("inside getAgentCommissionsOfRescindedAccounts()");
	  Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(AgentCommissions.class);
	  criteria.add(Restrictions.isNotNull("commissionPayable"));
	  if(null!=week){
		  criteria.add(Restrictions.eq("week",week ));
	  }	  	  
	  if(null!=year){
		  criteria.add(Restrictions.eq("year",year ));
	  }
	  ArrayList<AgentCommissions> agentCommissions=null;
	  if(null!= agentNumber && agentNumber!=""){
		  criteria.createCriteria("order").createCriteria("agent").add(Restrictions.eq("agentNumber", agentNumber));
	  }	  
	  agentCommissions=(ArrayList<AgentCommissions>) criteria.list();
	  if(!agentCommissions.isEmpty()){
		  return agentCommissions;
	  }
	  else{
		  throw new AgentCommissionNotFoundException();
	  }
	  
  }
  
  
	/******************************************************FOR REPORTS COMMISSON ***************************************************************************/
	
  
  //Gets Agent Commissions of a week and Agent..
	@SuppressWarnings("unchecked")
	public ArrayList<AgentCommissions> getCommissionsBetweenDaysWithCriteria(Integer week,String agent,Integer year)throws AgentCommissionNotFoundException{
		log.info("inside getCommissionsBetweenDaysWithCriteria()");
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(AgentCommissions.class);		
		ArrayList<AgentCommissions> commissions=null;
		if(null!=agent && agent!=""){
			criteria.createCriteria("order").createCriteria("agent").add(Restrictions.eq("agentNumber", agent));
		}			
		if(week!=null && week>0){
			criteria.add(Restrictions.eq("week", week));
		}
		
		if(year!=null && year>0){
			criteria.add(Restrictions.eq("year", year));
		}
		commissions=(ArrayList<AgentCommissions>) criteria.list();		
		return commissions;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<AgentCommissions> getAgentCommissionsByAgentNumber(String agentNumber)throws AgentCommissionNotFoundException{
		log.info("inside getAgentCommissionsByAgentNumber()" );
		
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(AgentCommissions.class);
		criteria.createCriteria("agent").add(Restrictions.eq("agentNumber", agentNumber));		
		ArrayList<AgentCommissions> agentCommissions=(ArrayList<AgentCommissions>) criteria.list();
		if(!agentCommissions.isEmpty()){
			return agentCommissions;
		}
		else{
			throw new AgentCommissionNotFoundException();
		}
	}
	
	
/*******************************************************************************************************/
  
  
	
	
	/*
	 * Added by Jeevan on Octonber 31m, 2013
	 * to get list of agentCommisisons of Orders
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<AgentCommissions> getAgentCommissionsOfOrders(ArrayList<Orders> orders)throws AgentCommissionNotFoundException{
		log.info("getAgentCommissionsOfOrders()");
		
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(AgentCommissions.class);
		criteria.add(Restrictions.in("order", orders));
		ArrayList<AgentCommissions> commissions=(ArrayList<AgentCommissions>) criteria.list();
		
		if(!commissions.isEmpty()){
			return commissions;
		}
		else{
			throw new AgentCommissionNotFoundException();
		}	
		
	}
	
	
	/*
	 * Added by Jeevan on October 31 to delete Agent Commisisons of Orders
	 */
	
	public Integer deleteAgentCommissionsOfOrders(ArrayList<AgentCommissions> commissions)throws Exception{
		log.info("insidedeleteAgentCommissionsOfOrders( )");
		
		hibernateTemplate.deleteAll(commissions);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		return commissions.get(0).getId();
	}
	
	
	
	
	
	/*
	 * Added by Jeevan on November 22, 2013.
	 * 
	 * Method to get Agent Commissions based on Week and Year..
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<AgentCommissions> getAgentCommissionsByWeekYear(Integer week,Integer year) throws AgentCommissionNotFoundException{
		log.info("inside getAgentCommissionsByWeekYear( )");
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(AgentCommissions.class);
		criteria.add(Restrictions.and(Restrictions.eq("week", week), Restrictions.eq("year", year)));
		ArrayList<AgentCommissions> agentCommissions=(ArrayList<AgentCommissions>) criteria.list();
		if(!agentCommissions.isEmpty()){
			return agentCommissions;
		}
		else{
			throw new AgentCommissionNotFoundException();
		}		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
  
  	
 /**************************************************for Agent Commissions********************************************************************************/
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  /****************************************************************************************888888888888888888888888*******************************************************************/
		 
 /** Code from PRAGA for Dashboard              */		 
		 
		 @SuppressWarnings("unchecked")
			@Override
			public ArrayList<Orders> getOrdersBetweenDays(String startDate,
					String endDate) throws Exception {
				// TODO Auto-generated method stub
				
				return (ArrayList<Orders>) hibernateTemplate.find("from Orders where dealStartDate between '"+startDate+"' and '"+endDate+"'");
			} 
	
 /** Code from PRAGA for Dashboard              */
		 
	 

/*****************************************************************For ContractType ***********************************************************************************/

/*
 * Added by Bhagya on May 07,2014.
 * To handle ContractType Related Operatrions/...
 * 			 
 */

	public Integer saveOrUpdateContractType(ContractTypes contractType)throws Exception{
	   log.info("inside saveOrUpdateContractType()");
	   hibernateTemplate.saveOrUpdate(contractType);
	   hibernateTemplate.getSessionFactory().getCurrentSession().flush();
	   return contractType.getId();
		}
	
	
	public Integer deleteContractType(ContractTypes contractType) throws Exception{
		   log.info("inside deleteContractType()");
		   hibernateTemplate.delete(contractType);
		   hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		   return contractType.getId();
	   }
	
	
	
	public ArrayList<ContractTypes> getAllContractType()throws ContractTypeNotFoundException{
		log.info("inside getAllContractType()");
		   ArrayList<ContractTypes> contractType=(ArrayList<ContractTypes>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(ContractTypes.class).list();
		   if(!contractType.isEmpty()){
			   return contractType;
		   }
		   else{
			   throw new ContractTypeNotFoundException();
		   }
	   }
	
	
	/*
	 * Created by Jeevan on May 29, 2014
	 * MEthod to get All Anniversary Payments
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getAllAnniversaryPaymentOrders(Integer pageNo, Integer range)throws OrderNotFoundException{
		log.info("inside getAllAnniversaryPayments()");
		ArrayList<Orders> orders=null;
		Date date=new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -1);
		Date thresholdDate=cal.getTime();
		Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
		criteria.add(Restrictions.le("dealStartDate",thresholdDate));
		//added on June 02, 2014 to ignore 1 year orders
		criteria.add(Restrictions.gt("term", "12"));
		criteria.add(Restrictions.ne("status", "rescinded"));
		int size=criteria.list().size();
		if(null!=pageNo || null!=range){
			criteria.setFirstResult(pageNo*range);
			criteria.setMaxResults(range);
		}
		orders=(ArrayList<Orders>) criteria.list(); 
		
		if(!orders.isEmpty()){
			for(Orders order:orders){
				order.setTotalResults(size); 
			}
			return orders;
		}
		else{
			throw new OrderNotFoundException();
		}
	}
	
	
	
	
	//Retreives Orders of a Single Result Search...
	/*
	 * Created by Jeevan on May 30, 2014 Method to get Anniversary Payments	 */
			@SuppressWarnings("unchecked")
			public <T> ArrayList<Orders> getOrdersFilteredByPropertyForAnniversaryPayments(String propertyName,T propertyValue,Integer pageNo,String orderProperty,Integer range)throws Exception{
				log.info("inside getOrdersFilteredByProperty2()");
						
				Date date1=new Date();
				Calendar cal=Calendar.getInstance();
				cal.setTime(date1);
				cal.add(Calendar.YEAR, -1);
				Date thresholdDate=cal.getTime();
				Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
				criteria.add(Restrictions.le("dealStartDate",thresholdDate));
				criteria.add(Restrictions.ne("status", "rescinded"));
				//added on June 02, 2014 to ignore 1 year orders
				criteria.add(Restrictions.gt("term", "12"));
				// Handling for Date Search
				if(propertyName.equalsIgnoreCase("orderDate")){
					SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
					Date date=format.parse(propertyValue.toString());
					
					Calendar c=Calendar.getInstance();
					c.setTime(date);
					c.add(Calendar.DATE, 1);	
					Date nDate=c.getTime();
					criteria.add(Restrictions.between(propertyName, date, nDate));
				}  // Handling Condition for Agents..
				else if(propertyName.equalsIgnoreCase("agent")){
					String name[]=propertyValue.toString().split(" ");
					ArrayList<HoveyUser> agents;
					if(name.length>1){
						String firstName=name[0];
						String lastName=name[1];
						agents=this.agentDao.getUserByAgentName(firstName, lastName);
					}
					else{
						agents=this.agentDao.getUserByFirstName(name[0]);
					}
					if(!agents.isEmpty()){
						 criteria.add(Restrictions.in("agent",agents));
					 }				
				}
				else if(propertyName.equalsIgnoreCase("taxId")){				
					criteria.createCriteria("taxId").add(Restrictions.ilike("taxId",  "%"+propertyValue.toString()+"%"));
				}			
				else if(propertyName.equalsIgnoreCase("customer")){			
					criteria.createCriteria("taxId").add(Restrictions.or(Restrictions.ilike("firstName", "%"+propertyValue+"%"), (Restrictions.ilike("lastName", "%"+propertyValue+"%"))));
				}	
				else if(propertyName.equalsIgnoreCase("phoneNo")){					
					criteria.createCriteria("taxId").add(Restrictions.ilike("phoneNo",  "%"+propertyValue.toString()+"%"));
				}
				else{	
					//modified acc to mail dated 30-05.			
					criteria.add(Restrictions.ilike(propertyName, "%"+propertyValue.toString()+"%"));
				}
				
							
				// Handling Sorting Based on Different Fields..
				if(orderProperty.equalsIgnoreCase("orderDate") || orderProperty.equalsIgnoreCase("sentToSupplier") || orderProperty.equalsIgnoreCase("dealStartDate") ||
						orderProperty.equalsIgnoreCase("upfrontPaidDate")){
					criteria.addOrder(Order.desc(orderProperty));
				}				
				else if(orderProperty.equalsIgnoreCase("agent")){
					criteria.createCriteria("agent").addOrder(Order.asc("firstName"));			
				}
				else{
					criteria.addOrder(Order.asc(orderProperty));
				}
				
				//getting total Orders...			
				@SuppressWarnings("unused")
				ArrayList<Orders> totalOrders=(ArrayList<Orders>) criteria.list();		
				int size=criteria.list().size();
				//Adding Pagination Conditions...
				if(null!=range){
					criteria.setFirstResult(pageNo*range);
					criteria.setMaxResults(range);
				}
				ArrayList<Orders>orders=(ArrayList<Orders>) criteria.list();		
								
				if(orders.isEmpty()){
					throw new OrderNotFoundException();
				}
				else{
					orders.get(0).setTotalResults(size);

					return orders;
				}		
			}	
		
	
		/** Multi SEarch for Anniversary Payments             */
			
			@SuppressWarnings("unchecked")
			public ArrayList<Orders> getMultiSearchResultsOFAnniversaryPayments(PipelineSearchDto search,Integer pageNo,Integer range,String sortElement)throws Exception{
				log.info("inside performMultiSearchResults");
				
				Date date1=new Date();
				Calendar cal=Calendar.getInstance();
				cal.setTime(date1);
				cal.add(Calendar.YEAR, -1);
				Date thresholdDate=cal.getTime();
				Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
				criteria.add(Restrictions.le("dealStartDate",thresholdDate));
				criteria.add(Restrictions.ne("status", "rescinded"));
				//added on June 02, 2014 to ignore 1 year orders
				criteria.add(Restrictions.gt("term", "12"));
				
				if(null!=search.getStartDate() && null!=search.getEndDate()){
					Calendar c=Calendar.getInstance();
					c.setTime(search.getEndDate());
					c.add(Calendar.DATE, 1);	
					Date endDate=c.getTime();
					criteria.add(Restrictions.between("orderDate", search.getStartDate(), endDate));
					
				}
				
				if(null!= search.getSupplierName() && search.getSupplierName()!="" ){
					criteria.add(Restrictions.eq("supplierName.supplierName", search.getSupplierName()).ignoreCase());
				}
				
				if(null!=search.getBusinessName() && search.getBusinessName()!=""){
					criteria.add(Restrictions.ilike("businessName", '%'+search.getBusinessName()+'%'));
				}
				
				
				if(null!=search.getContractType() && search.getContractType()!=""){
					criteria.add(Restrictions.eq("contractType", search.getContractType()).ignoreCase());
				}
				
				if(null!=search.getKwh1() &&  null!=search.getKwh2()){
					criteria.add(Restrictions.between("kwh",search.getKwh1(),search.getKwh2()));
				}
				
				if(null!=search.getAgents() && search.getAgents().size()>0){
					criteria.add(Restrictions.in("agent", search.getAgents()));
				}
				// added resAgent,by bhagya on April 30,2014
				
				if(null!=search.getResAgents() && search.getResAgents().size()>0){
					criteria.add(Restrictions.in("resAgent", search.getResAgents()));
				}
				if(null!=search.getStatus() && search.getStatus()!=""){
					if(search.getStatus().contains(",")){				
						String[] status=search.getStatus().split(",");
						if(status.length>1){
							ArrayList<String> statusList=new ArrayList<String>();					
							for(String stat:status){
								statusList.add(stat);
							}
							criteria.add(Restrictions.in("status", statusList));					
						}				
					}
					else{
						criteria.add(Restrictions.eq("status", search.getStatus()).ignoreCase());
					}
				}
				
				if(null!=search.getCommission1() && null!=search.getCommission2()){			
					criteria.add(Restrictions.between("commission", search.getCommission1(), search.getCommission2()));
				}		
				if(null!=search.getSentToSupplier1() && null!=search.getSentToSupplier2()){
					Calendar c=Calendar.getInstance();
					c.setTime(search.getSentToSupplier2());
					c.add(Calendar.DATE, 1);	
					Date endDate=c.getTime();
					criteria.add(Restrictions.between("sentToSupplier", search.getSentToSupplier1(), endDate));
				}		
				if(null!=search.getDealStartDate1() && null!=search.getDealStartDate2()){
					
					Calendar c=Calendar.getInstance();
					c.setTime(search.getDealStartDate2());
					c.add(Calendar.DATE, 1);	
					Date endDate=c.getTime();
					criteria.add(Restrictions.between("dealStartDate", search.getDealStartDate1(), endDate));
				}		
				if(null!=search.getUpfrontCommission1() && null!=search.getUpfrontCommission2()){
					criteria.add(Restrictions.between("upfrontCommission", search.getUpfrontCommission1(), search.getUpfrontCommission2()));
				}
				//added recently
				if(null!=search.getCommissionStatus() && search.getCommissionStatus()!=""){			
					if(search.getCommissionStatus().equalsIgnoreCase("paid")){
						criteria.add(Restrictions.gt("upfrontCommission", 0.0));
					}
					else if(search.getCommissionStatus().equalsIgnoreCase("unpaid")){
						criteria.add(Restrictions.and(Restrictions.ne("status", "rescinded"), Restrictions.eq("upfrontCommission", 0.0)));
					}
					else if(search.getCommissionStatus().equalsIgnoreCase("less")){
						criteria.add(Restrictions.ltProperty("upfrontCommission","commission"));
					}
					else if(search.getCommissionStatus().equalsIgnoreCase("greater")){
						criteria.add(Restrictions.gtProperty("upfrontCommission","commission"));
					}
					else {
						criteria.add(Restrictions.eqProperty("upfrontCommission","commission"));
					}
				}	
				
				if(null!=search.getUpfrontPaidDate1() && null!=search.getUpfrontPaidDate2()){
					
					Calendar c=Calendar.getInstance();
					c.setTime(search.getUpfrontPaidDate2());
					c.add(Calendar.DATE, 1);	
					Date endDate=c.getTime();
					criteria.add(Restrictions.between("upfrontPaidDate", search.getUpfrontPaidDate1(), endDate));
				}
				
				
				if(null!=search.getTerm1() && null!=search.getTerm2()){
					String term1=search.getTerm1().toString();
					if(Integer.valueOf(search.getTerm1())<12){
						term1="12";
					}
					criteria.add(Restrictions.between("term", term1, search.getTerm2().toString()));
					
				}
				
				
				if(null!=search.getNotes() && search.getNotes()!=""){
					criteria.add(Restrictions.ilike("notes", '%'+search.getNotes()+'%'));
				}
				
				
				if(null!=search.getAccountNumber() && search.getAccountNumber()!=""){
					criteria.add(Restrictions.ilike("accountNumber", "%"+search.getAccountNumber()+"%"));
				}
				
			/* *******Added on July 18,2013 to enable States and Utility on Search*********************/	
				if(null!=search.getState()&&  search.getState()!=""){
					criteria.add(Restrictions.or(Restrictions.eq("billingState.state",search.getState()).ignoreCase(),Restrictions.eq("serviceState.state", search.getState()).ignoreCase() ));
				}
				
				if(null!=search.getUtility()&&  search.getUtility()!=""){
					if(search.getUtility().contains(",")){				
						String[] util=search.getUtility().split(",");
						if(util.length>1){
							ArrayList<String>utilList=new ArrayList<String>();					
							for(String utility:util){
								utilList.add(utility);
							}
							criteria.add(Restrictions.in("utility.utility", utilList));					
						}				
					}
					else{
						criteria.add(Restrictions.eq("utility.utility", search.getUtility()).ignoreCase());
					}			
				}	
				
			/* ******End of Modifications*****************/	
				/****Added  service type restriction By Bhagya on Dec 11th,2014 ,as per clients need****/
				if(null!=search.getServiceType() && search.getServiceType()!=""){
						criteria.createCriteria("taxId").add(Restrictions.eq("type", search.getServiceType()));
					
					}
				ArrayList<Orders> totalOrders=(ArrayList<Orders>) criteria.list();
				multiSearchResult=totalOrders.size();
				if(sortElement.equalsIgnoreCase("orderId") || sortElement.equalsIgnoreCase("orderDate") || sortElement.equalsIgnoreCase("dealStartDate") || sortElement.equalsIgnoreCase("upfrontPaidDate")){
					criteria.addOrder(Order.desc(sortElement));
				}
				else{			
					if(sortElement.equalsIgnoreCase("agent")){
						criteria.createCriteria("agent").addOrder(Order.asc("firstName"));			
					}
					else{
						criteria.addOrder(Order.asc(sortElement));
					}			
				}	
				int size=criteria.list().size();
				if(null!=range){
					criteria.setFirstResult(pageNo*range);
					criteria.setMaxResults(range).list();
				}
				ArrayList<Orders> orders=(ArrayList<Orders>) criteria.list();
				
				if(orders.isEmpty()){
					throw new OrderNotFoundException();
				}
				else{
					orders.get(0).setTotalResults(size);
					return orders;
				}
			}
			
			
		 /**   END of DYNAMIC SEARCH  */	
		
			/*Added by bhagya on october 10th,2014
			 getting State By ID*/
			@SuppressWarnings("unchecked")
			 public State getStateById(Integer Id){
				 log.info("inside getStateById()");
				 Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(State.class);
				ArrayList<State> states= (ArrayList<State>) criteria.add(Restrictions.eq("id",Id)).list();
				State state=states.get(0);
				return state;
			 }
			
			/*Added by bhagya on october 10th,2014
			Getting Billing State By Id*/
			@SuppressWarnings("unchecked")
			 public BillingState getBillingStateById(Integer Id){
				 log.info("inside getBillingStateById()");
				 Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(BillingState.class);
				ArrayList<BillingState> billingState=(ArrayList<BillingState>) criteria.add(Restrictions.eq("id",Id)).list();
				BillingState state=billingState.get(0);
				return state;
			 }
	
			/*Added by bhagya on october 10th,2014
			getting billing State By StateName*/
			@SuppressWarnings("unchecked")
			public BillingState getBillingStateByName(String stateName){
				 log.info("inside getBillingStateByName()");
				 Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(BillingState.class);
				 ArrayList<BillingState> billingState=(ArrayList<BillingState>) criteria.add(Restrictions.eq("state",stateName)).list();
				 BillingState state=billingState.get(0);
				return state;
			}
			/*Added by bhagya on october 10th,2014
			deleting the state and billing state*/
			 public void deleteStateAndBillingState(State state,BillingState billingState)throws Exception{
				 log.info("inside deleteState()");
				 hibernateTemplate.delete(billingState);
				 hibernateTemplate.delete(state);	
				 hibernateTemplate.getSessionFactory().getCurrentSession().flush();
			 }
			
			
			
	
	
}
