package com.lagdaemon.service;

import com.lagdaemon.domain.User;

public interface UserService {

	void save(User user);

	User findByUsername(String username);

}
