package com.scm.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.helper.Helper;

@Controller
@RequestMapping("/user")
public class UserController {

	//user Dashboard Page
	@GetMapping("/dashboard")
	public String dasboard()
	{
		System.out.println("This is DashBoard Controller");
		return"user/dashboard";
	}
	
	//user Profile Page
	@RequestMapping("/profile")
	public String profile()
	{
		return "user/profile";
	}
	
	
}
