package com.sitdh.thesis.core.denim.form.entity;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NonNull;

@Data
public class ProjectEntity {
	
	@NonNull @JsonProperty(value="project_name")
	private String projectName;

	@NonNull @JsonProperty(value="interested_package")
	private String interestedPackage;
	
	@NonNull 
	private String description;
	
	@JsonProperty(value="owner_token")
	private String ownerToken;
	
	private Optional<String> slug;
}
