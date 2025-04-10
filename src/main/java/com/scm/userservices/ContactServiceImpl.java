package com.scm.userservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contacts;
import com.scm.entities.User;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repository.ContactRepo;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepo contactRepo;
	
	@Override
	public Contacts saveContact(Contacts contact) {
		return  contactRepo.save(contact);
	}

	@Override
	public Contacts updateContact(Contacts contact) {
		Contacts oldContact=contactRepo.findById(contact.getContactId()).orElseThrow(()-> new ResourceNotFoundException("Id Not Found in Update Method"));
		oldContact.setName(contact.getName());
		oldContact.setEmail(contact.getEmail());
		oldContact.setAddress(contact.getAddress());
		oldContact.setLinkedinLink(contact.getLinkedinLink());
		oldContact.setFavourite(contact.isFavourite());
		oldContact.setWebsiteLink(contact.getWebsiteLink());
		oldContact.setPicture(contact.getPicture());
		oldContact.setPublicId(contact.getPublicId());
		return contactRepo.save(oldContact);
	}

	@Override
	public List<Contacts> fetchAllContacts() {
		
		return contactRepo.findAll();
	}

	@Override
	public void deleteContact(String id) {
	
       var contact= contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id Not Found So How Can I delete:"+id));
	
		contactRepo.deleteById(id);
	}

	@Override
	public Contacts getById(String id) {
		
	return  contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id Not Found"+id));
		
	}


	@Override
	public Page<Contacts> getbyUser(User user, int page, int size, String sortBy, String direction) {
		 Sort sort = direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		 Pageable pageable1 = PageRequest.of(page, size, sort);
		return contactRepo.findByUser(user, pageable1);
	}
	
	
	//search method;

	@Override
	public Page<Contacts> searchByNameKeyword(User user,String name, int size, int page, String sortBy, String Order) {
		Sort sort = Order.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page,size,sort);
		return contactRepo.findByUserAndNameContaining(user,name, pageable);
	}

	@Override
	public Page<Contacts> searchByEmailKeyword(User user, String email, int size, int page, String sortBy,
			String Order) {
		Sort sort = Order.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable pageable =  PageRequest.of(page,size,sort);
		return contactRepo.findByUserAndEmailContaining(user, email, pageable);
	}

	@Override
	public Page<Contacts> searchByPhoneNumberKeyword(User user, String phoneNumber, int size, int page, String sortBy,
			String Order) {
		
		Sort sort = Order.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable pageable =  PageRequest.of(page,size,sort);
		return contactRepo.findByUserAndPhoneNumberContaining(user, phoneNumber, pageable);
		   
		
	}

	@Override
	public User getByEmail(String email) {
		
		return contactRepo.findByEmail(email);
	}


	
	
	
}
