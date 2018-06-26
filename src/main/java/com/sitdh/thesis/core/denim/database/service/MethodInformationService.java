package com.sitdh.thesis.core.denim.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sitdh.thesis.core.denim.database.entity.FileInformation;
import com.sitdh.thesis.core.denim.database.entity.MethodInformation;
import com.sitdh.thesis.core.denim.database.repository.MethodInformationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MethodInformationService {
	
	private MethodInformationRepository methodInfoRepo;
	
	@Autowired
	public MethodInformationService(MethodInformationRepository methodInfoRepo) {
		this.methodInfoRepo = methodInfoRepo;
	}

	public void attachMethod(String name, String signature, FileInformation fileInfo) {
		MethodInformation methodInfo = new MethodInformation();
		
		methodInfo.setClassName(fileInfo);
		methodInfo.setMethodName(name);
		methodInfo.setSignature(signature);
		
		
		this.methodInfoRepo.save(methodInfo);
		log.debug("Save information: " + name);
	}

}
