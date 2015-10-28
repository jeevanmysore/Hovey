package com.hovey.frontend.agent.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hovey.backend.admin.dao.AdminDao;
import com.hovey.backend.agent.dao.AgentDao;
import com.hovey.backend.agent.dao.DealSheetDao;
import com.hovey.backend.agent.model.AgentCommissions;
import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.supplier.dao.SupplierDao;
import com.hovey.backend.supplier.model.SupplierReports;
import com.hovey.backend.user.dao.UserDao;
import com.hovey.backend.user.model.HoveyUser;
import com.hovey.frontend.user.dto.HoveyUserDto;

@Service("agentService")
public class AgentServiceImpl implements AgentService{

	private static Logger log=Logger.getLogger(AgentServiceImpl.class);
	
	@Resource(name="agentDao")
	private AgentDao agentDao;
	
	@Resource(name="userDao")
	private UserDao userDao;
	
	@Resource(name="dealSheetDao")
	private DealSheetDao dealSheetDao;
	
	@Resource(name="supplierDao")
	private SupplierDao supplierDao;
	
	@Resource(name="adminDao")
	private AdminDao adminDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	public ArrayList<HoveyUserDto> getAgentsFromDao(String filter,Integer pageNo,Integer pageSize)throws Exception{
		log.info("inside getAgentsFromDao()");
		ArrayList<HoveyUser> agents=this.agentDao.getAgents(filter, pageNo, pageSize);
		ArrayList<HoveyUserDto> agentDtos=new ArrayList<HoveyUserDto>();
		for(HoveyUser agent:agents){
			HoveyUserDto agentDto=HoveyUserDto.populateHoveyUserDto(agent);
			agentDtos.add(agentDto);
		}
		return agentDtos;
	}
	
	/*
	 * Created By Jeevan on October 17, 2013.
	 * Method to get Total Pages Needed and Total Agents for a givewn Orders..
	 */
	public ArrayList<Integer> getTotalPagesAndAgents(String filter,Integer pageSize)throws Exception{
		log.info("inside getTotalPagesAndAgents()");		
		ArrayList<Integer> agentCounts=new ArrayList<Integer>();
		Integer totalAgents=this.agentDao.getNoOfAgentsHavingCriteria(filter);
		agentCounts.add(totalAgents);
		int result=totalAgents/pageSize;
		int pagesNeeded;
		if(totalAgents%pageSize>0){
			pagesNeeded=result+1;
		}
		else{
			pagesNeeded=result;
		}
		agentCounts.add(pagesNeeded);		
		return agentCounts;
	}
	
	
	
	
	
	//added by Jeevan
	public HoveyUserDto getUserByAgentNumber(String agentNumber)throws Exception{
		log.info("inside getUserByAgentNumber()");
		HoveyUser user=this.agentDao.getUserByAgentNumber(agentNumber);
		HoveyUserDto userDto=HoveyUserDto.populateHoveyUserDto(user);
		return userDto;
	}
	
	/*
	 * Added by Jeevan on July 22,2013
	 */
	public HoveyUserDto getUserByEmail(String email)throws Exception{
		log.info("inside getUserByEmail()");
		HoveyUser user=this.agentDao.getUserByEmail(email);
		HoveyUserDto userDto=HoveyUserDto.populateHoveyUserDto(user);
		return userDto;
	}
	
	
	
	//gets all the disabled USers..
	public ArrayList<HoveyUserDto> getDisabledAgentsFromDB()throws Exception{
		log.info("inside getDisabledAgentsFromDB()");
		ArrayList<HoveyUser> agents=this.agentDao.getDisabledAgents();
		ArrayList<HoveyUserDto>agentDtos=new ArrayList<HoveyUserDto>();
		if(!agents.isEmpty()){
			for(HoveyUser agent:agents){
				HoveyUserDto agentDto=HoveyUserDto.populateHoveyUserDto(agent);
				agentDtos.add(agentDto);
			}
		}
		return agentDtos;
	}
	
