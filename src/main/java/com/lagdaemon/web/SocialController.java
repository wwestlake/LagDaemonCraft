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
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import javax.annotation.PostConstruct;




@Controller
public class SocialController {
	
	private Facebook facebook;
	private Twitter twitter;
	private LinkedIn linkedin;
	private ConnectionRepository connectionRepository;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public SocialController(Facebook facebook, Twitter twitter, LinkedIn linkedin, ConnectionRepository connectionRepository) {
		//this.repo = repo;
		this.facebook = facebook;
		this.twitter = twitter;
		this.linkedin = linkedin;
		this.connectionRepository = connectionRepository;
	}
	
	
    //connect/facebook
    @RequestMapping(value = "/facebookInit", method=RequestMethod.GET)
    String facebook(Model model) {
    	log.info("Entering facebook");
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
        	log.info("primary connection is null");
            return "redirect:/connect/facebook";
        }
    	
        log.info("returning user profile");
        model.addAttribute("name", facebook.userOperations().getUserProfile().getName());
        return "hello";
    }
    
    @RequestMapping(value = "/twitterInit", method=RequestMethod.GET)
    public String twitter(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }
        model.addAttribute("name", twitter.userOperations().getUserProfile().getScreenName());
        return "/";
    }

    @RequestMapping(value = "/linkedinInit", method=RequestMethod.GET)
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

    @PostConstruct
    private void init() {
        try {
            String[] fieldsToMap = { "id", "about", "age_range", "birthday",
                    "context", "cover", "currency", "devices", "education",
                    "email", "favorite_athletes", "favorite_teams",
                    "first_name", "gender", "hometown", "inspirational_people",
                    "installed", "install_type", "is_verified", "languages",
                    "last_name", "link", "locale", "location", "meeting_for",
                    "middle_name", "name", "name_format", "political",
                    "quotes", "payment_pricepoints", "relationship_status",
                    "religion", "security_settings", "significant_other",
                    "sports", "test_group", "timezone", "third_party_id",
                    "updated_time", "verified", "viewer_can_send_gift",
                    "website", "work" };

            Field field = Class.forName(
                    "org.springframework.social.facebook.api.UserOperations")
                    .getDeclaredField("PROFILE_FIELDS");
            field.setAccessible(true);

            Field modifiers = field.getClass().getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, fieldsToMap);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }    
}
