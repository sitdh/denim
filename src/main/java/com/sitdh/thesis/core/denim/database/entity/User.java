package com.sitdh.thesis.core.denim.database.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table
public class User {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(unique=true)
	private String username;
	
	@Lob @JsonIgnore
	private String password;
	
	@Column(unique=true) @Email
	private String email;
	
	private String name;
	
	private String avatar;
	
	@Column(name="created_date")
	@JsonProperty(value="created_date")
	@JsonFormat(locale="th", shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Bangkok")
	private Date createdDate;
	
	@Column(name="lastest_update")
	@JsonProperty(value="lastest_update")
	@JsonFormat(locale="th", shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Bangkok")
	private Date lastestUpdate;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="owner")
	private List<AccessToken> tokens;
	
	@JsonProperty(value="projects") @JsonFormat(shape=JsonFormat.Shape.ARRAY)
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="owner")
	private List<Project> projects;
	
	@PrePersist
	public void beforeCreate() {
		Date date = new Date();
		this.setCreatedDate(date);
		this.setLastestUpdate(date);
	}
	
	@PreUpdate
	public void updateTimestamp() {
		this.setLastestUpdate(new Date());
	}

}
