/* Project      : Hovey Energy 
 * Current File : RegistrationController.java 
 * Created By   : Nnupur Krishnaa on 18-04-2013
 * 
 * Copyright (c) 2012 KNS Technologies, Bangalore. All rights reserved.
 * 
 * Reason       : This is controller class that contains methods and method request mapping for further processing. 
 * 
 */
package com.hovey.frontend.user.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

//import com.gracular.frontend.user.dto.UserDto;
import com.hovey.backend.user.exception.AccountExpiredException;
import com.hovey.backend.user.exception.HoveyUserNotFoundException;
import com.hovey.backend.user.exception.MailNotSendException;
import com.hovey.frontend.user.dto.HoveyUserDto;
import com.hovey.frontend.user.service.UserService;

@Controller
public class RegistrationController {

	private static Logger log=Logger.getLogger(RegistrationController.class);
	
	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping(value="/admin/register.do",method=RequestMethod.GET)
	public String showRegistraion(){
		System.out.println("in controller........");
		return "admin/registration";
	}
	
	//Registration..
	
	@RequestMapping(value="/admin/register.do",method=RequestMethod.POST)
	public ModelAndView registerUser(@RequestParam("username") String username,
				@RequestParam("password") String password,
				@RequestParam("firstname") String firstName,
				@RequestParam("lastname") String lastName,
				@RequestParam("city") String city,
				@RequestParam("state") String state,
				@RequestParam("zipcode") String zipcode,
				@RequestParam("email") String email,
				//@RequestParam("phnNumber") int phnNumber,
				@RequestParam("agentNumber") String agentNumber)
				{
			System.out.println("inside controller registerUser()");
		try{
			
			this.userService.registerUser(username,password,firstName,lastName,city,state,zipcode,email,agentNumber);
			String message="Successfully Registered";
			return new ModelAndView("redirect:/admin/register.do","status",message);			
			
		}
		catch(Exception e){
			log.error("Error in Registration "+e.toString());
			e.printStackTrace();
			String error="Failed to Register ";
			return new ModelAndView("redirect:/admin/register.do","error",error);
		}
		
	}
	
	//Check if Email Already exists or not....
	
	@RequestMapping(value="/validatemail.do",method=RequestMethod.POST)
	public @ResponseBody String checkifEmailAlreadyExists(@RequestParam("email") String email){
		try{
			String status=this.userService.getUserByEmail(email);
			return status;
		}
		catch(IndexOutOfBoundsException e){
			log.error("No Email Exists "+e.toString());
			e.printStackTrace();
			return "success";
		}
		catch(Exception e){
			log.error("Error in validating email "+e.toString());
			e.printStackTrace();
			return "fail";
		}
	}
	
	//Check if Username already Exists or not... 
	
	@RequestMapping(value="/validateuser.do",method=RequestMethod.POST)
	public @ResponseBody String checkifUserAlreadyExists(@RequestParam("username") String username){
		try{
			HoveyUserDto userDto=this.userService.getUserByUsername(username);
			if(null==userDto){
				return "success";
			}
			else
				return "fail";
		}
		catch(Exception e){
			log.info("fail to Validate user"+e.toString());
			return "fail";
		}
			
	}
	
	
	
	/**
	 * @author JEEVAN
	 */

	//for checking Agent Number......
	@RequestMapping(value="/validateagent.do",method=RequestMethod.POST)
	@ResponseBody
	public String validateAgentNumber(@RequestParam("no") String agentNumber){
		log.info("inside validate agentNumber()");
		try{
			HoveyUserDto userDto=this.userService.getUserByAgentNumber(agentNumber);
		    if(null!=userDto){
		    	return "success";
		    }
		    else{
		    	return "fail";
		    }
			
		}
		catch(Exception e){
			log.error("failed to validate agentNumber"+e.toString());
			return "fail";
		}
	}
	
	
	
