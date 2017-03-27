package com.lagdaemon.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lagdaemon.domain.User;
import com.lagdaemon.service.UserService;


@Controller
public class AdminController {

	@Autowired
    private UserService userService;

	@Secured("ROLE_ADMIN")
    @RequestMapping(value = "/useradmin", method = RequestMethod.GET)
    public String useradmin(Model model) {
		
		List<User> users = userService.allUsers();
		model.addAttribute("users", users);
		
    	return "/admin/UserAdmin";
    }
	
	
	
}
