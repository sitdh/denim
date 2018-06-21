package com.sitdh.thesis.core.denim.form.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserEntity {
	
	@NotEmpty @Size(min=4)
	private String name;

	@NotEmpty @Size(min=4, max=20)
	private String username;
	
	@NotEmpty @Size(min=4, max=50)
	private String password;
	
	@NotEmpty @Size(min=4, max=50)
	private String confirmedPassword;
	
	@Email
	private String email;
	
}
