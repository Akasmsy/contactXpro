package com.scm.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

	public static String getEmailLogedinUser(Authentication authentication)
	{
		
		if(authentication instanceof OAuth2AuthenticationToken)
		{
			var authenticationToken =(OAuth2AuthenticationToken) authentication;
			 var authorizedUserId = authenticationToken.getAuthorizedClientRegistrationId();
			 
			 var defaultUser = (OAuth2User)authentication.getPrincipal();
			 String username ="";
			  if(authorizedUserId.equalsIgnoreCase("google"))
				  
			  {
				  username=defaultUser.getAttribute("email").toString();
				  System.out.println("This Email Id Come From Google");
				 
			  }
			  
			  else if(authorizedUserId.equalsIgnoreCase("github"))
			  {
				  System.out.println("THis Email Id Come From Github");
				  username=defaultUser.getAttribute("email")!=null ? defaultUser.getAttribute("email").toString():
				  defaultUser.getAttribute("login").toString() + "@gmail.com";
				 
				 
			  }
              return username;
		}
		else
		{
			
		    return authentication.getName();
			
		}
	
	}
}
