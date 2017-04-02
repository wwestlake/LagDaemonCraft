package com.lagdaemon.web.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lagdaemon.domain.User;
import com.lagdaemon.domain.blog.Blog;
import com.lagdaemon.service.BlogService;


@Controller
public class BlogAdminController {
	
	@Autowired
	private BlogService blogService;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/blogadmin", method = RequestMethod.GET)
	public String blogAdmin(Model model) {
		List<Blog> blogList = blogService.getAllBlogs();
		model.addAttribute("blogList", blogList);
		model.addAttribute("blogForm",new Blog());
		return "/admin/BlogAdmin";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/blogadmin/create", method = RequestMethod.POST)
	public String createBlog(@ModelAttribute("blogForm") Blog newBlog, Model model) {
		blogService.createBlog(newBlog.getTitle(), newBlog.getDescription());
		return "redirect:/blogadmin";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/blogadmin/delete/{id}", method = RequestMethod.GET)
	public String createBlog(@PathVariable Long id, Model model) {
		blogService.deleteBlog(id);
		return "redirect:/blogadmin";
	}
	
	
}
