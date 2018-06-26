package com.sitdh.thesis.core.denim.analysis.structure;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.sitdh.thesis.core.denim.database.entity.Project;
import com.sitdh.thesis.core.denim.database.service.MethodInformationService;

@Component
public class ClassStructureAnalyzer<ClassStructureInformation> implements SourceStructureAnalyzer<List<ClassStructureInformation>, List<Path>> {

	private MethodInformationService methodInfoService;
	
	@Autowired
	public ClassStructureAnalyzer(MethodInformationService methodInfoService) {
		this.methodInfoService = methodInfoService;
	}
	
	@Override
	public void assignedResource(List<Path> resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ClassStructureInformation> analyze(Optional<Project> project) {
		List<ClassStructureInformation> classStructure = Lists.newArrayList();
		
		if (!project.isPresent()) return classStructure;
		
		project.get().getFiles().stream().forEach(f -> {
			
		});
		
		return classStructure;
	}

}
