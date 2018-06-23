package com.sitdh.thesis.core.denim.ws.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sitdh.thesis.core.denim.database.entity.AccessToken;
import com.sitdh.thesis.core.denim.database.entity.Project;
import com.sitdh.thesis.core.denim.database.service.AccessTokenService;
import com.sitdh.thesis.core.denim.database.service.ProjectService;
import com.sitdh.thesis.core.denim.form.entity.ProjectFormEntity;
import com.sitdh.thesis.core.denim.ws.error.ErrorMessageResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProjectReceiverServiceController {
	
	private ProjectService projectService;
	
	private AccessTokenService tokenService;
	
	private HttpHeaders headers;
	
	@Autowired
	public ProjectReceiverServiceController(
			HttpHeaders headers,
			ProjectService projectService,
			AccessTokenService tokenService) {
		this.headers = headers;
		this.projectService = projectService;
		this.tokenService = tokenService;
	}
	
	@PostMapping("/project/new")
	public ResponseEntity<?> createNewProject(@ModelAttribute ProjectFormEntity formEntity) {
		System.out.println(formEntity);
		
		Optional<AccessToken> accessToken = this.tokenService
				.accessTokenForUserCredential(
						formEntity.getToken(), 
						formEntity.getUser());
		
		ResponseEntity<?> response = null;
		
		Optional<Project> project = this.createProject(accessToken, formEntity);
		
		if (project.isPresent()) {
			response = new ResponseEntity<>(project.get(), headers, HttpStatus.ACCEPTED);
		} else {
			ErrorMessageResponse responseMeessage = ErrorMessageResponse.builder()
					.title("Invalid project information")
					.description("Project information is invalid please try again later")
					.timestamp(new Date())
					.build();
			response = new ResponseEntity<>(responseMeessage, headers, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	private Optional<Project> createProject(Optional<AccessToken> accessToken, ProjectFormEntity formEntity) {
		Optional<Project> project = Optional.empty();
		log.debug("Prepare information to convert form entity");
		if (accessToken.isPresent()) {
			project = projectService.createProjectFromEntity(formEntity, accessToken.get().getOwner());
		} else {
			log.debug("Access token not found");
		}
		
		return project;
	}
}
