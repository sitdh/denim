package com.sitdh.thesis.core.denim.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sitdh.thesis.core.denim.database.entity.Project;
import com.sitdh.thesis.core.denim.database.repository.ProjectRepository;
import com.sitdh.thesis.core.denim.file.storage.StorageService;
import com.sitdh.thesis.core.denim.utils.ProjectUtils;

@RestController
public class ProjectReceiverServiceController {

	private StorageService storage;
	
	private ProjectRepository projectRepo;
	
	@Autowired
	public ProjectReceiverServiceController(
			StorageService storage,
			ProjectRepository projectRepo) {
		this.storage = storage;
		this.projectRepo = projectRepo;
	}
	
	@PostMapping("/project/new")
	public Project newProject(
			@RequestParam("file") MultipartFile file,
			@RequestParam("project_name") String projectName,
			@RequestParam("description") String description,
			@RequestParam("package") String interestedPackage) {
		
		Project project = new Project();
		project.setInterestedPackage(interestedPackage);
		project.setDescription(description);
		project.setProjectName(projectName);
		project.setSlug(ProjectUtils.projectSlug(projectName));
		
		project.setLocation(this.storage.store(file, "hello").get());
		
		return this.projectRepo.save(project);
	}
}
