package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helper.Helper;
import com.scm.userservices.UserServices;

@ControllerAdvice
public class RootController {

	@Autowired
	private UserServices userServices;
	
	@ModelAttribute
	public void addLoggedUserInformation(Model model,Authentication authentication)
	{
		if(authentication==null)
		{
			return;
		}
		
		String username = Helper.getEmailLogedinUser(authentication);
		System.out.println("This Username Come From Controller Advice"+username);
	
		User user = userServices.getUserEmailById(username);
		if (user == null) {
		    System.out.println("No user found with email: " + username);
		}

		model.addAttribute("loggedInUser",user);
		
		
	}
}
