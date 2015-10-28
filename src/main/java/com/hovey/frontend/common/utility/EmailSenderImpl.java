package com.hovey.frontend.common.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;

/*
 * created by Jeevan on Aug 02,2013..
 * 
 */

@Repository("emailSender")
public class EmailSenderImpl implements EmailSender {

	@Autowired
	private MailSender mailSender;
	
	public void sendMail(String to,String subject,String body){
		SimpleMailMessage mail=new SimpleMailMessage();
		mail.setCc("jeevan@knstek.com");
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(body);
		mailSender.send(mail);
	}
	
}
