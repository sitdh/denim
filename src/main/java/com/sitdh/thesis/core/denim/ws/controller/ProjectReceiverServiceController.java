package com.sitdh.thesis.core.denim.ws.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.sitdh.thesis.core.denim.database.entity.AccessToken;
import com.sitdh.thesis.core.denim.database.entity.Project;
import com.sitdh.thesis.core.denim.database.entity.User;
import com.sitdh.thesis.core.denim.database.service.AccessTokenService;
import com.sitdh.thesis.core.denim.database.service.ProjectService;
import com.sitdh.thesis.core.denim.database.service.UserService;
import com.sitdh.thesis.core.denim.form.entity.DestroyProjectFormEntity;
import com.sitdh.thesis.core.denim.form.entity.ProjectFormEntity;
import com.sitdh.thesis.core.denim.ws.error.ErrorMessageResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProjectReceiverServiceController {
	
	private ProjectService projectService;
	
	private AccessTokenService tokenService;
	
	private HttpHeaders headers;
	
	private UserService userService;
	
	@Autowired
	public ProjectReceiverServiceController(
			HttpHeaders headers,
			ProjectService projectService,
			UserService userService,
			AccessTokenService tokenService) {
		
		this.headers = headers;
		this.projectService = projectService;
		this.tokenService = tokenService;
		this.userService = userService;
	}
	
	@GetMapping("/project/{user}/{slug}")
	public ResponseEntity<?> displayProjectInformation(@PathVariable("user") String user, @PathVariable("slug") String slug) {
		ResponseEntity<?> response = null;
		
		Optional<Project> projects = this.projectInformationFromSlugAndUser(
				this.userService.getUserInformation(Optional.ofNullable(user)), 
				slug)
				.stream()
				.filter(p -> p.getSlug().equals(slug))
				.findFirst();
		
		if (projects.isPresent()) {
			response = new ResponseEntity<>(projects.get(), headers, HttpStatus.OK);
		} else {
			
			response = new ResponseEntity<>(
					ErrorMessageResponse.builder()
						.title("Information not found")
						.description("Project is not available")
						.timestamp(new Date())
						.build(), 
					headers, 
					HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	@GetMapping("/project/{user}")
	public ResponseEntity<?> listingProjectInformation(@PathVariable("user") String user) {
		ResponseEntity<?> response = null;
		
		Optional<User> userInfo = this.userService.getUserInformation(Optional.of(user));
		
		if (userInfo.isPresent()) {
			response = new ResponseEntity<>(userInfo.get().getProjects(), headers, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(Lists.newArrayList(), 
					headers, 
					HttpStatus.OK);
		}
		
		return response;
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
	
	@PostMapping("/project/destroy")
	public ResponseEntity<?> destroyProject(@ModelAttribute DestroyProjectFormEntity destroyFormEntity) {
		
		
		return null;
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
	
	private List<Project> projectInformationFromSlugAndUser(Optional<User> user, String slug) {
		log.debug("Get project from user and slug");
		List<Project> projects = Lists.newArrayList();
		if (user.isPresent()) {
			log.debug("User found");
			projects = user.get().getProjects();
		}
		
		return projects.stream().filter(p -> p.getSlug().equals(slug)).collect(Collectors.toList());
	}
	
}
