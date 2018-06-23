package com.sitdh.thesis.core.denim.database.entity;

import java.time.Instant;
import java.time.ZoneId;
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

import lombok.Data;

@Data
@Entity
@Table(name="access_token")
public class AccessToken {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="access_token_id")
	private Integer ati;
	
	@Column(name="client_name")
	private String clientName;
	
	private String token;
	
	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name="lastest_access_date")
	private Date lastestAccessDate;
	
	@Column(name="expired_date")
	private Date expiredDate;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="owner")
	private User owner;
	
	@PrePersist
	public void persistData() {
		Date date = new Date();
		Instant localDatetime = date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime()
				.plusDays(1)
				.atZone(ZoneId.systemDefault())
				.toInstant();
		
		this.setCreatedDate(date);
		this.setLastestAccessDate(date);
		this.setExpiredDate(Date.from(localDatetime));
		
	}
	
	@PreUpdate
	public void updateDate() {
		this.setLastestAccessDate(new Date());
	}
}
