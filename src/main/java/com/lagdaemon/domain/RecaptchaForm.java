package com.lagdaemon.domain;


import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class RecaptchaForm {

	
	  @Transient	
	  private String recaptchaResponse;

	  public String getRecaptchaResponse() { return recaptchaResponse; }
	  public void setRecaptchaResponse(String value) { this.recaptchaResponse = value; }
	  
}
