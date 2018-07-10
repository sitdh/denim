package com.sitdh.thesis.core.denim.analysis.structure.connect;

import org.springframework.stereotype.Component;

import com.sitdh.thesis.core.denim.database.entity.FileInformation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClassConnectivity implements Connectivity {
	
	@Override
	public void accept(FileInformation fileInfo) {
		log.debug("Accept file: " + fileInfo.getClassName());
	}

}
