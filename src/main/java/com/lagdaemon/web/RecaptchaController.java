package com.lagdaemon.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.lagdaemon.service.RecaptchaFormValidator;

public abstract class RecaptchaController {

	@Autowired
	RecaptchaFormValidator recaptchaFormValidator;
	
	@ModelAttribute("recaptchaSiteKey")
	public String getRecaptchaSiteKey(@Value("${recaptcha.site-key}") String recaptchaSiteKey) {
	    return recaptchaSiteKey;
	}
	
	@InitBinder("userForm")
	public void initBinder(WebDataBinder binder) {
	    binder.addValidators(recaptchaFormValidator);
	}
	
	
}
