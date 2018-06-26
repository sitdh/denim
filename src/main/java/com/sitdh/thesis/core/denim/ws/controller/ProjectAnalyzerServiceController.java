package com.sitdh.thesis.core.denim.ws.controller;

import java.util.Date;
import java.util.Optional;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sitdh.thesis.core.denim.database.entity.AccessToken;
import com.sitdh.thesis.core.denim.database.entity.Project;
import com.sitdh.thesis.core.denim.database.service.AccessTokenService;
import com.sitdh.thesis.core.denim.database.service.UserService;
import com.sitdh.thesis.core.denim.ws.error.ErrorMessageResponse;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProjectAnalyzerServiceController {
	
	private UserService userService;
	
	private AccessTokenService tokenService;
	
	@Autowired
	private HttpHeaders headers;
	
	@Autowired
	public ProjectAnalyzerServiceController(
			UserService userService, 
			AccessTokenService tokenService) {
		
		log.debug("Project analyzer entered");
		this.userService = userService;
		this.tokenService = tokenService;
	}
	
	@GetMapping("/analysis/project/{slug}")
	public ResponseEntity<?> analysisClassConnectivity(
			@NonNull @PathParam("slug") String slug, 
			@NonNull @RequestParam("token") String token) {
		
		ResponseEntity<?> response = null;
		
		Optional<AccessToken> accessToken = this.tokenService.accessTokenInformation(token);
		
		if (accessToken.isPresent()) {
			Optional<Project> project = accessToken.get()
					.getOwner()
					.getProjects()
					.stream()
					.filter(p -> p.getSlug().equals(slug))
					.findFirst();
		} else {
			response = new ResponseEntity<>(
					ErrorMessageResponse.builder()
						.title("Invalid credential")
						.description("Invalid credetial please try again")
						.timestamp(new Date()).build(),
					headers,
					HttpStatus.UNAUTHORIZED);
		}
		
		
		return response;
	}

}
