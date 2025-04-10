package com.scm.userservices;

import java.util.List;
import java.util.Optional;

import com.scm.entities.User;

public interface UserServices {

	User createById(User user);
	
	Optional<User> getById (String id);
	
	void deleteById(String id);
	
	Optional<User>update(User user);
	
	boolean isUserExistsByEmail(String email);
	
	boolean isUserExist(String id);
	
	List<User>getAllUsers();

	User getUserEmailById(String email);

	
	
	
}
