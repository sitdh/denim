package com.sitdh.thesis.core.denim.database.entity;

import java.util.List;

import javax.persistence.CascadeType;
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
public class MethodInformation {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer methodId;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="class_name")
	private FileInformation className;
	
	private String methodName;
	
	private String signature;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="methodName")
	private List<MethodGraph> graph;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="invoke")
	private List<MethodGraph> invoked;

}
