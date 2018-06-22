package com.sitdh.thesis.core.denim.database.service;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.time.DateUtils;
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

	public Optional<AuthenticatedInformationResponseEntity> renewForToken(String accessToken, String client) {
		Optional<AccessToken> accToken = this.accessToken.findByTokenAndClientNameAndExpiredDateAfter(accessToken, client, new Date());
		
		Optional<AuthenticatedInformationResponseEntity> authInfoEntity = Optional.ofNullable(null);
		if (accToken.isPresent()) {
			AuthenticatedInformationResponseEntity at = new AuthenticatedInformationResponseEntity(accToken.get());
			authInfoEntity = Optional.ofNullable(at);
		}
		
		return authInfoEntity;
	}
	
	public Optional<AccessToken> accessTokenForUserCredential(String token, String username) {
		
		return this.accessToken
				.findByTokenAndExpiredDateAfter(token, new Date())
				.filter(t -> t.getOwner().getUsername().equals(username));
	}

	public void killAccessToken(AccessToken accessToken) {
		accessToken.setExpiredDate(DateUtils.addDays(new Date(), -30));
		
		this.accessToken.save(accessToken);
	}
}
