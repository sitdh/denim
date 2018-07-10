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
public class MethodGraph {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer mgId;
	
	private String lineNumber;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="method_name")
	private MethodInformation methodName;
	
	private String instructions;
	
	private String nextLines;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="invoke")
	private MethodInformation invoke;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="previous")
	private List<MethodGraph> next;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="previous")
	private MethodGraph previous;
	
}
