package com.sitdh.thesis.core.denim.analysis.collector.method;

import java.io.IOException;
import java.util.Optional;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.sitdh.thesis.core.denim.analysis.collector.MethodCollection;
import com.sitdh.thesis.core.denim.database.entity.FileInformation;
import com.sitdh.thesis.core.denim.database.service.MethodInformationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MethodStructureCollection implements MethodCollection {
	
	private MethodInformationService methodInfoService;
	
	public MethodStructureCollection(MethodInformationService methodInfoService) {
		this.methodInfoService = methodInfoService;
	}
	
	public void accept(FileInformation fileInfo) {
		this.loadJavaClass(fileInfo.getLocation()).ifPresent(f -> this.methodCollector(f, fileInfo));
	}
	
	private Optional<JavaClass> loadJavaClass(String location) {
		log.debug("Load class");
		Optional<JavaClass> javaClass = Optional.empty();
		
		try {
			javaClass = Optional.ofNullable(new ClassParser(location).parse());
			log.debug("Class loaded");
		} catch (ClassFormatException | IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		return javaClass;
	}
	
	private void methodCollector(JavaClass jc, FileInformation fileInfo) {
		log.debug("Accept class: " + jc.getClassName());
		Lists.newArrayList(jc.getMethods()).stream().forEach(m -> {
			
			if (! "<init>".equals(m.getName()) && jc.getPackageName().startsWith(fileInfo.getProject().getInterestedPackage())) {
				this.methodInfoService.attachMethod(
						m.getName(), 
						m.getSignature(), 
						fileInfo);
			}
		});
	}

}
