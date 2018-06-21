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

import com.sitdh.thesis.core.denim.database.entity.AccessToken;
import com.sitdh.thesis.core.denim.database.repository.AccessTokenRepository;
import com.sitdh.thesis.core.denim.database.service.UserService;
import com.sitdh.thesis.core.denim.form.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserServiceController {
	
	private UserService userService;
	
	private AccessTokenRepository accessTokenRepo;
	
	private HttpHeaders headers;
	
	@Autowired
	public UserServiceController(UserService userService, 
			AccessTokenRepository accessTokenRepo,
			HttpHeaders headers) {
		
		this.userService = userService;
		this.accessTokenRepo = accessTokenRepo;
		this.headers = headers;
	}
	
	@PostMapping("/account/new")
	public ResponseEntity<?> createNewUser(@ModelAttribute UserEntity userEntity) {
		log.debug("Create new user: " + userEntity.getUsername());

		return new ResponseEntity<>(userService.createNewUser(userEntity), headers, HttpStatus.OK);
	}
	
	@GetMapping("/account/{username}")
	public void userProfile(String user) {
		
	}
	
	@PostMapping("/account/auth")
	public void authenticateUser(@RequestParam("username") String username, 
			@RequestParam("password") String password) {
		
	}
	
	@PostMapping("/account/renew")
	public void renewingAccountAccess(
			@RequestParam("access_token") String accessToken, 
			@RequestParam("client") String client) {
		
		Optional<AccessToken> validAccessToken = this.accessTokenRepo.findByTokenAndClientNameAndExpiredDateAfter(
				accessToken, 
				client, 
				new Date());
		
		if (validAccessToken.isPresent()) {
			
		}
		
	}

}
