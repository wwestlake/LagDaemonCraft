package com.lagdaemon.web;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lagdaemon.domain.User;
import com.lagdaemon.interfaces.AuthenticationSource;
import com.lagdaemon.service.SecurityService;
import com.lagdaemon.service.UserService;
import com.lagdaemon.service.UserValidator;

import groovy.util.logging.Log;

@Controller
public class UserController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

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

        userForm.setAuthSource(AuthenticationSource.LOCAL);
        userForm.setLastLoginDateTime(LocalDateTime.now());
        userForm.setEmailValidated(false);
        userService.save(userForm);
        
        
        securityService.autologin(userForm.getEmail(), userForm.getPasswordConfirm());

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
        return "welcome";
    }
}
