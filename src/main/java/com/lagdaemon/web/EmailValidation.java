package com.lagdaemon.web;

import com.lagdaemon.domain.RecaptchaForm;

public class EmailValidation extends RecaptchaForm {
	private String email;
	private String code;

	
	public String getEmail() { return this.email; }
	public void setEmail(String value) { this.email = value; }
	
	public String getCode() { return this.code; }
	public void setCode(String value) { this.code = value; }

	
}
