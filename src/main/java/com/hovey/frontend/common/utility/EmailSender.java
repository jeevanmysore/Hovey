package com.hovey.frontend.common.utility;

public interface EmailSender {
	public void sendMail(String to,String subject,String body);
}
