package com.sitdh.thesis.core.denim.analysis.structure;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.sitdh.thesis.core.denim.analysis.structure.connect.Connectivity;
import com.sitdh.thesis.core.denim.database.entity.Project;
import com.sitdh.thesis.core.denim.database.service.MethodInformationService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClassStructureAnalyzer<ClassStructureInformation> implements SourceStructureAnalyzer<List<ClassStructureInformation>, List<Path>> {

	private MethodInformationService methodInfoService;
	
	private Connectivity methodConnectivity;
	
	@Autowired
	public ClassStructureAnalyzer(
			MethodInformationService methodInfoService,
			@Qualifier("methodConnectivity") Connectivity methodConnectivity) {
		
		this.methodInfoService = methodInfoService;
		this.methodConnectivity = methodConnectivity;
	}
	
	@Override
	public void assignedResource(List<Path> resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ClassStructureInformation> analyze(@NonNull Project project) {
		log.debug("Project accepted");
		List<ClassStructureInformation> classStructure = Lists.newArrayList();
		
		log.debug("Project exists");
		
		project.getFiles().stream().forEach(this.methodConnectivity::accept);
		
		return classStructure;
	}

}
