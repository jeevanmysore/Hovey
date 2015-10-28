/* Project      : Hovey Energy 

 * Current File : LoginController.java 
 * Created By   : Nnupur Krishnaa on 18-04-2013
 * 
 * Copyright (c) 2012 KNS Technologies, Bangalore. All rights reserved.
 * 
 * Reason       : This is controller class that contains methods and method request mapping for further processing. 
 * 
 */
package com.hovey.frontend.user.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.hovey.frontend.user.service.UserService;




@Controller
public class LoginController {

	
	private static Logger log=Logger.getLogger(LoginController.class);
	
	@Resource(name="userService")
	private UserService userService;
	
	//after successful login...
	
	@RequestMapping(value="/loginSuccess.do",method=RequestMethod.GET)
	public String redirectToLoginSuccess(HttpServletRequest request,Map<String, Object> map){
		
		HttpSession session=request.getSession();
		try{			
			Authentication auth=SecurityContextHolder.getContext().getAuthentication();
			
			String user=auth.getName();
			System.out.println("USER"+user);
			session.setAttribute("username", user);
			
			return "redirect:/home.do";
		}
			catch(Exception e){
				e.printStackTrace();
				String error="Problem While Login, Please Try Again Later";
				map.put("message",error);
				return "error";
			}
		}
	
	
	/*
	//Display Admin Page
		@RequestMapping(value="/admin.do",method=RequestMethod.GET)
		public ModelAndView displayAdminPage(){
			log.info("inside displayAdminPage()");
			try{
				ArrayList<HoveyUserDto> userDtos=this.userService.getDisabledUsers();
				if(!userDtos.isEmpty()){
					return new ModelAndView("admin/admin","users",userDtos);
				}
				else{
					return new ModelAndView("admin/admin");
				}
				
			}
			catch(NullPointerException e){
				return new ModelAndView("admin/admin");
			}
			catch(Exception e){
				log.error("Error while displaying admin Page "+e.toString());
				e.printStackTrace();
				String error="Error Occured While Displaying Admin Home";
				return new ModelAndView("error","error",error);
			}
		}
		*/
		//Enable the User...
		
		@RequestMapping(value="/activateuser.do",method=RequestMethod.GET)
		public ModelAndView userActivationByAdmin(@RequestParam("username") String username){
			log.info("inside userActivationByAdmin()");
			try{
				this.userService.EnableUser(username);
				return new ModelAndView("redirect:/admin.do");
				
			}
			catch(Exception e){
				log.error("Error while Activating user"+e.toString());
				String error="Error While Activating User";
				return new ModelAndView("error","error",error);
			}
		}
		
		//Logout.. 
		
		@RequestMapping(value="/logout.do", method=RequestMethod.GET)
		public void logout(HttpServletRequest request, HttpServletResponse response){
			try{
				
				HttpSession session=request.getSession(false);
				session.invalidate();
				response.reset();

				   response.setHeader("Cache-Control", "no-cache");
				   response.setHeader("Pragma", "no-cache");
				    response.setHeader("Cache-Control", "no-store");
				   response.setHeader("Cache-Control", "must-revalidate");
				   response.setDateHeader("Expires", 0);
				   response.sendRedirect("./");
			}
			catch(Exception e){
				log.info("USer Logged Out"+ e.toString());
				try {
					response.sendRedirect("./");
				} catch (IOException e1) {
					log.info(e.getMessage()+" "+e.getCause());
					e1.printStackTrace();
				}
			}
		
		}
}
	