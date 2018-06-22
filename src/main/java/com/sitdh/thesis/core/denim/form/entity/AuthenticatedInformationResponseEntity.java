package com.sitdh.thesis.core.denim.form.entity;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sitdh.thesis.core.denim.database.entity.AccessToken;

import lombok.Data;

@Data
public class AuthenticatedInformationResponseEntity {
	
	private Integer id;
	
	private String username;
	
	private String email;
	
	@JsonProperty(value="access_token")
	private String accessToken;
	
	@JsonProperty(value="client_name")
	private String clientName;
	
	@Past @JsonProperty(value="accessed_date")
	@JsonFormat(locale="th", shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Bangkok")
	private Date accessedDate;
	
	@Future @JsonProperty(value="expired_date")
	@JsonFormat(locale="th", shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Bangkok")
	private Date expiredDate;
	
	public AuthenticatedInformationResponseEntity() {}
	
	public AuthenticatedInformationResponseEntity(AccessToken token) {
		
		this.setId(token.getOwner().getId());
		this.setUsername(token.getOwner().getUsername());
		this.setEmail(token.getOwner().getEmail());
		this.setAccessToken(token.getToken());
		
		this.setClientName(token.getClientName());
		
		this.setAccessedDate(token.getLastestAccessDate());
		this.setExpiredDate(token.getExpiredDate());
	}

}