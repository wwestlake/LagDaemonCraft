package com.lagdaemon.service;


import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class EmailServiceImpl {

	@Autowired
	public EmailService emailService;

	@Value("${com.lagdaemon.from-email}")
	String fromEmail;
	
	@Value("${com.lagdaemon.from-name}")
	String fromName;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	public void sendEmailWithThymeleaf(String toEmail, String toName, String subject, String template, Map<String, Object> model) throws UnsupportedEncodingException, CannotSendEmailException {

		final Email email = DefaultEmail.builder()
				.from(new InternetAddress(fromEmail, fromName))
				.to(newArrayList(new InternetAddress(toEmail, toName)))
				.subject(subject)
				.body("")
				.encoding("UTF-8")
				.build();

		log.info("Sending email from: " + fromEmail + " to: " + toEmail);
		
		emailService.send(email, template, model);
	}	

	public void sendEmail(String toEmail, String toName, String subject, String body) throws UnsupportedEncodingException, CannotSendEmailException {

		
		final Email email = DefaultEmail.builder()
				.from(new InternetAddress(fromEmail, fromName))
				.to(newArrayList(new InternetAddress(toEmail, toName)))
				.subject(subject)
				.body(body)
				.encoding("UTF-8")
				.build();

		log.info("Sending email from: " + fromEmail + " to: " + toEmail);

		emailService.send(email);
	}	
	

}
