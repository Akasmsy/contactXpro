package com.scm.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scm.entities.Contacts;
import com.scm.entities.User;
import com.scm.form.ContactForm;
import com.scm.form.SearchContactForm;
import com.scm.helper.AlertMessage;
import com.scm.helper.ColorType;
import com.scm.helper.Helper;
import com.scm.helper.appConstaints;
import com.scm.userservices.ContactService;
import com.scm.userservices.UserServices;
import com.scm.userservices.Imageservice;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class ContactsController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private Imageservice imageservice;
	
	@GetMapping("/contacts")
	public String addContacts(Model model) 
	{
		ContactForm contactform = new ContactForm();
		
		model.addAttribute("contactForm",contactform);
		
		return "user/add_contacts";
	}
	
	@PostMapping("/save_contacts")
	public String saveContacts(@Valid @ModelAttribute ContactForm contactForm,BindingResult bindingResult,Authentication authentication,RedirectAttributes redirectAttributes)
	{
		logger.info("contactImage: "+contactForm.getContactPicture().getOriginalFilename());
		
		System.out.println(contactForm);
		if(bindingResult.hasErrors())
		{
			bindingResult.getAllErrors().forEach(error->logger.info(error.toString()));
			
			return"user/add_contacts";
		}
		
		String username = Helper.getEmailLogedinUser(authentication);
		User user1= userServices.getUserEmailById(username);
		
		Contacts contect = new Contacts();
		
		
		contect.setName(contactForm.getName());
		contect.setEmail(contactForm.getEmail());
		contect.setPhoneNumber(contactForm.getPhoneNumber());
		contect.setAddress(contactForm.getAddress());
		contect.setDescription(contactForm.getDescription());
		contect.setFavourite(contactForm.isFavouriate());
		contect.setWebsiteLink(contactForm.getWebsiteLink());
		contect.setLinkedinLink(contactForm.getLinkdienLink());
		
		if(contactForm.getContactPicture()!=null && !contactForm.getContactPicture().isEmpty())
		{
		String fileName = UUID.randomUUID().toString();
		String fileUrl = imageservice.uploadImage(contactForm.getContactPicture(),fileName);
		
		contect.setPicture(fileUrl);
		contect.setPublicId(fileName);
		}
		contect.setUser(user1);
		
		contactService.saveContact(contect);
		
		
		redirectAttributes.addFlashAttribute("successMsg", new AlertMessage()
				.builder()
				.message("SuccessFully your contact is added...!")
				.color(ColorType.green)
				.build());
		return "redirect:/user/contacts";
		
		
	}
	
	
	//view Contacts
	
	@GetMapping("/view")
	public String viewContacts(
			@RequestParam(value="page",defaultValue ="0")int page,
			@RequestParam(value="size",defaultValue = "4")int size,
			@RequestParam(value="sortBy",defaultValue = "name")String sortBy,
			@RequestParam(value="direction",defaultValue = "asc")String direction,

			Model model , Authentication authentication)
	{
		String username = Helper.getEmailLogedinUser(authentication);
		User user1  =    userServices.getUserEmailById(username);
		Page<Contacts> pageContacts = contactService.getbyUser(user1, page, size,sortBy, direction);
		
		model.addAttribute("pageContacts",pageContacts);
		model.addAttribute("pageSize",appConstaints.PAGE_SIZE);
		model.addAttribute("searchContactForm",new SearchContactForm());
				
		return "user/contacts";
	}
	
	
	
	//search 
	@GetMapping("/search")
	public String searchContacts(
			
			@RequestParam(value="size",defaultValue = appConstaints.PAGE_SIZE+"")int size,
			@RequestParam(value="page",defaultValue = "0")int page,

			@RequestParam(value="sortBy",defaultValue = "name")String sortBy,
			@RequestParam(value="Order",defaultValue="asc")String Order,
			Model model,@ModelAttribute SearchContactForm searchContactForm,
			RedirectAttributes redirectAttributes,
			 Authentication authentication)
			{
		     
		    logger.info("SearchContact Form: "+searchContactForm);
		    
		    User user = userServices.getUserEmailById(Helper.getEmailLogedinUser(authentication));
		    
//		    if(searchContactForm.getField()==null || searchContactForm.getValue()==null)
//		    {
//		    	AlertMessage alertMessage = new AlertMessage()
//		    			                       .builder()
//		    			                       .color(ColorType.red)
//		    			                       .message("Plz select the fields and Value.....!")
//		    			                       .build();
//		    	redirectAttributes.addFlashAttribute("successMsg",alertMessage);
//		  	  return "redirect:/user/view";
//
//		    }
//		    
		    Page<Contacts> pageContacts = null;

		    if (searchContactForm.getField().equalsIgnoreCase("name")) {
		        pageContacts = contactService.searchByNameKeyword(user, searchContactForm.getValue(), size, page, sortBy, Order);
		    } else if (searchContactForm.getField().equalsIgnoreCase("email")) {
		        pageContacts = contactService.searchByEmailKeyword(user, searchContactForm.getValue(), size, page, sortBy, Order);
		    } else if (searchContactForm.getField().equalsIgnoreCase("phoneNumber")) {
		        pageContacts = contactService.searchByPhoneNumberKeyword(user, searchContactForm.getValue(), size, page, sortBy, Order);
		    }

		    // ✅ First check for null
		    if (pageContacts == null) {
		        AlertMessage alertMessage = AlertMessage.builder()
		            .color(ColorType.red)
		            .message("Please select the field and value!")
		            .build();

		        redirectAttributes.addFlashAttribute("successMsg", alertMessage);
		        return "redirect:/user/view";
		    }

		    // ✅ Now it's safe to log it
		    logger.info("Total Elements Found: " + pageContacts.getTotalElements());
		    logger.info("Content: " + pageContacts.getContent());

		    model.addAttribute("pageContacts", pageContacts);
		    model.addAttribute("pageSize", appConstaints.PAGE_SIZE);
		    model.addAttribute("searchContactForm", searchContactForm);

			   
					
		     return "user/search";
	}
	
	
	
	
	//delete
	@GetMapping("/contact/delete/{contactId}")
	public String delete(@PathVariable String contactId,RedirectAttributes redirectAttributes,Model model)
	{
      
	   contactService.deleteContact(contactId);
	   AlertMessage alertMessage = new AlertMessage().builder()
			                            .color(ColorType.green)
			                            .message("Contact Delete SuccessFully")
			                            .build();
	   redirectAttributes.addFlashAttribute("successMsg",alertMessage);
	  return "redirect:/user/view";
	}
	
	
	//update
	@GetMapping("/contact/view/{contactId}")
	public String updateView(@PathVariable String contactId,Model model)
	{
		Contacts contacts=contactService.getById(contactId);
		
		ContactForm contactForm = new ContactForm();
		contactForm.setName(contacts.getName());
		contactForm.setEmail(contacts.getEmail());
		contactForm.setAddress(contacts.getAddress());
		contactForm.setPhoneNumber(contacts.getPhoneNumber());
		contactForm.setLinkdienLink(contacts.getLinkedinLink());
		contactForm.setWebsiteLink(contacts.getWebsiteLink());
		contactForm.setFavouriate(contacts.isFavourite());
		contactForm.setDescription(contacts.getDescription());
		contactForm.setPicture(contacts.getPicture());
		model.addAttribute("contactForm", contactForm);
		model.addAttribute("contactId", contactId);
		return "user/update_contacts";
	}
	
	@PostMapping("/contact/update/{contactId}")
	public String updateContacts(@PathVariable String contactId,
			                     @ModelAttribute ContactForm contactForm,
			                     RedirectAttributes redirectAttributes
			                     )
	{
		var contact =  contactService.getById(contactId);
		contact.setContactId(contactId);
		contact.setName(contactForm.getName());
		contact.setEmail(contactForm.getEmail());
		contact.setAddress(contactForm.getAddress());
		contact.setFavourite(contactForm.isFavouriate());
		contact.setLinkedinLink(contactForm.getLinkdienLink());
		contact.setWebsiteLink(contactForm.getWebsiteLink());
		contact.setDescription(contactForm.getDescription());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setPicture(contactForm.getPicture());
		
		
		//resolve Image Update
		if(contactForm.getContactPicture()!=null &&!contactForm.getContactPicture().isEmpty())
		{
		String filename = UUID.randomUUID().toString();
		String imageUrl = imageservice.uploadImage(contactForm.getContactPicture(), filename);
		contact.setPublicId(filename);
		contact.setPicture(imageUrl);
		contactForm.setPicture(imageUrl);
		}
	    contactService.updateContact(contact);
	    redirectAttributes.addFlashAttribute("successMsg",new AlertMessage()
	    		                                        .builder()
	    		                                        .color(ColorType.green)
	    		                                        .message("message Updated Successfullly....!")
	    		                                        .build());
		
		return "redirect:/user/contact/view/"+contactId;
	}
	
}
