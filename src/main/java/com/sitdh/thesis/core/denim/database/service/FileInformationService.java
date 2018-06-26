package com.sitdh.thesis.core.denim.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sitdh.thesis.core.denim.database.entity.FileInformation;
import com.sitdh.thesis.core.denim.database.entity.Project;
import com.sitdh.thesis.core.denim.database.repository.FileInformationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileInformationService {
	
	private FileInformationRepository fileRepo;

	@Autowired
	public FileInformationService(FileInformationRepository fileRepo) {
		this.fileRepo = fileRepo;
	}
	
	public FileInformation addFileToProject(FileInformation file, Project project) {
		log.debug("Add file " + file.getLocation() + " to " + project.getProjectName());
		file.setProject(project);
		
		return this.fileRepo.save(file);
	}
}
