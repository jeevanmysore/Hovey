/* Project      : Hovey Energy 
 * Current File : UserDetailsServiceImpl.java 
 * Created By   : Nnupur Krishnaa on 18-04-2013
 * 
 * Copyright (c) 2012 KNS Technologies, Bangalore. All rights reserved.
 * 
 * Reason       : This is service class that is used for processing spring security check. 
 * 
 */
package com.hovey.common.security.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hovey.frontend.user.dto.HoveyUserDto;
import com.hovey.frontend.user.service.UserService;


@Service(value="userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{


	@Resource(name="userService")
	private UserService userService;
	
	private static Logger log=Logger.getLogger(UserDetailsServiceImpl.class);
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		log.info("in loadUserByUsername ");
		HoveyUserDto userEntity=null;
		try{
			userEntity=userService.getUserByUsername(username);
		}
		catch(Exception e){
			log.error("in user service "+e.toString());
		}
		 if (userEntity == null)
		    	throw new UsernameNotFoundException("user not found");

		    boolean flag;
		   if(userEntity.isEnabled()){
			   flag=true;
		   }
		   else{
			   flag=false;
		   }
		    
		  String userName = userEntity.getUsername();
		  String password = userEntity.getPassword();
		    
		    boolean enabled = flag;
		    boolean accountNonExpired = flag;
		    boolean credentialsNonExpired = flag;
		    boolean accountNonLocked = flag;
		    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		    System.out.println("userentity.................."+userEntity.getUserType());
		    /**
	    	 * Modified By Bhagya On October 30th,2014  ,as per clients modification
	    	 * Added a ROle FOr Super Admin
	    	 */
		    if(Character.toString(userEntity.getUserType()).equalsIgnoreCase("A")){
		    	authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
		    	
		    }else if(Character.toString(userEntity.getUserType()).equalsIgnoreCase("S")){
		    	authorities.add(new GrantedAuthorityImpl("ROLE_SUPER_ADMIN"));
		     }
		    else{
		    	authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
		    }
		    //authorities.add(new GrantedAuthorityImpl(Character.toString(userEntity.getUserType())));
		    
		    User user = new User(userName,password, enabled,accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		    return (UserDetails)user;
		
	}

}
