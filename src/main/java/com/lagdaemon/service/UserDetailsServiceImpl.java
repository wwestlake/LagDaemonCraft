package com.lagdaemon.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.lagdaemon.domain.Role;
import com.lagdaemon.domain.User;
import com.lagdaemon.domain.UserRepository;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		log.info("Found user: " + user.getEmail() + ", checking credentials");
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswordHash(), grantedAuthorities);
	}
	
	
}
