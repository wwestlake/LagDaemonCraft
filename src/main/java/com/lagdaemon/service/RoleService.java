package com.lagdaemon.service;

import com.lagdaemon.domain.Role;

public interface RoleService {
	void save(Role user);

	Role findByRolename(String rolename);

}
