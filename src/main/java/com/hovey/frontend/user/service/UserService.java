/* Project      : Hovey Energy 
 * Current File : UserService.java 
 * Created By   : Nnupur Krishnaa on 18-04-2013
 * 
 * Copyright (c) 2012 KNS Technologies, Bangalore. All rights reserved.
 * 
 * Reason       : This is interface class that contains declaration of methods to be implemented. 
 * 
 */
package com.hovey.frontend.user.service;

import java.util.ArrayList;

import com.hovey.backend.user.model.HoveyUser;
import com.hovey.frontend.user.dto.HoveyUserDto;



//import com.gracular.frontend.user.dto.UserDto;

public interface UserService {

	//public UserDto getUserByUsername(String username);
public void registerUser(String username,String password,String firstName,String lastName,String city,String state,String zipcode,String email,String agentNumber)throws Exception;
public HoveyUserDto getUserByUsername(String username);
public void sendEmail(String from, String subject, String email, String body);
public void insertUkeyforPassword(String ukey, HoveyUser user);
public HoveyUser getUser(String email);
public HoveyUser getUserByUkey(String ukey);
public void updatePassword(String password, HoveyUser user);
public String getUserByEmail(String email);
public String getUserByMail(String email);
public ArrayList<HoveyUserDto> getDisabledUsers();
public void EnableUser(String username);
public HoveyUserDto getUserByAgentNumber(String agentNumber)throws Exception;
public boolean processForgotPassword(String email)throws Exception;
public String initPAsswordReset(String ukey)throws Exception;
public String resetPassword(String username,String password)throws Exception;

}

