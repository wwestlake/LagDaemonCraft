package com.lagdaemon.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  UserRepository extends JpaRepository<User, Long> {
	User findByMinecraftId(String mineCraftId);
	User findByEmail(String username);
	User findByLogin(String login);
	User findById(long id);
}
