package com.scm.userservices;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helper.ResourceNotFoundException;
import com.scm.helper.appConstaints;
import com.scm.repository.UserRepo;

@Service
public class UserServiceImpl implements UserServices {
   
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public User createById(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRolesList(List.of(appConstaints.ROLE_USER));
		user.setEnabled(true);
		return userRepo.save(user);
	}

	@Override
	public Optional<User> getById(String id) {
		
		return userRepo.findById(id) ;
	}

	@Override
	public void deleteById(String id) {
	Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent())
		{
			User user2 = optionalUser.get();
			userRepo.deleteById(id);
		}
		else
	    {
	    	throw new ResourceNotFoundException("User Not Found in so how I can Delete");
	    }
	    		
	}

	@Override
	public Optional<User> update(User user) {
    Optional<User> optionalUser = userRepo.findById(user.getUserId());
    if(optionalUser.isPresent())
    {
    	User existedUser = optionalUser.get();
    	existedUser.setName(user.getName());
    	existedUser.setEmail(user.getEmail());
    	existedUser.setPassword(user.getPassword());
    	existedUser.setPhoneNumber(user.getPhoneNumber());
    	existedUser.setAbout(user.getAbout());
    	existedUser.setProfilepic(user.getProfilepic());
    	existedUser.setEnabled(user.isEnabled());
    	existedUser.setEmailidVerified(user.isEmailidVerified());
    	existedUser.setIsphoneNumberVerified(user.isIsphoneNumberVerified());
    	System.out.println(user.getProvider());
    	existedUser.setProvider(user.getProvider());
    	System.out.println(user.getProvider());
    	existedUser.setProviderUserId(user.getProviderUserId());
    	return Optional.of(userRepo.save(existedUser));
    }
    else
    {
    	throw new ResourceNotFoundException("User Not Found");
    }
    		
	
}

	@Override
	public boolean isUserExistsByEmail(String email) {
	   User user = userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User Not found through email"));
	    return user !=null?true:false;
	}

	@Override
	public boolean isUserExist(String id) {
		User user = userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User is not exist through id"));
	     return user!=null ? true :false;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();

	}

	@Override
	public User getUserEmailById(String email) {
		return userRepo.findByEmail(email).orElse(null);
	}
}
