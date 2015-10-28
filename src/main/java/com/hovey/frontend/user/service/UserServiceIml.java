/* Project      : Hovey Energy 
 * Current File : UserServiceIml.java 
 * Created By   : Nnupur Krishnaa on 18-04-2013
 * 
 * Copyright (c) 2012 KNS Technologies, Bangalore. All rights reserved.
 * 
 * Reason       : This is class that containsdefinition of methods declared.. 
 * 
 */
package com.hovey.frontend.user.service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import com.hovey.backend.user.dao.UserDao;
import com.hovey.backend.user.exception.AccountExpiredException;
import com.hovey.backend.user.exception.HoveyUserNotFoundException;
import com.hovey.backend.user.exception.MailNotSendException;
import com.hovey.backend.user.model.HoveyUser;
import com.hovey.backend.user.model.Mail;
import com.hovey.frontend.common.utility.EmailSender;
import com.hovey.frontend.user.dto.HoveyUserDto;


@Transactional
@Service("userService")
public class UserServiceIml implements UserService {

	@Resource(name="userDao")
	private UserDao userDao;
	
	@Resource(name="emailSender")
	private EmailSender emailSender;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	private static Logger log=Logger.getLogger(UserServiceIml.class);
	
	// User Registration
		public void registerUser(String username,String password,String firstName,String lastName,String city,String state,String zipcode,String email,String agentNumber)throws Exception{
			log.info("inside service.registerUser()");
			
			String hassedPass=passwordEncoder.encodePassword(password, null);			
			HoveyUser user=new HoveyUser();
			if(null!=email && email!=""){
				user.setEmail(email);
			}
			
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setPassword(hassedPass);
			user.setUsername(username);
			user.setEnabled(true);
			user.setAgentNumber(agentNumber);
			user.setCity(city);
			user.setState(state);
			//user.setPhnNumber(phnNumber);
			user.setZipcode(zipcode);
			user.setUserType('U');		
			user.setWelcomeMessage("Make it a GREAT Day");
			this.userDao.registerUser(user);
		}
		
		public HoveyUserDto getUserByUsername(String username){
			log.info("inside getUserByUsername();");
		try{
			Assert.hasText(username);
			HoveyUser user=this.userDao.getUserInfoFromUsername(username);
			if(user!=null){
				HoveyUserDto userDto=HoveyUserDto.populateHoveyUserDto(user);
				return userDto;
			}
			else{
				return null;
			}		
			
		}
		catch(Exception e){
			e.printStackTrace();
			log.error("error in user Service "+e.toString());
			return null;
		}
	}

		@Override
		public void sendEmail(String from, String subject, String email,
				String body) {
			
		 System.out.println("inside mail service");
		    	Mail mail = null;
		      mail.sendMail(from, email, subject, body);
		      System.out.println("after sendmail statent");
			
		}
		
		

		@Override
		public void insertUkeyforPassword(String ukey, HoveyUser user) {
		
			user.setUkey(ukey);
			//user.setEmail(email);
			this.userDao.insertUkey(user);
			
		}

		@Override
		public HoveyUser getUser(String email) {
			HoveyUser user=this.userDao.getUserByEmail(email);
			return user;
			
		}

		@Override
		public HoveyUser getUserByUkey(String ukey) {
			HoveyUser user=this.userDao.getUserByUkey(ukey);
			return user;
		}

		@Override
		public void updatePassword(String password, HoveyUser user) {
			user.setPassword(password);
			this.userDao.updatePassword(user);
			
		}

		@Override
		public String getUserByEmail(String email) {
			log.info("inside getUserbyEmail()");
			HoveyUser user=this.userDao.validateEmail(email);
			if(null=="user"){
				return "success";
			}
			else
				return "fail";
		}

		@Override
		public String getUserByMail(String email) {
			log.info("inside getUserbyMail()");
			try{
				
			HoveyUser user=this.userDao.checkEmail(email);
			System.out.println("User valueeeeeee....."+user.getEmail());
			if(user.getEmail()!=null){
				System.out.println("successsssss");
				return "success";
			}
			else{
				System.out.println("failed");
				return "fail";
				
			}
				
			}catch(IndexOutOfBoundsException e){
				log.error("No Email Exists "+e.toString());
				e.printStackTrace();
				return "success";
			}
			
		}

