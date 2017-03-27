package com.lagdaemon.web.admin;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lagdaemon.domain.User;
import com.lagdaemon.service.UserService;
import com.lagdaemon.web.RecaptchaController;

@Controller
public class UserAdminController extends RecaptchaController  {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
    private UserService userService;

	@Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/useredit/{id}", method = RequestMethod.GET)
    public String userEdit(@PathVariable int id, Model model) {
		
    	User user = userService.findById(id);
    	log.info("Editing user: " + user.getEmail());
    	model.addAttribute("userForm", user);
        return "/admin/useredit";
    }

	@Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/userupdate/{id}", method = RequestMethod.POST)
    public String userUpdate(@PathVariable int id, @ModelAttribute("userForm") User userForm, Model model) {

    	User dbUser = userService.findById(id);
    	
    	
    	dbUser.setFirstName(userForm.getFirstName());
    	dbUser.setLastName(userForm.getLastName());
    	dbUser.setDisplayName(userForm.getDisplayName());
    	dbUser.setMinecraftId(userForm.getMinecraftId());
    	dbUser.setLogin(userForm.getLogin());
    	dbUser.setEmail(userForm.getEmail());
    	dbUser.setLocked(userForm.getLocked());
    	
		
		userService.save(dbUser);
		return "redirect:/useradmin";
    }
	
	
	@Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/userlock/{id}", method = RequestMethod.GET)
    public String userLock(@PathVariable int id, Model model) {
    	User user = userService.findById(id);
    	user.setLocked(!user.getLocked());
    	userService.save(user);
        return "redirect:/useradmin";
    }
	
	@Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/userdelete/{id}", method = RequestMethod.GET)
    public String userDelete(@PathVariable int id) {
    	userService.deleteUser(id);
        return "redirect:/useradmin";
    }
	
	
}
