package com.lagdaemon.domain.blog;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lagdaemon.domain.User;

public interface BlogRepository  extends JpaRepository<Blog, Long>{
	Set<Blog> findByAuthor(User author);
	Set<Blog> findByCreatedOn(LocalDateTime date);
	Set<Blog> findByPublishedOn(LocalDateTime date);
}
