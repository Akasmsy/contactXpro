package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contacts {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String contactId;
	private String name;
	private String  email;
	private String address;
	private String phoneNumber;
	private String picture;
	@Column(length=1000)
	private String description;
	public boolean favourite;
	public String websiteLink;
	public String linkedinLink;
	public String publicId;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "contact",cascade = CascadeType.ALL,fetch=FetchType.EAGER )
	private List<SocialLink>socialLink = new ArrayList<>();
}
