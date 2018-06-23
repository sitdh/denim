package com.sitdh.thesis.core.denim.database.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sitdh.thesis.core.denim.database.entity.Project;
import com.sitdh.thesis.core.denim.database.entity.User;
import com.sitdh.thesis.core.denim.database.repository.ProjectRepository;
import com.sitdh.thesis.core.denim.file.storage.StorageService;
import com.sitdh.thesis.core.denim.form.entity.ProjectFormEntity;
import com.sitdh.thesis.core.denim.utils.ProjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProjectService {
	
	private ProjectRepository projectRepo;
	
	private StorageService storage;
	
	private Validator validator;
	
	@Autowired
	public ProjectService(
			ProjectRepository projectRepo,
			StorageService storage) {
		
		log.debug("Init: " + ProjectService.class.getName());
		this.projectRepo = projectRepo;
		this.storage = storage;
		
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	public Optional<List<Project>> fetchProjectForOwner(User user) {
		return projectRepo.findAllByOwner(user);
	}

	public Optional<Project> createProjectFromEntity(@Valid ProjectFormEntity formEntity, User user) {
		
		Project project = null;
		
		Set<ConstraintViolation<ProjectFormEntity>> p = validator.validate(formEntity);
		
		if (p.size() == 0) {
			log.debug("Project entity valid");
			
			project = this.entityMapping(formEntity);
			project.setOwner(user);
			this.storage
				.store(formEntity.getFile(), project.getSlug())
				.ifPresent(project::setLocation);
			
			project = this.projectRepo.save(project);
		} else {
			log.debug("Project: Invalid validation");
		}
		
		return Optional.ofNullable(project);
	}
	
	private Project entityMapping(ProjectFormEntity projectEntity) {
		Project project = new Project();
		
		project.setProjectName(projectEntity.getProject());
		project.setDescription(projectEntity.getDescription());
		project.setSlug(ProjectUtils.projectSlug(projectEntity.getProject()));
		project.setInterestedPackage(projectEntity.getPkg());
		
		return project;
	}
}
