package com.sitdh.thesis.core.denim.ws.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sitdh.thesis.core.denim.analysis.structure.SourceStructureAnalyzer;
import com.sitdh.thesis.core.denim.database.entity.AccessToken;
import com.sitdh.thesis.core.denim.database.entity.Project;
import com.sitdh.thesis.core.denim.database.service.AccessTokenService;
import com.sitdh.thesis.core.denim.database.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProjectAnalyzerServiceController implements DefaultServiceController {
	
	private AccessTokenService tokenService;
	
	private SourceStructureAnalyzer<?,?> structureAnalyzer;
	
	@Autowired
	private HttpHeaders headers;
	
	@Autowired
	public ProjectAnalyzerServiceController(
			UserService userService, 
			AccessTokenService tokenService,
			SourceStructureAnalyzer<?,?> structureAnalyzer) {
		
		log.debug("Project analyzer entered");
		this.tokenService = tokenService;
		this.structureAnalyzer = structureAnalyzer;
	}
	
	@GetMapping("/analysis/project/{slug}")
	public ResponseEntity<?> analysisClassConnectivity(
			@PathVariable("slug") String slug, 
			@RequestParam("token") String token) {
		
		ResponseEntity<?> response = null;
		
		Optional<AccessToken> accessToken = this.tokenService.accessTokenInformation(token);
		
		if (accessToken.isPresent()) {
			log.debug("Received slug: " + slug);
			Optional<Project> project = accessToken.get()
					.getOwner()
					.getProjects()
					.stream()
					.filter(p -> p.getSlug().equals(slug))
					.findFirst();
			
			this.structureAnalyzer.analyze(project.get());
			
		} else {
			response = this.responseError(
					"Invalid credential", 
					"Invalid credetial please try again", 
					headers, 
					HttpStatus.UNAUTHORIZED);
			
		}
		
		
		return response;
	}

}
