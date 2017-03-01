package com.lagdaemon;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.lagdaemon.domain.Role;
import com.lagdaemon.domain.RoleRepository;
import com.lagdaemon.domain.User;
import com.lagdaemon.domain.UserRepository;
import com.lagdaemon.interfaces.AuthenticationSource;
import com.lagdaemon.service.SecurityService;
import com.lagdaemon.service.UserService;

@Component
public class DatabseInitializer implements ApplicationListener<ContextRefreshedEvent>  {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserService userService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	
	
	@Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("Context Refreshed Event");

		List<Role> roles = roleRepository.findAll();
		if (roles.size() > 0) return;
		
		// Initialize roles to default
		log.info("Configuring database with role and user");
		
		Role role = new Role();
		role.setRole("admin");
		roleRepository.save(role);
		
		User user = new User();
		user.addRole(role);
		user.setEmail("admin@lagdaemon.com");
		user.setPasswordHash("password");
        user.setAuthSource(AuthenticationSource.LOCAL);
        user.setLastLoginDateTime(LocalDateTime.now());
        user.setEmailValidated(true);
        userService.save(user);
		
		
	}
}
