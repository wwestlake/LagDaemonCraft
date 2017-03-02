package com.lagdaemon;

import javax.inject.Inject;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.stereotype.Controller;

//@Controller
public class CustomConnectController extends ConnectController {
    @Inject
    public CustomConnectController(
            ConnectionFactoryLocator connectionFactoryLocator,
            ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator, connectionRepository);
    }
    //This connectedView will be called after user authorize twitter app.  So here you can redirect   
    //users to the page you need.
    @Override
    protected String connectedView(String providerId){
        return "redirect:/";
    }
    //This connectView will be called if user disconnect from social media.  Here you can redirect 
    //them once they got disconnected.  
    @Override
    protected String connectView(String providerId) {
        return "redirect:/" + providerId;
    }
}
