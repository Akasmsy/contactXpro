package com.scm.userservices;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.entities.Contacts;
import com.scm.entities.User;

public interface ContactService {

	Contacts saveContact(Contacts contact);
	
	Contacts updateContact(Contacts contact);
	
	List<Contacts>fetchAllContacts();
	
	void deleteContact(String id);
	
	Contacts getById(String id);
	
	User getByEmail(String email);
	
	//search
	Page<Contacts>searchByNameKeyword(User user,String name,int size,int page,String sortBy,String Order);
	
	Page<Contacts>searchByEmailKeyword(User user,String email,int size,int page,String sortBy,String Order);
	
	Page<Contacts>searchByPhoneNumberKeyword(User user,String phoneNumber,int size,int page,String sortBy,String Order);
	
	//Pagination
	Page<Contacts>getbyUser(User user,int page,int size,String sortBy,String direction);

	
	
}
