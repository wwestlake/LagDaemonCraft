package com.lagdaemon.web;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import com.google.common.collect.ImmutableMap;
import com.lagdaemon.domain.User;
import com.lagdaemon.service.EmailServiceImpl;
import com.lagdaemon.service.RecaptchaFormValidator;
import com.lagdaemon.service.UserService;
import com.lagdaemon.service.UserValidator;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

@Controller
public class UserController extends RecaptchaController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private UserService userService;

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
    public String registration(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
        	
            return "registration";
        }
        
        User newUser = userService.createUser(userForm);
        
    	try {
    		Map<String, Object> map = ImmutableMap.of( 
    				"verification_code", newUser.getEmailValidationCode(),
    				"email", userForm.getEmail()
    		);
    		
    		emailService.sendEmailWithThymeleaf(newUser.getEmail(), "", "LagDaemon Craft Account Registration", "EmailVerificationCodeSend.html", map);
    		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (CannotSendEmailException e) {
			e.printStackTrace();
		}    	

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        if(referrer!=null){
            request.getSession().setAttribute("url_prior_login", referrer);
        }
    	model.addAttribute("userForm", new User());
        return "login";
    }

    @RequestMapping(value = "/loginRecaptcha", method = RequestMethod.POST)
    public String loginRecaptcha(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "login";
        }
    	
    	return "forward:/login";
    }
    
    @RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
    public String loginSuccess(HttpServletRequest request) throws MalformedURLException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        log.info(name);
        String url = (String) request.getSession().getAttribute("url_prior_login");

        User user = userService.findByUsername(name);
        user.setLastLoginDateTime(LocalDateTime.now());
        
        String path = new URL(url).getPath();
    	return "redirect:" + path;
    }
    
    @RequestMapping(value = {"/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }
    
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
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
    
    @RequestMapping(value="/profile", method = RequestMethod.GET)
    public String editProfile (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        log.info(name);
        
        User user = userService.findByUsername(name);
        model.addAttribute("user", user);
        
        return "/user/profile";
    }
    
    @RequestMapping(value="/profile", method = RequestMethod.POST)
    public String saveProfile (@ModelAttribute User user, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        
    	User dbUser = userService.findByUsername(name);
    	
    	log.info(user.getFirstName());
    	
    	dbUser.setFirstName(user.getFirstName());
    	dbUser.setLastName(user.getLastName());
    	dbUser.setDisplayName(user.getDisplayName());
    	dbUser.setMinecraftId(user.getMinecraftId());
    	
    	
        userService.save(dbUser);
        model.addAttribute("message", "Profile Saved");
        return "/user/profile";
    }

    @RequestMapping(value="/manualEmailValidation", method = RequestMethod.GET)
    public String getManualEmailValidation (Model model) {
    	model.addAttribute("emailValidationForm",new EmailValidation());
    	return "manualEmailValidation";
    }
     
    @RequestMapping(value="/manualEmailValidation", method = RequestMethod.POST)
    public String postManualEmailValidation (@ModelAttribute("emailValidationForm") @Valid EmailValidation form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "manualEmailValidation";
        }
    	
    	return userService.validateUserEmail(form.getEmail(), form.getCode());
    }
    
    
}
