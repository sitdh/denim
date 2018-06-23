package com.sitdh.thesis.core.denim.database.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sitdh.thesis.core.denim.database.entity.AccessToken;
import com.sitdh.thesis.core.denim.database.entity.User;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Integer> {

	public Optional<AccessToken> findByTokenAndClientNameAndExpiredDateAfter(String token, String clientName, Date currentDate);
	
	public Optional<AccessToken> findByTokenAndExpiredDateAfter(String token, Date currentDate);
	
	public Optional<AccessToken> findByTokenAndOwnerAndExpiredDateAfter(String token, User user, Date currentDate);
	
}