	/*
	 * Added by Jeevan on Aug 02, 2013.. 
	 * refactoring forgot password...
	 * 
	 * 
	 */
	@RequestMapping(value="/forgotpassword.do",method=RequestMethod.GET)
	public String initializeForgotPasswordProcess(Map<String,Object> map){
		log.info("inside initializeForgotPasswordProcess()");
		try{
			return "forgotPassword";
		}
		catch(Exception e){
			String message= "Failed to initialize forgot password process, Please try again later";
			log.error(message +" "+e.toString());
			map.put("message",message);
			return "error";
		}		
	}
	
	
	
	/*
	 * Accepts mail, sends Rest password link to mail..
	 */
	
	@RequestMapping(value="/forgotpassword.do",method=RequestMethod.POST)
	public String processForgotPAssword(@RequestParam("email") String email,Map<String, Object>map){
		log.info("inside processForgotPAssword( )");
		try{
			boolean status = this.userService.processForgotPassword(email);
			if(status==true){
				String message="An email has been sent to your account with details, please check it";
				map.put("message", message);
				return "mailSuccess";
			}
			else{
				throw new Exception();
			}
		}
		catch(HoveyUserNotFoundException e){
			String message="No Account found with the given email ";
			log.error(message+" "+e.toString() );
			map.put("message", message);
			return "error";
		}
		catch(MailNotSendException e){
			String message="Unable to send mail, please check the internet/configuration settings";
			log.error(message+" "+e.toString() );
			map.put("message", message);
			return "error";
		}
		catch(Exception e){
			e.printStackTrace();
			String message="An unexpected Error Occurred";
			log.error(message+" "+e.toString() );
			map.put("message", message);
			return "error";
		}
	}
	
	
	
	
	/*
	 * Initialising reset Password..
	 */
	@RequestMapping(value="/reset.do",method=RequestMethod.GET)
	public String initResetPassword(@RequestParam("ukey") String ukey,Map<String, Object> map){
		log.info("inside initResetPassword()");
		try{
			String username=this.userService.initPAsswordReset(ukey);
			if(null!=username){
				map.put("username", username);
				return "resetPassword";
			}
			else{
				throw new Exception();
			}			
		}
		catch(HoveyUserNotFoundException e){
			String message="No User found associating with the link";
			log.error(message+" "+e.toString());
			map.put("message",message);
			return "error";
		}
		catch(AccountExpiredException e){
			String message="Link validaity expired, please request for a new one";
			log.error(message+" "+e.toString());
			map.put("message",message);
			return "error";
		}
		catch(Exception e){
			e.printStackTrace();
			String message="An unexpected error occured";
			log.error(message+" "+e.toString());
			map.put("message",message);
			return "error";
		}
	}
	
	@RequestMapping(value="/reset.do",method=RequestMethod.POST)
	public String performPasswordReset(@RequestParam("username") String username,@RequestParam("password") String password,
			Map<String, Object> map){
		log.info("inside performPasswordReset()");
		try{
			String result=this.userService.resetPassword(username, password);
			if(null!=result){
				String message="Password Reset Successfully, Please Login to Continue..";
				map.put("message", message);
				return "mailSuccess";
			}
			else{
				throw new Exception();
			}
		}
		catch(HoveyUserNotFoundException e){
			String message="No User found to reset password";
			log.error(message+" "+e.toString());
			map.put("message",message);
			return "error";
		}
		catch(Exception e){
			e.printStackTrace();
			String message="An unexpected error occured";
			log.error(message+" "+e.toString());
			map.put("message",message);
			return "error";
		}
		
	}
	
	
	
	
	
	
	//Check if the Entered Email exists or not..
		@RequestMapping(value="/checkEmail.do",method=RequestMethod.POST)
		public @ResponseBody String checkifEmailExists(@RequestParam("email") String email){
			try{
				String status=this.userService.getUserByMail(email);
				return status;
			}
			catch(IndexOutOfBoundsException e){
				log.error("No Email Exists "+e.toString());
				e.printStackTrace();
				return "success";
			}
			catch(Exception e){
				log.error("Error in validating email "+e.toString());
				e.printStackTrace();
				return "fail";
			}
		}
	
}