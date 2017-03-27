package com.lagdaemon.service;

import org.springframework.web.client.RestClientException;

public class RecaptchaServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5380140457473960201L;

	public RecaptchaServiceException(String message, RestClientException e) {
		super(message, e);
	}
	
}
