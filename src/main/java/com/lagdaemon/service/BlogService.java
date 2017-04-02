package com.lagdaemon.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.lagdaemon.domain.User;
import com.lagdaemon.domain.blog.Blog;
import com.lagdaemon.domain.blog.BlogRepository;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private UserService userService;
	
	public List<Blog> getAllBlogs() {
		List<Blog> result = new ArrayList<Blog>();
		blogRepository.findAll().forEach(result::add);
		return result;
	}

	public Blog createBlog(String title, String description) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User author = userService.findByUsername(name);
		Blog result = new Blog();
		result.setTitle(title);
		result.setDescription(description);
		result.setCreatedOn(LocalDateTime.now());
		result.setAuthor(author);
		result.setIsPublished(false);
		result.setAllowComments(false);
		result.setPublishedOn(LocalDateTime.now());
		blogRepository.save(result);
		return result;
	}

	public void deleteBlog(Long id) {
		blogRepository.delete(id);
	}
	
}

