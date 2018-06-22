package com.sitdh.thesis.core.denim.database.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.sitdh.thesis.core.denim.database.entity.AccessToken;
import com.sitdh.thesis.core.denim.database.entity.User;
import com.sitdh.thesis.core.denim.database.repository.AccessTokenRepository;
import com.sitdh.thesis.core.denim.form.entity.AuthenticatedInformationResponseEntity;
import com.sitdh.thesis.core.denim.util.hash.HashMessage;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service @Data
public class AccessTokenService {
	
	private AccessTokenRepository accessToken;
	
	private HashMessage hashMessage;

	@Autowired
	public AccessTokenService(
			AccessTokenRepository accessToken, 
			@Qualifier("TokenHashDigest") HashMessage hash) {
		
		this.accessToken = accessToken;
		this.hashMessage = hash;
	}

	public AuthenticatedInformationResponseEntity createAccessTokenForUser(User user) {
		log.debug("Hash message");
		
		String accessToken = this.hashMessage.hash(user.getName() + "#" + user.getName());
		String clientName = Faker.instance().name().fullName();
		
		log.debug("Client name: " + clientName);
		
		AccessToken token = new AccessToken();
		token.setClientName(clientName);
		token.setCreatedDate(new Date());
		token.setLastestAccessDate(token.getCreatedDate());
		token.setToken(accessToken);
		token.setOwner(user);
		
		token = this.accessToken.save(token);
		
		return new AuthenticatedInformationResponseEntity(token);
	}
}
