package com.scm.securityConfig;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helper.appConstaints;
import com.scm.repository.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OauthSuccessHandler implements AuthenticationSuccessHandler {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepo userRepo;
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		    logger.info("OauthSuccessHandler");
	   
		    var OAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
		    String oauthorizedClientUserId = OAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
		    logger.info(oauthorizedClientUserId);
		    
		    var oauth2User = (DefaultOAuth2User)authentication.getPrincipal();
		    oauth2User.getAttributes().forEach((key,value)->{
		    	logger.info("KEY:" +key+ "| Value:" +value);
		    });
		    
		    User user = new User();
		    user.setEmailidVerified(true);
		    user.setEnabled(true);
		    user.setRolesList(List.of(appConstaints.ROLE_USER));
		    if(oauthorizedClientUserId.equalsIgnoreCase("google"))
		    {
		    	
		    	user.setName(oauth2User.getAttribute("name").toString());
		    	user.setEmail(oauth2User.getAttribute("email").toString());
		    	user.setProfilepic(oauth2User.getAttribute("picture").toString());
		    	user.setProvider(Providers.GOOGLE);
		    	user.setProviderUserId(oauth2User.getName());
		    	
		    }
		    else if(oauthorizedClientUserId.equalsIgnoreCase("github"))
		    {
		     String email = oauth2User.getAttribute("email")!=null ? oauth2User.getAttribute("email").toString():
		    	 oauth2User.getAttribute("login").toString()+"@gmail.com";
		     
		     String picture = oauth2User.getAttribute("avatar_url").toString();
		     String name = oauth2User.getAttribute("login").toString();
		     String providerId = oauth2User.getName();
		     user.setProvider(Providers.GITHUB);
		     
		     user.setEmail(email);
		     user.setProfilepic(picture);
		     user.setName(name);
		     user.setProviderUserId(providerId);
		     
		    }
		    
		    
		 User databaseUser = userRepo.findByEmail(user.getEmail()).orElse(null);
		 
		    if(databaseUser==null)
		    {
		    	userRepo.save(user);
		    	System.out.println("Data Saved");
		    }
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
//		    DefaultOAuth2User defaultuser =(DefaultOAuth2User)authentication.getPrincipal();
//		   
//		    logger.info(defaultuser.getName().toString());
//		    
//		    defaultuser.getAttributes().forEach((key,value)->{
//		        logger.info("{} => {}",key , value);
//		    });
//		    
//		    String email = defaultuser.getAttribute("email").toString();
//		    String name =  defaultuser.getAttribute("name").toString();
//		    String picture =defaultuser.getAttribute("picture").toString();
//		    
//		    User user = new User();
//		    user.setName(name);
//		    user.setEmail(email);
//		    user.setPassword("password");
//		    user.setAbout("Akash Here");
//		    user.setProfilepic(picture);
//		    user.setProvider(Providers.GOOGLE);
//		    user.setEnabled(true);
//		    user.setEmailidVerified(true);
//		    user.setProviderUserId(user.getName());
//		    user.setRolesList(List.of(appConstaints.ROLE_USER));
//		    
//		    User user2 = userRepo.findByEmail(email).orElse(null);
//		    if(user2==null)
//		    {
//		    userRepo.save(user);
//		    logger.info("User saved:"+email);
//		    }
//		    
	       new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
//		    
//		   }
	
	}

}
