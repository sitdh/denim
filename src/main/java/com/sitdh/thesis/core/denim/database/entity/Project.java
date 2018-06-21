package com.sitdh.thesis.core.denim.database.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Table @Data
public class Project {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer pid;
	
	@Column(name="project_name")
	@JsonProperty(value="project_name")
	private String projectName;
	
	private String slug;
	
	@JsonIgnore
	private String location;
	
	@Column(name="interested_package")
	@JsonProperty(value="interested_package")
	private String interestedPackage;
	
	private String description;
	
	@JsonProperty(value="created_date")
	@JsonFormat(locale="th", shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Bangkok")
	private Date createdDate;
	
	@JsonProperty(value="last_update")
	@JsonFormat(locale="th", shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Bangkok")
	private Date lastUpdate;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="owner")
	private User owner;
	
	
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