	//pagination
	//gets all the disabled USers..
	public ArrayList<HoveyUserDto> getDisabledAgentsFromDB(int pageNo)throws Exception{
		log.info("inside getDisabledAgentsFromDB()");
		ArrayList<HoveyUser> agents=this.agentDao.getDisabledAgents(pageNo);
		ArrayList<HoveyUserDto>agentDtos=new ArrayList<HoveyUserDto>();
		if(!agents.isEmpty()){
			for(HoveyUser agent:agents){
				HoveyUserDto agentDto=HoveyUserDto.populateHoveyUserDto(agent);
				agentDtos.add(agentDto);
			}
		}
		return agentDtos;
	}
	
	
	//getting total no of records..
	public int findTotalNoOfPages(int pageSize)throws Exception{
		log.info("inside findTotalNoOfPages()");
		int totalRecords=this.agentDao.getTotalDisabledUsers();
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
	
	
	
	//Enabling Agent..
	public void EnableAgent(String agentNumber)throws Exception{
		log.info("inside EnableAgent()");
		HoveyUser agent=this.agentDao.getUserByAgentNumber(agentNumber);
		agent.setEnabled(true);
		this.userDao.updateUser(agent);
	}
	
	
	
	//for Enabling or Disaqbling the Agent.
	public String enableorDisableAgentinDao(String agentNumber,boolean status)throws Exception{
		log.info("inside enableorDisableAgentinDao() ");
		HoveyUser agent=this.agentDao.getUserByAgentNumber(agentNumber);
		agent.setEnabled(status);
		String result=this.agentDao.updateAgent(agent);
		return result;
	}
	
	
	//for Password Reset...........
	
	public String resetPasswordinDao(String agentNumber,String password)throws Exception{
		log.info("inside resetPasswordinDao()");
		HoveyUser agent=this.agentDao.getUserByAgentNumber(agentNumber);
		org.springframework.util.Assert.notNull(password);
		String hassPwd=passwordEncoder.encodePassword(password, null);
		agent.setPassword(hassPwd);
		String result=this.agentDao.updateAgent(agent);
		return result;		
	}
	
	
	/*
	 * Added by Jeevan on Aug 14, 2013 to give admin resetting the pwd
	 */
	public String resetPasswordOfAdmin(String username,String password)throws Exception{
		log.info("inside resetPasswordOfAdmin()");
		HoveyUser admin=this.agentDao.getUserByUsername(username);
		String hassPwd=passwordEncoder.encodePassword(password, null);
		admin.setPassword(hassPwd);
		String result=this.agentDao.updateAgent(admin);
		return result;		
	}
	
	/*
	 * Added on July 22,2013 by Jeevan..
	 * method to Update Agents
	 *
	 */	
	@Transactional
	public String updateAgent(HoveyUserDto agentDto)throws Exception{
		log.info("inside updateAgent()");
		HoveyUser agent=this.agentDao.getUserByUsername(agentDto.getUsername());
		
		agent.setCity(agentDto.getCity());
		agent.setEmail(agentDto.getEmail());
		agent.setFirstName(agentDto.getFirstName());
		agent.setLastName(agentDto.getLastName());
		agent.setState(agentDto.getState());
		agent.setZipcode(agentDto.getZipcode());
		//added on October 28,2013
		agent.setWelcomeMessage(agentDto.getWelcomeMessage());
		if(agent.getAgentNumber().equals(agentDto.getAgentNumber())){		
			agent.setAgentNumber(agentDto.getAgentNumber());
			String result=this.agentDao.updateAgent(agent);
			return result;
		}
		else{
			/*Two step Process
			 * 1. Create Temp Agent
			 * 2. Update all orders and Supplier Reports to Temp Agent
			 * 3. Savee Agent new New Nuo
			 * 4. Transform all Orders of Temp Agent to new Agent
			 * 5. Delete Temp Agent
			 */
			ArrayList<Orders> orders=null;
			ArrayList<SupplierReports> reports=null;
			ArrayList<AgentCommissions> commissions=null;
			HoveyUser tempAgent=new HoveyUser();
			tempAgent.setUsername("tempAgent");
			tempAgent.setAgentNumber("9876");
			this.userDao.registerUser(tempAgent);
			
			try{
				orders=this.dealSheetDao.getOrdersOfAgent(agent.getAgentNumber());
				for(Orders order : orders){
					order.setAgent(tempAgent);
				}
				this.dealSheetDao.saveDealSheetOrdersToDB(orders);				
			}
			catch(Exception e){
				log.info("NO Orders Existsa for Agent");
			}
			
		    
		   try{
			   reports=this.supplierDao.getSupplierReportsOfAgent(agent.getAgentNumber());
			   for(SupplierReports report:reports){
				   report.setAgentId(tempAgent);			   
			   }
			   this.supplierDao.saveorUpdateSupplierReports(reports);
			  
		   }
		   catch(Exception e){
			  log.info("NO Supplier Reports Exists for Agent");
		   }
		   
		   
		   try{
			   commissions=this.adminDao.getAgentCommissionsByAgentNumber(agent.getAgentNumber());
			   for(AgentCommissions commission:commissions){
				   commission.setAgent(tempAgent);
			   }
			   this.adminDao.saveOrUpdateAgentCommissions(commissions);
		   }
		   catch(Exception e){
			   log.info("NO Agent Commissions Exists");
		   }
		   
		   
		   
		   
		   
		   agent.setAgentNumber(agentDto.getAgentNumber());		   
		   String result=this.agentDao.updateAgent(agent);
		  
		   
		   if(null!=orders){
			   for(Orders order : orders){
					order.setAgent(agent);
				}
				this.dealSheetDao.saveDealSheetOrdersToDB(orders);				
		   }
		   
		   if(null!=reports){
			   for(SupplierReports report:reports){
				   report.setAgentId(agent);			   
			   }
			   this.supplierDao.saveorUpdateSupplierReports(reports);			  
		   }
		   
		   if(null!=commissions){
			   for(AgentCommissions commission:commissions){
				   commission.setAgent(agent);
			   }
			   this.adminDao.saveOrUpdateAgentCommissions(commissions);
		   }
		   
		  
		   this.agentDao.deleteAgent(tempAgent);
		   
			return result;		
		}
			
	}
	
	/*
	 * Method to change Username
	 */
	
	@Transactional
	public String changeUsername(String agentNumber,String username)throws Exception{
		log.info("inside changeUsername()");
		HoveyUser agent=this.agentDao.getUserByAgentNumber(agentNumber);
		agent.setUsername(username);
		String result=this.agentDao.updateAgent(agent);
		return result;
	}
	
	
	
	/*
	 * Created by Jeevan on December 18, 2013
	 * A Method to Apply Default Welcome Message to all the Agents
	 * Steps:
	 * 
	 * 1. Get all the Agents.
	 * 2. Update Welcome Message
	 * 3. Update Agents
	 */
	public Integer updateAgentsWithDefaultWelcomeMessage(String welcomeMessage) throws Exception{
		log.info("inside updateAgentsWithDefaultWelcomeMessage() ");
		Integer updateResult=1;
		ArrayList<HoveyUser> agents=this.agentDao.getAgents(null, null, null);
		for(HoveyUser agent:agents){
			agent.setWelcomeMessage(welcomeMessage);
			Integer agentUpdateStatus=Integer.parseInt(this.agentDao.updateAgent(agent));
			if(agentUpdateStatus<=0){
				updateResult=0;
			}
		}
		return updateResult;
	}
}
