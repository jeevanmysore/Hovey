/* Project      : Hovey Energy 
 * Current File : UserDaoImpl.java 
 * Created By   : Nnupur Krishnaa on 18-04-2013
 * 
 * Copyright (c) 2012 KNS Technologies, Bangalore. All rights reserved.
 * 
 * Reason       : This is interface class that contains definition of methods declared in UserDao. 
 * 
 */
package com.hovey.backend.user.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.hovey.backend.user.model.HoveyUser;



@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	private static Logger log=Logger.getLogger(UserDaoImpl.class);
	
	public void registerUser(HoveyUser user)throws Exception{
		log.info("inside registerUser()");
		Assert.notNull(user);
		hibernateTemplate.save(user);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();	
	}

	public HoveyUser getUserInfoFromUsername(String username)throws Exception{
		log.info("inside getUserInfoFromUsername ");
		try{
			@SuppressWarnings("unchecked")
				
			
			ArrayList<HoveyUser> users=(ArrayList<HoveyUser>) hibernateTemplate.find("from HoveyUser where username='"+username+"'");
			if(!users.isEmpty())
				return users.get(0);
			else
				return null;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void insertUkey(HoveyUser user) {
		log.info("inside insertUkey()");
		Assert.notNull(user);
		hibernateTemplate.update(user);
		
	}

	@Override
	public HoveyUser getUserByEmail(String email) {
		@SuppressWarnings("unchecked")
		ArrayList<HoveyUser> users=(ArrayList<HoveyUser>) hibernateTemplate.find("from HoveyUser where email='"+email+"'");
		if(!users.isEmpty())
			return users.get(0);
		else
			return null;
	}

	@Override
	public HoveyUser getUserByUkey(String ukey) {
		@SuppressWarnings("unchecked")
		ArrayList<HoveyUser> users=(ArrayList<HoveyUser>) hibernateTemplate.find("from HoveyUser where ukey='"+ukey+"'");
		if(!users.isEmpty())
			return users.get(0);
		else
			return null;
	}

	@Override
	public void updatePassword(HoveyUser user) {
		log.info("inside updatepassword");
		Assert.notNull(user);
		hibernateTemplate.update(user);
		
	}

	@Override
	public HoveyUser validateEmail(String email) {
		log.info("inside vaidateEmail()");
		HoveyUser user=(HoveyUser) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class)
				.add(Restrictions.eq("email",email)).list().get(0);
		return user;
	}

	@Override
	public HoveyUser checkEmail(String email) {
		log.info("inside checkEmail()");
		@SuppressWarnings("unchecked")
		ArrayList<HoveyUser> users=(ArrayList<HoveyUser>) hibernateTemplate.find("from HoveyUser where email='"+email+"'");
		if(!users.isEmpty())
			return users.get(0);
		else
			return null;
	}

	@Override
	public ArrayList<HoveyUser> getDisabledUsers() {
		log.info("in side getDisabledUsers()");
		@SuppressWarnings("unchecked")
		ArrayList<HoveyUser> users=(ArrayList<HoveyUser>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class)
									.add(Restrictions.eq("enabled",false)).list();
		if(users.isEmpty()){
			return null;
		}
		else{
			return users;
		}
	}

	@Override
	public void updateUser(HoveyUser user) {
		Assert.notNull(user);
		hibernateTemplate.update(user);
	}
	
	//added by Jeevan
	//geting User by Agent Number..
	public HoveyUser getUserByAgentNumber(String agentNumber)throws Exception{
		log.info("inside getUserByAgentNumber()");
		HoveyUser user=(HoveyUser) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(HoveyUser.class)
				.add(Restrictions.eq("agentNumber", agentNumber)).list().get(0);
		return user;
	}
	
	
}