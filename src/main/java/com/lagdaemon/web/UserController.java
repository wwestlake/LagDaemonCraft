package com.lagdaemon.web;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.context.SecurityContextHolder;
import com.google.common.collect.ImmutableMap;
import com.lagdaemon.domain.User;
import com.lagdaemon.interfaces.AuthenticationSource;
import com.lagdaemon.service.EmailServiceImpl;
import com.lagdaemon.service.SecurityService;
import com.lagdaemon.service.UserService;
import com.lagdaemon.service.UserValidator;

import groovy.util.logging.Log;
import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

@Controller
public class UserController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

	@Autowired
	EmailServiceImpl emailService;

    
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
        	
            return "registration";
        }
        
        
        String uuid = UUID.randomUUID().toString(); 
        userForm.setAuthSource(AuthenticationSource.LOCAL);
        userForm.setLastLoginDateTime(LocalDateTime.now());
        userForm.setEmailValidated(false);
        userForm.setEmailValidationCode(uuid);
        userService.save(userForm);
        
        
    	try {
			//emailService.sendEmail("wwestlake@lagdaemon.com", "Bill Westlake", "This is a test", "I hope I got this email");
    		Map<String, Object> map = ImmutableMap.of( 
    				"verification_code", uuid,
    				"email", userForm.getEmail()
    		);
    		
    		emailService.sendEmailWithThymeleaf(userForm.getEmail(), "", "LagDaemon Craft Account Registration", "EmailVerificationCodeSend.html", map);
    		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotSendEmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
    	
    	//Iterable<User> users = repo.findAll();
    	
    	
        return "welcome";
    }
  //
    
    @RequestMapping(value = {"/verify"}, method = RequestMethod.GET)
    public String verify(@RequestParam("code") String code,
            @RequestParam("email") String email, Model model) {
    
    	log.info("Got code: " + code + " for email: " + email);
    	
    	User user = userService.findByUsername(email);
    	
    	log.info("Got user: " + user.getEmail() + " code: " + user.getEmailValidationCode());
    	
    	if (user.getEmailValidationCode().equals(code)) {
    		user.setEmailValidated(true);
    		user.setEmailValidationCode("");
    		log.info("Saving user validated");
    		userService.save(user);
    		return "redirect:/login";
    	}
    	return "redirect:/";
    }
    
    
    
    
}
