package com.sitdh.thesis.core.denim.database.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity @Table
public class ConstantValue {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer constantId;
	
	private String type;
	
	private String constantValue;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="file_info")
	private FileInformation fileInfo;
}
