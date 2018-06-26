package com.sitdh.thesis.core.denim.database.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.sitdh.thesis.core.denim.database.entity.AccessToken;
import com.sitdh.thesis.core.denim.database.entity.User;
import com.sitdh.thesis.core.denim.database.repository.AccessTokenRepository;
import com.sitdh.thesis.core.denim.form.entity.AuthenticatedInformationResponseEntity;
import com.sitdh.thesis.core.denim.util.hash.HashMessage;
import com.sitdh.thesis.core.denim.utils.DateTimeUtils;

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
			
			log.debug("Access token exists");
			
			AccessToken renewedToken = this.updateExpireDate(accToken.get());
			renewedToken = this.accessToken.save(renewedToken);
			
			AuthenticatedInformationResponseEntity at = new AuthenticatedInformationResponseEntity(renewedToken);
			authInfoEntity = Optional.ofNullable(at);
			
		} else {
			log.debug("Access token not found");
		}
		
		return authInfoEntity;
	}
	
	public Optional<AccessToken> accessTokenForUserCredential(String token, String username) {
		log.debug("Get token for user: " + username);
		return this.accessTokenInformation(token)
				.filter(t -> t.getOwner().getUsername().equals(username));
	}
	
	public Optional<AccessToken> accessTokenInformation(String token) {
		return this.accessToken.findByTokenAndExpiredDateAfter(token, new Date());
	}

	public void killAccessToken(AccessToken accessToken) {
		accessToken.setExpiredDate(DateTimeUtils.addDays(new Date(), -30));
		
		this.accessToken.save(accessToken);
	}
	
	private AccessToken updateExpireDate(AccessToken accessToken) {
		Date renewedDate = DateTimeUtils.addDays(accessToken.getExpiredDate(), 3);
		if (DateTimeUtils.compareDate(accessToken.getExpiredDate(), renewedDate) <= 7) {
			accessToken.setExpiredDate(renewedDate);
		}
		
		return accessToken;
	}
}
