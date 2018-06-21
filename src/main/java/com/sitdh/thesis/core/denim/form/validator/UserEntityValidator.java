package com.sitdh.thesis.core.denim.form.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sitdh.thesis.core.denim.form.entity.UserEntity;

public class UserEntityValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserEntity.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		UserEntity userEntity = (UserEntity) target;
		
		if (!userEntity.getPassword().equals(userEntity.getConfirmedPassword())) {
			errors.reject("password", "password.mismatch");
		}
	}

}