		@Override
		public ArrayList<HoveyUserDto> getDisabledUsers() {
			log.info("inside getDisabledUsers");
			ArrayList<HoveyUser> users=this.userDao.getDisabledUsers();
			if(users.isEmpty()){
				return null;
			}
			else{
				System.out.println("inside getDisabledUsers else condition" );
				ArrayList<HoveyUserDto> userDtos=new ArrayList<HoveyUserDto>();
				for(HoveyUser user:users){
					HoveyUserDto  userDto=HoveyUserDto.populateHoveyUserDto(user);
					userDtos.add(userDto);
				}
				return userDtos;
				
			}
		}

		@Override
		public void EnableUser(String username) {
				HoveyUser user;
				try {
					user = this.userDao.getUserInfoFromUsername(username);
					user.setEnabled(true);
					this.userDao.updateUser(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
		
		//added by Jeevan
		public HoveyUserDto getUserByAgentNumber(String agentNumber)throws Exception{
			log.info("inside getUserByAgentNumber()");
			HoveyUser user=this.userDao.getUserByAgentNumber(agentNumber);
			HoveyUserDto userDto=HoveyUserDto.populateHoveyUserDto(user);
			return userDto;
		}
		
		
		
	/*
	 * Added by Jeevan on 02 Aug,2013 to refactor Forgot PAssword...
	 */
	/*
	 * process:
	 * 1. get User by mail;
	 * 2. Develop token and time stamp,
	 * 2. update user.
	 * 4. send email with token..
	 * 
	 */
		//processing forget Password..
	public boolean processForgotPassword(String email)throws Exception{
		log.info("inside processForgotPassword()");
		HoveyUser user=this.getUser(email);
		boolean a =true;
		if(null!=user){
			String uuid=UUID.randomUUID().toString();
			String token=uuid.toString().replaceAll("-", "").toUpperCase();
			user.setUkey(token);
			//setting time constraint of 24 hrs..
			Calendar cal=Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, 1);
			Date timeStamp=cal.getTime();
			user.setTimeStamp(timeStamp);
			this.userDao.updateUser(user);
			String res=this.sendMail(token, email);
			if(!res.equalsIgnoreCase("success")){
				a=false;
				throw new MailNotSendException();
			}		
		}
		else{
			a=false;
			throw new HoveyUserNotFoundException();
		}
		return a;		
	}
	
	
	//setsusp amd sends mail..
	private String sendMail(String token,String email){
		String to=email;
		String subject="Hovey Energy Password Recovery";
		String body="Hai, Greetings from Hovey Energy.\n " +
				"Please click on the following link to reset your password, the link will expire in 24 hours"+"\n"+
				"http://23.23.245.138:7090/hoveyenergy/reset.do?ukey="+token;
		
		emailSender.sendMail(to, subject, body);
		return "success";
	}
	
	
	/*
	 * init password Reset process..
	 * 
	 * 1.involves.. getting User Based on Ukey
	 * 2. checking for expiry,
	 * 3. if everything fine showing reset page..
	 */
		
	public String initPAsswordReset(String ukey)throws Exception{
		log.info("inside initPAsswordReset()" );
		HoveyUser user=this.userDao.getUserByUkey(ukey);
		if(null!=user){
			Date date=new Date();
			if(date.before(user.getTimeStamp())){
				return user.getUsername();
			}
			else{
				throw new AccountExpiredException();
			}			
		}
		else{
			throw new HoveyUserNotFoundException();
		}
	}
		
	
	/*
	 * performing reset
	 * 
	 * 
	 */
	public String resetPassword(String username,String password)throws Exception{
		log.info("inside resetPassword()");
		HoveyUser user=this.userDao.getUserInfoFromUsername(username);
		if(null!=user){
			String hassPassword=passwordEncoder.encodePassword(password, null);
			user.setPassword(hassPassword);
			user.setUkey(null);
			user.setTimeStamp(null);
			this.userDao.updatePassword(user);
			return "success";
		}
		else{
			throw new HoveyUserNotFoundException();
		}
		
	}
	
		
}

	
		
		
