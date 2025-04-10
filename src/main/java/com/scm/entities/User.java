package com.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.CollectionType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements  UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String userId;
	@Column(nullable = false,name="user_name")
	private String name;
	@Column(unique  = true, nullable = false)
	private String email;
	@Getter(value=AccessLevel.NONE)
	private String password;
	@Column(length = 1000)
	private String about;
	@Column(length = 1000)
	private String profilepic;
	private String phoneNumber;
	
	@Getter(value=AccessLevel.NONE)
	private boolean isEnabled=false;
	
	private boolean isEmailidVerified=false;
	
	private boolean isphoneNumberVerified=false;
	
	//SELF,GOOGLE,GITHUB,FACEBOOK
	@Enumerated(value=EnumType.STRING)
	private Providers provider=Providers.SELF;
	private String providerUserId;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	private List<Contacts>contactList = new ArrayList<>();
    
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String>rolesList = new ArrayList<>();
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority>roles = rolesList.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList());
		return roles;
	}

	@Override
	public String getUsername() {
		
		return this.email;
	}
	
	@Override
	 public String getPassword()
	 {
		return this.password;
		
	 }
	
	@Override
	public  boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}

	
}