package com.sitdh.thesis.core.denim.database.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sitdh.thesis.core.denim.database.entity.User;
import com.sitdh.thesis.core.denim.database.repository.UserRepository;
import com.sitdh.thesis.core.denim.form.entity.UserEntity;
import com.sitdh.thesis.core.denim.util.hash.HashMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	private UserRepository userRepo;
	
	private HashMessage hashMessage, gravatarEmailHash;
	
	private Validator validator;
	
	@Autowired
	public UserService(UserRepository userRepo, 
			@Qualifier("SHADigest") HashMessage hashMessage,
			@Qualifier("GravatarHash") HashMessage gravatarEmailHash
			) {
		this.userRepo = userRepo;
		this.hashMessage = hashMessage;
		this.gravatarEmailHash = gravatarEmailHash;
		
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	public Optional<List<User>> findAll() {
		return Optional.ofNullable(this.userRepo.findAll());
	}

	public Optional<User> createNewUser(@Valid UserEntity user) {
		log.debug("Create user: " + user);
		Set<ConstraintViolation<UserEntity>> u = validator.validate(user);
		
		Optional<User> newUser = Optional.empty();
		if (0 == u.size() && user.getPassword().equals(user.getConfirmedPassword())) {
			log.debug("User information valid");
			User tmpUser = new User();
			tmpUser.setEmail(user.getEmail());
			tmpUser.setName(user.getName());
			tmpUser.setPassword(
					this.hashMessage.hash(user.getPassword()));
			tmpUser.setUsername(user.getUsername());
			
			tmpUser.setAvatar(
					String.format(
							"https://www.gravatar.com/avatar/%s", 
							this.gravatarEmailHash.hash(user.getEmail())));
			
			tmpUser = this.userRepo.save(tmpUser);
			
			newUser = Optional.of(tmpUser);
		} else {
			log.debug("Invalid user");
			u.stream().forEach(c -> {
				log.debug(c.getInvalidValue() + ": " + c.getMessage());
			});
		}
		
		return newUser;
	}
	
	public boolean isUsernameOrEmailExists(String username, String email) {
		return this.userRepo.findByUsernameOrEmail(username, email).isPresent();
	}

	public Optional<User> getUserFromUsernameAndPassword(String username, String password) {
		Optional<User> user = this.userRepo.findByUsernameAndPassword(
				username, 
				this.hashMessage.hash(password));
		
		return user;
	}
}
