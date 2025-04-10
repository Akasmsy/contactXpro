package com.scm.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
    @NotBlank(message="Invalid UserName")
    @Size(min=3,max=10)
	private String name;
    @NotBlank(message="Invalid Email")
	private String email;
    @NotBlank(message="Invalid Password")
    @Size(min=3,max=10)
	private String password;
    @NotBlank(message="Invalid PhoneNumber")
    @Size(min=8,max=12)
	private String phoneNumber;
    @NotBlank(message="Plz Enter The Something Here")
	private String about;
}
