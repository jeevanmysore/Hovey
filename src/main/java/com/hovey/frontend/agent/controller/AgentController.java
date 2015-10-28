package com.hovey.frontend.agent.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hovey.backend.agent.exception.AgentNotFoundException;
import com.hovey.frontend.agent.service.AgentService;
import com.hovey.frontend.user.dto.HoveyUserDto;

@Controller
public class AgentController {
	private static Logger log=Logger.getLogger(AgentController.class);
	
	@Resource(name="agentService")
	private AgentService agentService;
	
	
	private int pageSize=20;
	
	
	//Method to Display all the agents in Admin Pages.
	/*
	 * Modified by Jeevan on October 17,2013 to add Pagination and Agent Selection Filter
	 */
	
	@RequestMapping(value="/admin/agents.do",method=RequestMethod.GET)
	public String getAgents(Map<String, Object>map,@RequestParam(value="filter",defaultValue="all",required=false)String filter,
			@RequestParam(value="page",required=false,defaultValue="0")Integer pageNo){
		log.info("inside getAgents()");
		try{			
			ArrayList<Integer> agentCounts=this.agentService.getTotalPagesAndAgents(filter, 20);
			Integer totalAgents=agentCounts.get(0);
			Integer pagesNeeded=agentCounts.get(1);
			int firstAgent=pageSize*pageNo+1;
			int lastAgent=this.getAgentsOfLastPageFromRangeandTotalAgents(20, totalAgents, firstAgent);
			ArrayList<HoveyUserDto> agents=this.agentService.getAgentsFromDao(filter,pageNo,20);
			map.put("agents", agents);
			map.put("filter",filter);
			map.put("page",pageNo);
			map.put("end", pagesNeeded);
			map.put("first", firstAgent);
			map.put("total", totalAgents);
			map.put("last", lastAgent);			
			return "admin/agents";
		}
		catch(AgentNotFoundException e){
			log.error("Agents Not Found "+e.toString());
			String status="No Agents Found";
			map.put("status", status);
			return "admin/agents";
		}
		catch(Exception e){
			e.printStackTrace();
			log.error("Error in getting Agent Details "+e.toString());
			String message="Error in getting Agent Details";
			map.put("message", message);
			
			return "error";
		}		
	}
	
	
	private int getAgentsOfLastPageFromRangeandTotalAgents(int range,int totalAgents,int firstAgent){
		log.info("inside getOrdersOfLastPageFromRangeandTotalOrders()	");
		int lastAgent=firstAgent+range-1;
		if(lastAgent>totalAgents){
			lastAgent=(totalAgents%range)+firstAgent-1;
		}
		return lastAgent;
	}
	
	
	
	
	public  String displayAgentforEdit(@RequestParam("no") String agentNumber,Map<String, Object> map){
		log.info("inside displayAgentForEdit()");
		try{
			HoveyUserDto agentDto=this.agentService.getUserByAgentNumber(agentNumber);
			map.put("agent", agentDto);
			return "viewAgent";
		}
		catch(Exception e){
			log.error("Error in gettig Agent"+e.toString());
			String message="Error in Displaying Agent";
			map.put("message",message);
			return "error";
		}
		
	}
	
	
	
	//Displays all the Disabled Agents...
	@RequestMapping(value="/admin/getdisabledagents.do",method=RequestMethod.GET)
	public String displayEnableAgents(Map<String, Object>map,HttpServletRequest request,@RequestParam(value="page",required=false) Integer pageNumber){
		log.info("inside displayEnableAgent()");
		int pagesNeeded;
		
		if(pageNumber==null){
			pageNumber=0;
		}
		try{
			pagesNeeded=this.agentService.findTotalNoOfPages(pageSize);
			ArrayList<HoveyUserDto> agentDtos=this.agentService.getDisabledAgentsFromDB(pageNumber);
			if(!agentDtos.isEmpty()){			
				map.put("page",pageNumber);
				map.put("end", pagesNeeded);
				map.put("agents", agentDtos);
				return "admin/enableAgents";
			}
			else{
				return "admin/enableAgents";
			}
						
			
		}
		catch(AgentNotFoundException e){
			log.error("No Agent Found to Enable "+e.toString());
			return "admin/enableAgents";
		}
		catch(Exception e){
			log.error("No Users found to Enable"+e.toString());
			String message="Error in Displaying Enable Users Page";
			e.printStackTrace();
			map.put("message", message);
			return "error";
		}
	}
	
	
	@RequestMapping(value="/admin/enableagent.do",method=RequestMethod.GET)
	public String enableUser(@RequestParam("agentId") String agentNumber,Map<String, Object>map){
		log.info("inside enableAgents()");
		try{		
			this.agentService.EnableAgent(agentNumber);
			return "redirect:/admin/getdisabledagents.do";
			
		}
		catch(Exception e){
			log.error("Failed to Enable Agent"+e.toString());
			String message="Error While Enabling Agent ";
			map.put("message", message);
			return "error";
		}
	}
	
	
	
