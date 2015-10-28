package com.hovey.backend.agent.dao;

import java.util.ArrayList;

import com.hovey.backend.agent.exception.AgentNotFoundException;
import com.hovey.backend.user.model.HoveyUser;

public interface AgentDao {

	
	//public ArrayList<HoveyUser> getAgents()throws Exception;
	public ArrayList<HoveyUser> getAgents(String filter,Integer pageNo,Integer pageSize)throws Exception;
	public Integer getNoOfAgentsHavingCriteria(String filter) throws AgentNotFoundException;
	public HoveyUser getUserByAgentNumber(String agentNumber)throws Exception;
	public ArrayList<HoveyUser> getDisabledAgents()throws Exception;
	public ArrayList<HoveyUser> getDisabledAgents(int pageNo)throws Exception;
	public int getTotalDisabledUsers()throws Exception;
	public String updateAgent(HoveyUser agent)throws Exception;
	public ArrayList<HoveyUser> getUserByAgentName(String firstName,String lastName)throws Exception;
	public ArrayList<HoveyUser> getUserByFirstName(String firstName)throws Exception;
	public HoveyUser getUserByUsername(String username)throws Exception;
	public HoveyUser getUserByEmail(String email)throws Exception;
	
	public String deleteAgent(HoveyUser agent)throws Exception;
}
