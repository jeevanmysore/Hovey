/* Project      : Hovey Energy 

 * Current File : LoginController.java 
 * Created By   : Nnupur Krishnaa on 26-04-2013
 * 
 * Copyright (c) 2012 KNS Technologies, Bangalore. All rights reserved.
 * 
 * Reason       : This is controller class will decide whether to redirect to admin page or user home page
 * 
 */
package com.hovey.frontend.user.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.hovey.frontend.user.dto.HoveyUserDto;
import com.hovey.frontend.user.service.UserService;

@Controller
public class HomeController {
	
	@Resource(name="userService")
	private UserService userService;
	
	private static Logger log=Logger.getLogger(HomeController.class);
	@RequestMapping(value="/home.do",method=RequestMethod.GET)	
	
	public String redirectToHome(HttpServletRequest request,Map<String, Object>map){
		log.info("inside home");	
		try{
			Authentication auth=SecurityContextHolder.getContext().getAuthentication();
			
			String user=auth.getName();
			System.out.println("user "+user);
			if(null!=user){
				String role=SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().toString();
				
				HoveyUserDto userDto=this.userService.getUserByUsername(user);
				map.put("user",userDto);	
				/**
				 * Modified By Bhagya On October 30th,2014,as per clients modification
				 * applying a role for super admin and when login as super admin its redirected to dasbboard page
				 */
				if(role.equalsIgnoreCase("ROLE_SUPER_ADMIN")){
					log.info("inside redirectToLoginSuccess" );
					return "redirect:/admindashboard.do";
				}
				else if(role.equalsIgnoreCase("ROLE_ADMIN")){
					log.info("inside redirectToLoginSuccess" );
						return "redirect:/admin/getpipeline.do";
				}
				else{
					
					return "redirect:/agent/home.do";
				}			
			}
			else
			return "redirect:/";
		}
		catch(Exception e){
			return "redirect:/";
		}
	}	
	
	
	@RequestMapping("/agent/home.do")
	public String redirectToAgentHome(HttpServletRequest request,Map<String, Object>map){
		log.info("inside redirectToAgentHome");
		try{			
				Authentication auth=SecurityContextHolder.getContext().getAuthentication();
			
				String user=auth.getName();
				System.out.println("user "+user);
				if(null!=user){			
					HoveyUserDto userDto=this.userService.getUserByUsername(user);
					map.put("user",userDto);				
					return "agent/userHome";
				}
				else{
					throw new Exception();
				}
		}
		catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}
	}
	
}
