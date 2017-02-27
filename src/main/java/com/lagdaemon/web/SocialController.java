package com.lagdaemon.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;

@Controller
public class SocialController {
	
	private Facebook facebook;
	private Twitter twitter;
	private LinkedIn linkedin;
	private ConnectionRepository connectionRepository;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private @Autowired Model model;
	
	public SocialController(Facebook facebook, Twitter twitter, LinkedIn linkedin, ConnectionRepository connectionRepository) {
		//this.repo = repo;
		this.facebook = facebook;
		this.twitter = twitter;
		this.linkedin = linkedin;
		this.connectionRepository = connectionRepository;
	}
	
	
    //connect/facebook
    @RequestMapping(value = "/facebook", method=RequestMethod.GET)
    String facebook(Model model) {
    	log.info("Entering facebook");
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
        	log.info("primary connection is null");
            return "redirect:/connect/facebookConnect";
        }
    	
        log.info("returning user profile");
        model.addAttribute("name", facebook.userOperations().getUserProfile().getName());
        return "redirect:/connect/facebookConnected";
   }
   
    @RequestMapping(value = "/twitter", method=RequestMethod.GET)
    public String twitter(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }
        model.addAttribute("name", twitter.userOperations().getUserProfile().getScreenName());
        return "/";
    }

    @RequestMapping(value = "/linkedin", method=RequestMethod.GET)
    public String linkedin(Model model) {
        if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) {
            return "redirect:/connect/linkedin";
        }
        LinkedInProfile profile = linkedin.profileOperations().getUserProfile();
        model.addAttribute("name", profile.getFirstName() + " "  + profile.getLastName());
        return "/";
    }
    
    @RequestMapping(value = "/SocialConnect", method=RequestMethod.GET) 
    public String SocialConnect(Model model) { 
    	return "/SocialConnect";
    }

    @RequestMapping(value = "/connect/facebookConnect", method=RequestMethod.GET) 
    public String FBConnect(Model model) { 
    	log.info("handler for /connect/facebookConnect");
    	return "/connect/facebookConnect";
    }
    
    @RequestMapping(value = "/connect/facebookConnected", method=RequestMethod.GET) 
    public String FBConnected(Model model) { 
    	log.info("handler for /connect/facebookConnected");
    	return "/connect/facebookConnected";
    }

	///facebook#_=_ fix
    @RequestMapping(value = "/connect/facebook#_=_", method=RequestMethod.GET) 
    public String FBhashFix(Model model) { 
    	log.info("handler for facebook hash fix");
    	return "redirect:/connect/facebook";
    }

    
    
    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,
            ConnectionRepository connectionRepository, NativeWebRequest request) {

        ConnectController controller = new ConnectController(connectionFactoryLocator,
                connectionRepository);
            String url = "http://dev-machine.com:8080/";
            controller.setApplicationUrl(url) ;

            String status = controller.connectionStatus(request, model);
            
            log.info(status);
            
            
            //controller.setViewPath(viewPath);
            return controller;

    }    
    
}
