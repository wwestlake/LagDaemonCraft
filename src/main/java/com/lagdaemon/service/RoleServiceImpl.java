package com.lagdaemon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lagdaemon.domain.Role;
import com.lagdaemon.domain.RoleRepository;
import com.lagdaemon.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

	@Override
	public void save(Role role) {
		roleRepository.save(role);
		
	}

	@Override
	public Role findByRolename(String rolename) {
		Role role = roleRepository.findByRole(rolename);
		return role;
	}

	
	
	
}
