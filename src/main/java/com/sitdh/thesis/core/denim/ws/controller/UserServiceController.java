package com.sitdh.thesis.core.denim.ws.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sitdh.thesis.core.denim.database.entity.User;
import com.sitdh.thesis.core.denim.database.service.AccessTokenService;
import com.sitdh.thesis.core.denim.database.service.UserService;
import com.sitdh.thesis.core.denim.form.entity.UserEntity;
import com.sitdh.thesis.core.denim.ws.error.ErrorMessageResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserServiceController {
	
	private UserService userService;
	
	private AccessTokenService accessTokenService;
	
	private HttpHeaders headers;
	
	@Autowired
	public UserServiceController(UserService userService, 
			AccessTokenService accessTokenService,
			HttpHeaders headers) {
		
		this.userService = userService;
		this.accessTokenService = accessTokenService;
		this.headers = headers;
	}
	
	@PostMapping("/account/new")
	public ResponseEntity<?> createNewUser(@ModelAttribute UserEntity userEntity) {
		log.debug("Create new user: " + userEntity.getUsername());
				
		Optional<User> user = userService.createNewUser(userEntity);
		ResponseEntity<?> response;
		
		if (user.isPresent()) {
			log.debug("Data valid");
			response = new ResponseEntity<>(user.get(), headers, HttpStatus.ACCEPTED);
		} else {
			log.error("Invalid data");
			ErrorMessageResponse errorMessage = ErrorMessageResponse.builder()
					.description("No data information")
					.title("Invalid information")
					.timestamp(new Date())
					.build();
			
			response = new ResponseEntity<>(errorMessage, headers, HttpStatus.BAD_REQUEST);
		}

		return response;
	}
	
	@GetMapping("/account/{username}")
	public void userProfile(String user) {
		
	}
	
	@PostMapping("/account/auth")
	public ResponseEntity<?> authenticateUser(@RequestParam("username") String username, 
			@RequestParam("password") String password) {
		ResponseEntity<?> response = null;
		
		Optional<User> u = this.userService.getUserFromUsernameAndPassword(username, password);
		if (u.isPresent()) {
			response = new ResponseEntity<>(
					this.accessTokenService.createAccessTokenForUser(u.get()), 
					headers, 
					HttpStatus.OK);
		} else {
			ErrorMessageResponse errorMessage = ErrorMessageResponse.builder()
					.description("Wrong username or password")
					.title("Unauthorized")
					.timestamp(new Date())
					.build();
			response = new ResponseEntity<>(errorMessage, headers, HttpStatus.UNAUTHORIZED);
		}
		
		return response;
	}
	
	@PostMapping("/account/renew")
	public void renewingAccountAccess(
			@RequestParam("access_token") String accessToken, 
			@RequestParam("client") String client) {
		
		
	}

}
