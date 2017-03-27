package com.lagdaemon.service;

public interface RecaptchaService {
	boolean isResponseValid(String remoteIp, String response) throws RecaptchaServiceException;
}
