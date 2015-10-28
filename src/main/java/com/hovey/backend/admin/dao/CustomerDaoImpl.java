package com.hovey.backend.admin.dao;

import java.util.ArrayList;




import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.impl.CriteriaImpl.CriterionEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;



import com.hovey.backend.agent.exception.CustomersNotFoundException;
import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.agent.model.Customer;
import com.hovey.backend.agent.model.OrderBySqlFormula;
import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.supplier.model.SupplierMapping;


@Transactional
@Repository("customerDao")
public class CustomerDaoImpl implements CustomerDao {

	private static Logger log=Logger.getLogger(CustomerDaoImpl.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	
	/*private static int customerSize;
	private static int pageSize=20;
	*/
	
	
	//gets Total No of Records..	
			
			@SuppressWarnings("unchecked")
			public int getTotalCustomers()throws Exception{
				log.info("inside getCustomersOfAgent()");
				ArrayList<Customer> customers =(ArrayList<Customer>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)						
						.setProjection(Projections.projectionList().add(Projections.groupProperty("taxId")))
						.list();
				if(customers.isEmpty()){
					throw new CustomersNotFoundException();
				}
				else{
					return customers.size();
				}		
			}
		
	/*
	 * Added on August 26,2013 by Jeevan in order to Display Business Name instead of Customer Name in Manage Commissions.
	 */
	//Retreiving All Customers...
		@SuppressWarnings("unchecked")
		public ArrayList<Customer> getAllCustomers()throws CustomersNotFoundException{
			log.info("inside getAllCustomers()");
			ArrayList<Customer> customers=(ArrayList<Customer>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Customer.class).list();
			if(!customers.isEmpty()){
				return customers;
			}
			else{
				throw new CustomersNotFoundException();
			}
		}
		
	

		
	
			
			
	
	//Retreives all the Customers of pageSize;
			@SuppressWarnings("unchecked")
			public ArrayList<Customer> getCustomers(int pageNo,int pageSize)throws Exception{
				log.info("inside getCustomersOfAgent()");
				
				Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Customer.class);
				criteria.addOrder(Order.asc("firstName"));
				criteria.setFirstResult(pageNo*pageSize);
				criteria.setMaxResults(pageSize);
				ArrayList<Customer> customers =(ArrayList<Customer>)criteria.list();
				if(customers.isEmpty()){
					throw new CustomersNotFoundException();
				}
				else{
					return customers;
				}		
			}
			
			
			
			
			
			
		
			/*
			 * Getting Customer from Orders
			 * (non-Javadoc)
			 * @see com.hovey.backend.admin.dao.CustomerDao#getCustomersFromOrders(int, int)
			 */			
		@SuppressWarnings("unchecked")
		public ArrayList<Customer> getCustomersFromOrders(int pageNo,int pageSize,String sortBy,String searchBy)throws Exception{
			log.info("inside getCustomersFromOrders()" );
			
			ArrayList<Customer> customers=null;
			Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class);
				//for searching
				if(null!=searchBy){	
					
					
					criteria.add(Restrictions.like("businessName","%"+searchBy+"%"));
				}
				//Getting Unique Customers
				criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("taxId")));
				
				//for Sorting.				
				if(sortBy.equalsIgnoreCase("order_id")){
					criteria.addOrder(OrderBySqlFormula.sqlFormula("count(order_id) desc"));
				}
				else if(sortBy.equalsIgnoreCase("commission") || sortBy.equalsIgnoreCase("upfront_commission")){
					criteria.addOrder(OrderBySqlFormula.sqlFormula("sum("+sortBy+") asc"));
				}
				else if(sortBy.equalsIgnoreCase("businessName")){
					criteria.addOrder(Order.asc(sortBy));
				}
				else{
					criteria.createCriteria("taxId").addOrder(Order.asc(sortBy));
				}	
		     if(pageSize>0){
		    	 criteria.setFirstResult(pageNo*pageSize);
		    	 criteria.setMaxResults(pageSize);		
		     }
			customers=(ArrayList<Customer>) criteria.list();
			if(!customers.isEmpty()){
				return customers;
			}
			else{
				throw new CustomersNotFoundException();
			}			
		}
			
			
		

		//gets Total No of Records..	
				@SuppressWarnings("unchecked")
				public int getTotalCustomers(String searchBy)throws Exception{
					log.info("inside getCustomersOfAgent()");
					ArrayList<Customer> customers =(ArrayList<Customer>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
							.add(Restrictions.like("businessName","%"+searchBy+"%"))
							.setProjection(Projections.projectionList().add(Projections.groupProperty("taxId")))
							.list();
					if(customers.isEmpty()){
						throw new CustomersNotFoundException();
					}
					else{
						return customers.size();
					}		
				}
			
		
	
  // Getting Customer by CustomerId 	
	@SuppressWarnings("unchecked")
	public Customer getCustomerById(Integer customerId)throws CustomersNotFoundException{
		log.info("inside getCustomerById()");
		ArrayList<Customer> customers=(ArrayList<Customer>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Customer.class)
				.add(Restrictions.eq("customerId",customerId)).list();
		if(!customers.isEmpty()){
			return customers.get(0);
		}
		else{
			throw new CustomersNotFoundException();
		}
	}
	
	
	//editing the Customer
	public Integer editCustomer(Customer customer)throws Exception{
		log.info("inside editCustomer()");
		hibernateTemplate.saveOrUpdate(customer);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		return customer.getCustomerId();
	}
	
	
 /* ****for Deletion *********************************/
	
	//gettitng Total Orders of a Customer..
	public Integer getTotalOrdersofaCustomer(int customerId)throws Exception{
		Integer size=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				.add(Restrictions.eq("taxId.customerId", customerId)).list().size();
		return size;
	}
	
	
	// retreiving all customer orders so that they can be updasted with new customerid and updated..
	@SuppressWarnings("unchecked")
	public ArrayList<Orders> getOrdersofaCustomer(int customerId)throws Exception{
		log.info("inside getOrdersofaCustomer()");
		ArrayList<Orders> orders=(ArrayList<Orders>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Orders.class)
				.add(Restrictions.eq("taxId.customerId", customerId)).list();
		
		if(!orders.isEmpty()){
			return orders;
		}
		else{
			throw new OrderNotFoundException();
		}
	}
	
	
	// updating the Orders..
	public Integer updateOrderswithChangedCustomer(ArrayList<Orders> orders) throws Exception{
		log.info("insideupdateOrderswithChangedCustomer( )");
		Assert.notNull(orders);
		hibernateTemplate.saveOrUpdateAll(orders);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		return orders.get(0).getOrderId();				
	}
	
	
	//Deleting the Customer.
	public Integer deleteCustomer(Customer customer)throws Exception{
		log.info("inside deleteCustomer ()");
		Assert.notNull(customer);
		hibernateTemplate.delete(customer);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		System.out.println("Customeer"+customer.getCustomerId());
		return customer.getCustomerId(); 
	}
	
	
	// Deleting the Orders...
	public void deleteAllOrdersOfaCustomer(ArrayList<Orders> orders)throws Exception{
		log.info("inside deleteAllOrdersOfaCustomer()");
		Assert.notNull(orders);
		hibernateTemplate.deleteAll(orders);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();		
	}	
	
/* ****for Deletion *********************************/	
	
	
}
