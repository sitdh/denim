package com.sitdh.thesis.core.denim.database.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table @Data
public class Project {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer pid;
	
	@Column(name="project_name")
	private String projectName;
	
	private String name;
	
	private String slug;
	
	private String location;
	
	@Column(name="interested_package")
	private String interestedPackage;
	
	private String description;
	
	private Date createdDate;
	
	private Date lastUpdate;
	
	
	@PrePersist
	public void prePersist() {
		Date date = new Date();
		this.setCreatedDate(date);
		this.setLastUpdate(date);
	}
	
	@PreUpdate
	public void preUpdate() {
		this.setLastUpdate(new Date());
	}
}
