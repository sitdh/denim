package com.sitdh.thesis.core.denim.form.entity;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProjectFormEntity {
	
	private MultipartFile file;
	
	private String project;
	
	private String description;
	
	private String pkg;
	
	private String token;
	
	private String user;

}
