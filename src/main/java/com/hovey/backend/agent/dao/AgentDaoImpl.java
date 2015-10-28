package com.hovey.backend.agent.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hovey.backend.agent.exception.AgentNotFoundException;
import com.hovey.backend.user.model.HoveyUser;
@Repository("agentDao")
@Transactional
public class AgentDaoImpl implements AgentDao {

	private static Logger log=Logger.getLogger(AgentDaoImpl.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	
	private int pageSize=10;
	
	
	//retreives all the Agents..
	/*
	 *Modified by Jeevan on October 17,2013... to add Selected Fileter for Different Agents
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<HoveyUser> getAgents(String filter,Integer pageNo,Integer pageSize)throws Exception{
		log.info("inside getAgents()");	
		Criteria criteria= hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class).add(Restrictions.eq("userType","u")).addOrder(Order.asc("firstName"));
		if(null!=filter && filter!=""){
			if(filter.equalsIgnoreCase("enabled")){
				criteria.add(Restrictions.eq("enabled", true));
			}
			else if(filter.equalsIgnoreCase("disabled")){
					criteria.add(Restrictions.eq("enabled", false));
			}
		}	
		if(null!=pageNo && null!=pageSize){
			criteria.setFirstResult(pageNo*pageSize);
			criteria.setMaxResults(pageSize);	
		}
		ArrayList<HoveyUser> agents=(ArrayList<HoveyUser>) criteria.list();
		if(agents.isEmpty()){
			throw new AgentNotFoundException();
		}
		else{
			return agents;
		}
	}
	
	
	/*
	 * Created by Jeevan on October 17, 2013 to get No Of Agents Satisfying the Criteria..
	 * 
	 */
	public Integer getNoOfAgentsHavingCriteria(String filter) throws AgentNotFoundException{
		log.info("inside getNoOfAgentsHavingCriteria()");
		Criteria criteria= hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class).add(Restrictions.eq("userType","u"));
		
		if(null!=filter && filter!=""){
			if(filter.equalsIgnoreCase("enabled")){
				criteria.add(Restrictions.eq("enabled", true));
			}
			else if(filter.equalsIgnoreCase("disabled")){
					criteria.add(Restrictions.eq("enabled", false));
			}
		}	
		criteria.setProjection(Projections.count("username"));		
		Integer totalAgents=(Integer) criteria.list().get(0);
		return totalAgents;	
	}
	
	
	
		//geting agent by Agent Number..
		public HoveyUser getUserByAgentNumber(String agentNumber)throws Exception{
			log.info("inside getUserByAgentNumber()");
		
			HoveyUser user=(HoveyUser) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class)
					.add(Restrictions.eq("agentNumber", agentNumber)).list().get(0);
			return user;
		}
		
		//geting agent by Username.
		/*
		 * Added by Jeevan on July 22, 2013.
		 */
				public HoveyUser getUserByUsername(String username)throws Exception{
					log.info("inside getUserByAgentNumber()");
					HoveyUser user=(HoveyUser) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class)
							.add(Restrictions.eq("username", username)).list().get(0);
					return user;
				}
				
				
				public HoveyUser getUserByEmail(String email)throws Exception{
					log.info("inside getUserByEmail()");
					HoveyUser user=(HoveyUser) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class)
							.add(Restrictions.eq("email", email)).list().get(0);
					return user;
				}
		
		
		//Fetches all the Users who are not Enabled..
		@SuppressWarnings("unchecked")
		public ArrayList<HoveyUser> getDisabledAgents()throws Exception{
			ArrayList<HoveyUser> agents=(ArrayList<HoveyUser>) this.hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class)
					.add(Restrictions.eq("enabled", false)).list();
			
			if(agents.isEmpty()){
				throw new AgentNotFoundException();
			}
			else{
				return agents;
			}
		}
		
		
		//Fetches paginated Users who are not Enabled..
		@SuppressWarnings("unchecked")
		public ArrayList<HoveyUser> getDisabledAgents(int pageNo)throws Exception{
			ArrayList<HoveyUser> agents=(ArrayList<HoveyUser>) this.hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class)
					.add(Restrictions.eq("enabled", false)).setFirstResult(pageNo*pageSize).setMaxResults(pageSize).list();
			
			if(agents.isEmpty()){
				throw new AgentNotFoundException();
			}
			else{
				return agents;
			}
		}
		
		
		//for Getting Total No of Records...
		@SuppressWarnings("unchecked")
		public int getTotalDisabledUsers()throws Exception{
			ArrayList<HoveyUser> agents=(ArrayList<HoveyUser>) this.hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class)
					.add(Restrictions.eq("enabled", false)).list();
			return agents.size();
		}
		
		
		
		
		public String updateAgent(HoveyUser agent)throws Exception{
			log.info("inside updateAgent()");
			hibernateTemplate.update(agent);
			hibernateTemplate.getSessionFactory().getCurrentSession().flush();
			return agent.getAgentNumber();
		}
		
		//geting agent by Agent Name.
				@SuppressWarnings("unchecked")
				public ArrayList<HoveyUser> getUserByAgentName(String firstName,String lastName)throws Exception{
					log.info("inside getUserByAgentName()");
					ArrayList<HoveyUser> agents=(ArrayList<HoveyUser>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class)
							.add(Restrictions.and(Restrictions.ilike("firstName",'%'+firstName+'%'), Restrictions.ilike("lastName", '%'+lastName+'%'))).list();
					return agents;
				}
				
				//geting agent by Agent name single
				@SuppressWarnings("unchecked")
				public ArrayList<HoveyUser> getUserByFirstName(String firstName)throws Exception{
					log.info("inside getUserByFirstName()");
					ArrayList<HoveyUser> agents=(ArrayList<HoveyUser>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class)
							.add(Restrictions.or(Restrictions.ilike("firstName",'%'+firstName+'%'), Restrictions.ilike("lastName",'%'+firstName+'%'))).list();
					return agents;
				}		
				
		
				
		//deleting Agents
		public String deleteAgent(HoveyUser agent)throws Exception{
			log.info("inside deleteAgent()");
			hibernateTemplate.delete(agent);
			hibernateTemplate.getSessionFactory().getCurrentSession().flush();
			return agent.getAgentNumber();
		}
				
		
}
