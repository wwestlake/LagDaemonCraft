package com.lagdaemon.service;

import java.util.List;

import com.lagdaemon.domain.User;

public interface UserService {

	void save(User user);

	User findByUsername(String username);
	User findByLogin(String login);
	User createUser(User user);
	User findById(long id);
	String validateUserEmail(String email, String code);
	List<User> allUsers();
	void deleteUser(long id);
}
