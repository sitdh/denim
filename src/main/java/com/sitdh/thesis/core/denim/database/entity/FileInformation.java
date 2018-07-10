package com.sitdh.thesis.core.denim.database.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity @Table
public class FileInformation {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer fid;
	
	private String location;
	
	private String sourceLocation;
	
	private String packageName;
	
	@Column(name="class_name")
	private String className;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="project")
	private Project project;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="fileInfo")
	private List<ConstantValue> constants;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="className")
	private List<MethodInformation> methods;

}
