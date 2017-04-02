package com.lagdaemon.domain.forum;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lagdaemon.domain.User;

public interface ForumRepository extends JpaRepository<Forum, Long> {
	
	List<Forum> findByOwner(User owner);
	
}
