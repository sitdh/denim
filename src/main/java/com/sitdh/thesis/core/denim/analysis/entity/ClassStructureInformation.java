package com.sitdh.thesis.core.denim.analysis.entity;

import lombok.Data;

@Data
public class ClassStructureInformation {

	private String targetClass;
	
	private String sourceClass;
	
	private MethodPair pair;
}
