package com.lagdaemon.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lagdaemon.domain.Role;
import com.lagdaemon.domain.RoleRepository;
import com.lagdaemon.domain.User;
import com.lagdaemon.domain.UserRepository;
import com.lagdaemon.interfaces.AuthenticationSource;

import groovy.util.logging.Log;

@Service
public class UserServiceImpl implements UserService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private RoleService roleService;

    
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String email) {
        return userRepository.findByEmail(email);
    }    
	
    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }    

    @Override
    public User findById(long id) {
    	return userRepository.findById(id);
    }
  
    @Override
    public void deleteUser(long id) {
    	userRepository.delete(id);
    }
    
    
    @Override
    public User createUser(User userForm) {
        
        String uuid = UUID.randomUUID().toString(); 
        userForm.setAuthSource(AuthenticationSource.LOCAL);
        userForm.setLastLoginDateTime(LocalDateTime.now());
        userForm.setEmailValidated(false);
        userForm.setEmailValidationCode(uuid);
        userForm.setPasswordHash(bCryptPasswordEncoder.encode(userForm.getPasswordHash()));
        userForm.setPasswordConfirm("");
		userForm.setLocked(false);

        Role role = roleService.findByRolename("ROLE_USER");
        userForm.addRole(role);
        save(userForm);
        
        
        return userForm;
    }

	@Override
	public String validateUserEmail(String email, String code) {
    	
    	User user = findByUsername(email);
    	if (user == null) user = this.findByLogin(email);
    	if (user == null) return "redirect:/";
    	
    	if (user.getEmailValidationCode().equals(code)) {
    		user.setEmailValidated(true);
    		user.setEmailValidationCode("-");
    		log.info("Saving user:" + user.getEmail());
    		save(user);
    		return "redirect:/login";
    	}
    	return "redirect:/";		
	}

	@Override
	public List<User> allUsers() {
		return userRepository.findAll();
	}    
    
    
}
