package com.scm.form;

import org.springframework.web.multipart.MultipartFile;

import com.scm.validators.validFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {

	@NotBlank(message = "name is required")
	private String name;
	
	@NotBlank(message="email is required")
	@Email
	private String email;
	
	@NotBlank(message="phoneNummber is required")
	private String phoneNumber;
	
	@validFile(message = "required")  //custom annotations
	private MultipartFile contactPicture;
	
	@NotBlank(message="address is required")
	private String address;
	
	private String description;
	
	private boolean favouriate;
	
	public String websiteLink;
	
	public String linkdienLink;
	
	public String picture;
	
	
	
}
