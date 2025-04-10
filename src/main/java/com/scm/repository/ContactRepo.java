package com.scm.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contacts;
import com.scm.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contacts, String>{

	User findByEmail(String email);
	
	Page<Contacts> findByUser(User user,Pageable pageable );
	
	//search
	Page<Contacts> findByUserAndNameContaining(User user,String name,Pageable pageable);
	
	Page<Contacts> findByUserAndEmailContaining(User user,String email,Pageable pageable);
	
	Page<Contacts>  findByUserAndPhoneNumberContaining(User user,String phoneNumber,Pageable pageable);

	

	
}
