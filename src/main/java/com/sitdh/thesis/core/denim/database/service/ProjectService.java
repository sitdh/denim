package com.sitdh.thesis.core.denim.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sitdh.thesis.core.denim.database.entity.Project;
import com.sitdh.thesis.core.denim.database.repository.ProjectRepository;
import com.sitdh.thesis.core.denim.form.entity.ProjectEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProjectService {
	
	private ProjectRepository projectRepo;
	
	@Autowired
	public ProjectService(ProjectRepository projectRepo) {
		log.debug("Init: " + ProjectService.class.getName());
		this.projectRepo = projectRepo;
	}

	public Project createProject(ProjectEntity projectEntity) {
		
	}
}
