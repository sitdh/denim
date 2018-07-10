package com.sitdh.thesis.core.denim.database.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sitdh.thesis.core.denim.database.entity.FileInformation;
import com.sitdh.thesis.core.denim.database.entity.MethodInformation;
import com.sitdh.thesis.core.denim.database.repository.MethodInformationRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MethodInformationService {
	
	private MethodInformationRepository methodInfoRepo;
	
	private FileInformationService fileInfo;
	
	@Autowired
	public MethodInformationService(
			MethodInformationRepository methodInfoRepo,
			FileInformationService fileInfo) {
		
		this.methodInfoRepo = methodInfoRepo;
		this.fileInfo = fileInfo;
	}

	public void attachMethod(String name, String signature, FileInformation fileInfo) {
		MethodInformation methodInfo = new MethodInformation();
		
		methodInfo.setClassName(fileInfo);
		methodInfo.setMethodName(name);
		methodInfo.setSignature(signature);
		
		
		this.methodInfoRepo.save(methodInfo);
		log.debug("Save information: " + name);
	}

	public Optional<MethodInformation> getMethodFromClassAndMethod(
			@NonNull String className,
			@NonNull String name) {
		
		Optional<FileInformation> f = fileInfo.getFileInformationFromClassname(className);
		Optional<MethodInformation> mInfo = Optional.empty();
		if (f.isPresent()) {
			mInfo = f.get()
					.getMethods()
					.stream()
					.filter(m -> m.getMethodName().equals(name))
					.findFirst();
			
		}
		
		return mInfo;
	}

}
