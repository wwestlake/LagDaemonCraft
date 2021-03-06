package com.lagdaemon.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;

@Controller
@RequestMapping("/")
public class IndexController {
	
	//UserRepository repo;
	Facebook facebook;
	Twitter twitter;
	LinkedIn linkedin;
	ConnectionRepository connectionRepository;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public IndexController(Facebook facebook, Twitter twitter, LinkedIn linkedin, ConnectionRepository connectionRepository) {
		//this.repo = repo;
		this.facebook = facebook;
		this.twitter = twitter;
		this.linkedin = linkedin;
		this.connectionRepository = connectionRepository;
	}
	
	
    @GetMapping
    String index(Model model){
    	//User bill = new User(AuthenticationSource.LOCAL,"wwestlake@lagdaemon.com", true);
    	//repo.save(bill);
    	log.info("connecting to index");
        if (connectionRepository.findPrimaryConnection(Facebook.class) != null) {
        	log.info("primary connection is NOT null");
            model.addAttribute("name", facebook.userOperations().getUserProfile().getName());
        }
    	
        if (connectionRepository.findPrimaryConnection(Twitter.class) != null) {
        	log.info("primary connection is NOT null");
            model.addAttribute("name", twitter.userOperations().getUserProfile().getScreenName());
        }
    	
        if (connectionRepository.findPrimaryConnection(LinkedIn.class) != null) {
        	log.info("primary connection is NOT null");
        	LinkedInProfile profile = linkedin.profileOperations().getUserProfile();
            model.addAttribute("name", profile.getFirstName() + " " + profile.getLastName());
        }
        
    	//Iterable<User> users = repo.findAll();
    	
        return "index";
    }
    
  
    
}
