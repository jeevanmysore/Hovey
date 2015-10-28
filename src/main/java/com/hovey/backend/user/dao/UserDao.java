/* Project      : Hovey Energy 
 * Current File : UserDao.java 
 * Created By   : Nnupur Krishnaa on 18-04-2013
 * 
 * Copyright (c) 2012 KNS Technologies, Bangalore. All rights reserved.
 * 
 * Reason       : This is interface class that contains declaration of methods to be implemented. 
 * 
 */
package com.hovey.backend.user.dao;


import java.util.ArrayList;

import com.hovey.backend.user.model.HoveyUser;

public interface UserDao {

	
	public void registerUser(HoveyUser user)throws Exception;
	public HoveyUser getUserInfoFromUsername(String username)throws Exception;
	public void insertUkey(HoveyUser user);
	public HoveyUser getUserByEmail(String email);
	public HoveyUser getUserByUkey(String ukey);
	public void updatePassword(HoveyUser user);
	public HoveyUser validateEmail(String email);
	public HoveyUser checkEmail(String email);
	public ArrayList<HoveyUser> getDisabledUsers();
	public void updateUser(HoveyUser user);
	public HoveyUser getUserByAgentNumber(String agentNumber)throws Exception;
}