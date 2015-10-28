package com.hovey.frontend.agent.service;

import java.util.ArrayList;

import com.hovey.frontend.user.dto.HoveyUserDto;

public interface AgentService {
	//public ArrayList<HoveyUserDto> getAgentsFromDao()throws Exception;
	public ArrayList<HoveyUserDto> getAgentsFromDao(String filter,Integer pageNo,Integer pageSize)throws Exception;
	public ArrayList<Integer> getTotalPagesAndAgents(String filter,Integer pageSize)throws Exception;
	public HoveyUserDto getUserByAgentNumber(String agentNumber)throws Exception;
	public ArrayList<HoveyUserDto> getDisabledAgentsFromDB()throws Exception;
	public void EnableAgent(String agentNumber)throws Exception;
	public ArrayList<HoveyUserDto> getDisabledAgentsFromDB(int pageNo)throws Exception;
	public int findTotalNoOfPages(int pageSize)throws Exception;
	public String enableorDisableAgentinDao(String agentNumber,boolean status)throws Exception;
	public String resetPasswordinDao(String agentNumber,String password)throws Exception;
	public String resetPasswordOfAdmin(String username,String password)throws Exception;
	public String updateAgent(HoveyUserDto agentDto)throws Exception;
	public HoveyUserDto getUserByEmail(String email)throws Exception;
	public String changeUsername(String agentNumber,String username)throws Exception;
	public Integer updateAgentsWithDefaultWelcomeMessage(String welcomeMessage) throws Exception;
}