	//for Enabling or Disabling the Agent..
	@RequestMapping(value="/admin/enableordisableagent.do",method=RequestMethod.GET)
	public String enableOrDisableAgent(@RequestParam("agent") String agentNumber,@RequestParam("status") String status,Map<String, Object>map){
		log.info("inside enableOrDisableAgent()");
		try{
			boolean enableStatus=false;
			if(status.equalsIgnoreCase("enable")){
				enableStatus=true;
			}
			String result=this.agentService.enableorDisableAgentinDao(agentNumber, enableStatus);
			if(null!=result){
				return "redirect:/admin/agents.do";
				
			}
			else{
				throw new AgentNotFoundException();
			}
			
		}
		catch(Exception e){
			String message="Error while Enabling/Disabling Agent";
			map.put("message", message);
			return "error";
		}		
	}
	
	
	//displayin the page
	@RequestMapping(value="/admin/resetagentpassword.do",method=RequestMethod.GET)
	public String dispalyResetPasswordPage(@RequestParam("agent") String agentNumber,Map<String, Object>map){
		try{
			map.put("agent", agentNumber);
			return "admin/resetPassword";
		}
		catch(Exception e){
			log.error("Error in displayingh Reset Password PAge :");
			String message="Error in Displaying Reset Password Page";
			map.put("message", message);
			return "error";
		}
		
	}
	
	
	//for Reset Password
	@RequestMapping(value="/admin/resetagentpassword.do",method=RequestMethod.POST)
	public String resetPassword(@RequestParam("agent") String agentNumber,@RequestParam("password") String password,Map<String, Object>map){
		log.info("inside enableOrDisableAgent()");
		try{
			
			String result=this.agentService.resetPasswordinDao(agentNumber, password);
			if(null!=result){
				String message="Password Changed Successfully";
				map.put("message", message);
				return "admin/resetPassword";
				
			}
			else{
				throw new AgentNotFoundException();
			}
			
		}
		catch(Exception e){
			String message="Error whileResetting Password of Agent";
			map.put("message", message);
			return "error";
		}		
	}
	
 /* ************************ Editing Agents ************************************  */
	
	
	/*
	 * Added By Jeevan on July 22,2013..
	 * 
	 * Displays page for Editing Agetns..
	 * intiates agent Edit Process.
	 * 
	 */
	@RequestMapping(value="/admin/editagent.do",method=RequestMethod.GET)
	 public String initiateAgentEdit(@ModelAttribute("agent") HoveyUserDto agent, Map<String, Object> map,
			 @RequestParam("agentId")String agentId){
		 try{
			 log.info("inside intiateAgentEdit()");
			 HoveyUserDto agentDto=this.agentService.getUserByAgentNumber(agentId);
			 map.put("agent", agentDto);
			 return "admin/editAgent";
			 
		 }
		 catch(Exception e){
			 String message="Error while intiating Editing Agent, Please try again later";
			 
			 log.error(message+" "+e.toString());
			 map.put("message", message);
			 return "error";			 
		 }		
	 }
	
	
	/*
	 * Added by Jeevan on July 22,2013.
	 * Method to update Agents Info..
	 * 
	 */
	@RequestMapping(value="/admin/editagent.do",method=RequestMethod.POST)
	public String editAgent(@ModelAttribute("agent") HoveyUserDto agent,Map<String, Object>map){
		log.info("inside editAgent()");
		try{
			String updateResult=this.agentService.updateAgent(agent);
			if(null!=updateResult){
				String message="Agent "+agent.getAgentNumber() +" Update Successfully";
				map.put("message", message);
				return "redirect:/admin/agents.do";
			}
			else{
				throw new Exception();
			}			
		}
		catch(Exception e){
			String message="Error while updating Agent, Please try again later";
			map.put("message", message);			
			return "error";
		}	
	}
	
