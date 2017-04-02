package com.lagdaemon.web;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagdaemon.domain.User;
import com.lagdaemon.minecraft.ServerListPing17;

@Controller
public class MinecraftStatusController {

	@Autowired
	private ServerListPing17 server;
	
	
    @RequestMapping(value = "/lagdaemoncraft", method = RequestMethod.GET)
    public String lagdaemoncraft_status(Model model) {
    	server.setAddress("minecraft.lagdaemon.com", 25765);
    	
    	try {
			ServerListPing17.StatusResponse response = server.fetchData();
			model.addAttribute("status", response);
		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("status", null);
		}
    	
    	model.addAttribute("userForm", new User());
        return "ldcstatus";
    }

    @ResponseBody
    @RequestMapping("/favicon")
    public byte[] favicon() throws IOException {
    	server.setAddress("minecraft.lagdaemon.com", 25765);
        return server.fetchData().getFaviconBytes();
    }
	
}
