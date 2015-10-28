/* Project      : Hovey Energy 
 * Current File : LoginController.java 
 * Created By   : Nnupur Krishnaa on 22-04-2013
 * 
 * Copyright (c) 2012 KNS Technologies, Bangalore. All rights reserved.
 * 
 * Reason       : This is controller class that invokes method when someone want forgets the password and reset new apssword.
 * 
 */
package com.hovey.frontend.user.controller;

import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

//import com.gracular.frontend.user.dto.UserDto;
import com.hovey.backend.user.model.HoveyUser;
import com.hovey.frontend.user.service.UserService;

@Controller
public class PasswordController {

	private static Logger log=Logger.getLogger(RegistrationController.class);
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="mailSender")
	private MailSender mailSender;
	
	@RequestMapping(value="/forGotPassword.do",method=RequestMethod.GET)
	public String showRegistraion(){
		System.out.println("in controller........");
		return "forGotPasswod";
	}
	
	//activating java mail to send email to the user..
	
	@RequestMapping(value="/forGotPassword.do",method=RequestMethod.POST)
	public ModelAndView sendMail(@RequestParam("email") String email){
		
		Random randomGenerator = new Random();
		String constant="234678588"; 
		String ukey=(randomGenerator.nextInt(100))+constant;
		System.out.println(ukey);
		try{
		
		HoveyUser user=userService.getUser(email);
		
		
		userService.insertUkeyforPassword(ukey,user);
		}catch(Exception e){
			log.error("Email is empty"+e.toString());
			return new ModelAndView("redirect:/forGotPassword.do?status=fail");
		}
		
		System.out.println("inside controller forgot password()");
		String from="knstech01@gmail.com";
		String subject="Password Recovery";
		
		String Body="Click on the link to reset your password"+"\n"+
				"http://localhost:8080/hoveyenergy/forget.do?ukey="+ukey;
		try{
			sendMail(from, email, subject, Body);
			return new ModelAndView("redirect:/");			
			
		}
		
		catch(Exception e){
			log.error("Error in sending mail "+e.toString());
			e.printStackTrace();
			return new ModelAndView("redirect:/forGotPassword.do?status=fail");
		}
}
	
	@RequestMapping(value="/forget.do",method=RequestMethod.GET)
	public String showPassword(HttpServletRequest req){
		System.out.println("in controller........");
		
		
		String[] ukey1=req.getQueryString().split("=");
		
		//System.out.println(ukey1[1]);
		String ukey=ukey1[1];
		HttpSession session=req.getSession();
		session.setAttribute("ukey", ukey);
		return "ResetPassword";
	}
	
	
	//Reset ne2w password..
	
	@RequestMapping(value="/forget.do",method=RequestMethod.POST)
	public ModelAndView updatePassword(@RequestParam("password") String password,HttpServletRequest request){
		
		HttpSession session=request.getSession();
		String ukey=(String) session.getAttribute("ukey");
		
		HoveyUser user=userService.getUserByUkey(ukey);
		userService.updatePassword(password,user);
		return new ModelAndView("successfulRegistration");
		
		
	}
	
	
	public void sendMail(String from, String to, String subject, String msg) {
		 
		SimpleMailMessage message = new SimpleMailMessage();
		System.out.println("inside mail sendmail");
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);	
	}
	
	
	
	
}