  //Validations...
	/*
	 * Checking if email already exists....
	 */
	@RequestMapping(value="/agentemailexiststoedit.do",method=RequestMethod.GET)
	@ResponseBody
	public String checkExistanceOfEmail(@RequestParam("username")String username,@RequestParam("email")String email){
		log.info("inside checkExistanceOfEmail()");
		try{
			HoveyUserDto agent=this.agentService.getUserByEmail(email);
			if(username.equals(agent.getUsername())){
				return "success";
			}
			else{
				throw new Exception();
			}			
		}
		catch(IndexOutOfBoundsException e){
			return "success";
		}
		catch(Exception e){
			return "fail";
		}		
	}
	
	/*
	 * Checking if email already exists....
	 */
	@RequestMapping(value="/agentIdexiststoedit.do",method=RequestMethod.GET)
	@ResponseBody
	public String checkExistanceOfAgentId(@RequestParam("username")String username,@RequestParam("agentId")String agentNumber){
		log.info("inside checkExistanceOfEmail()");
		try{
			HoveyUserDto agent=this.agentService.getUserByAgentNumber(agentNumber);
			if(username.equals(agent.getUsername())){
				return "success";
			}
			else{
				throw new Exception();
			}			
		}
		catch(IndexOutOfBoundsException e){
			return "success";
		}
		catch(Exception e){
			return "fail";
		}		
	}
	
	
	
	
	
	
	/* ************************ End of Editing Agents ************************************  */
	
	
	@RequestMapping(value="/admin/changeusername.do",method=RequestMethod.GET)
	public String initChangeUsername(@RequestParam("agentId") String agentNumber,Map<String, Object>map){
		log.info("inside initChangeUsername()");
		try{
			 HoveyUserDto agentDto=this.agentService.getUserByAgentNumber(agentNumber);
			 map.put("agent", agentDto);
			 return "admin/editUsername";			 
		}
		catch(Exception e){
				String message="Error while intiating Change Agent Username, Please try again later";			 
			 log.error(message+" "+e.toString());
			 map.put("message", message);
			 return "error";			 
		}
	}
	
	
	@RequestMapping(value="/admin/changeusername.do",method=RequestMethod.POST)
	public String changeUsername(@RequestParam("agentId")String agentNumber,@RequestParam("username")String username,Map<String, Object>map){
		log.info("inside changeUsername()");
		try{
			String updateResult=this.agentService.changeUsername(agentNumber, username);
			if(null!=updateResult){
				String message="Username Changed Successfully";
				map.put("message", message);
				return "redirect:/admin/agents.do";
			}
			else{
				throw new Exception();
			}	
		}
		catch(Exception e){
			String message="Error while Changing Agent Username, Please try again later";
			map.put("message", message);	
			e.printStackTrace();
			return "error";
		}			
	}
	
	
	/*
	 * Added by Jeevan on December 18, 2013
	 * Method to Appply Default Welcome Message to All the Agents.
	 * 
	 * Added as per Clients ?Requirement.
	 */
	@RequestMapping(value="/admin/welcomemessage.do",method=RequestMethod.GET)
	public String applyDefaultWelcomeMessagetoAgents(@RequestParam("welcomeMessage") String welcomeMessage, RedirectAttributes redAttribs,Map<String, Object> map){
		log.info("inside applyDefaultWelcomeMessagetoAgents() ");
		try{
			Integer updateResult=this.agentService.updateAgentsWithDefaultWelcomeMessage(welcomeMessage);
			if(updateResult>0){
				redAttribs.addFlashAttribute("messageStatus", "Welcome Message Successfully Updated to all Agents");
				return "redirect:/admin/agents.do";
			}
			else{
				throw new Exception();
			}
		}
		catch(AgentNotFoundException e){
			log.error(e.toString());
			redAttribs.addFlashAttribute("messageStatus", "No Agents Found");
			return "redirect:/admin/agents.do";
		}
		catch(Exception e){
			map.put("message","Error While Applying Default Welcome Message to Agents");
			return "error";
		}
	}
	
	

}
