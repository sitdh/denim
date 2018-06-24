package com.sitdh.thesis.core.denim.ws.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sitdh.thesis.core.denim.database.entity.AccessToken;
import com.sitdh.thesis.core.denim.database.entity.User;
import com.sitdh.thesis.core.denim.database.service.AccessTokenService;
import com.sitdh.thesis.core.denim.database.service.UserService;
import com.sitdh.thesis.core.denim.form.entity.AuthenticatedInformationResponseEntity;
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
	public ResponseEntity<?> userProfile(@PathVariable("username") String user, @RequestParam("token") String token) {
		Optional<AccessToken> accToken = this.accessTokenService
				.accessTokenForUserCredential(token, user);
		
		ResponseEntity<?> response = null;
		if (accToken.isPresent()) {
			response = new ResponseEntity<>(accToken.get(), headers, HttpStatus.OK);
		} else {
			ErrorMessageResponse errorMessage = ErrorMessageResponse.builder()
					.description("Wrong username credential")
					.title("Unauthorized")
					.timestamp(new Date())
					.build();
			response = new ResponseEntity<>(errorMessage, headers, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	@PostMapping("/account/auth")
	public ResponseEntity<?> authenticateUser(@RequestParam("username") String username, 
			@RequestParam("password") String password) {
		log.debug("Authenticate user");
		
		ResponseEntity<?> response = null;
		
		Optional<User> u = this.userService.getUserFromUsernameAndPassword(username, password);
		if (u.isPresent()) {
			log.debug("User found");
			response = new ResponseEntity<>(
					this.accessTokenService.createAccessTokenForUser(u.get()), 
					headers, 
					HttpStatus.OK);
		} else {
			log.debug("User not found: " + username);
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
	public ResponseEntity<?> renewingAccountAccess(
			@RequestParam("access_token") String accessToken, 
			@RequestParam("client") String client) {
		
		ResponseEntity<?> response;
		
		Optional<AuthenticatedInformationResponseEntity> token = this.accessTokenService.renewForToken(accessToken, client);
		if (token.isPresent()) {
			log.debug("Credential found");
			response = new ResponseEntity<>(token.get(), headers, HttpStatus.OK);
		} else {
			log.error("Invalid credential");
			ErrorMessageResponse errorMessage = ErrorMessageResponse.builder()
					.description("Credential not found")
					.title("Invalid credential")
					.timestamp(new Date())
					.build();
			
			response = new ResponseEntity<>(errorMessage, headers, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	@GetMapping("/account/signout/{username}")
	public ResponseEntity<?> signout(
			@PathVariable("username") String username, 
			@RequestParam("access_token") String token) {
		
		ErrorMessageResponse responseMessage = null;
		HttpStatus status = HttpStatus.OK;
		
		Optional<AccessToken> accToken = this.accessTokenService.accessTokenForUserCredential(token, username);
		if (accToken.isPresent()) {
			log.debug("Credential found, killing access token");
			this.accessTokenService.killAccessToken(accToken.get());
			
			responseMessage = ErrorMessageResponse.builder()
					.title("Signout completed")
					.description("You already signout.")
					.timestamp(new Date())
					.build();
			
		} else {
			log.debug("Invalid access token");
			responseMessage = ErrorMessageResponse.builder()
					.title("Invalid credential")
					.description("No credential found.")
					.timestamp(new Date())
					.build();
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<>(responseMessage, headers, status);
	}

}
