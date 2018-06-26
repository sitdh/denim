package com.sitdh.thesis.core.denim.database.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sitdh.thesis.core.denim.database.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findByUsernameAndPassword(String username, String password);
	
	public Optional<User> findByUsernameOrEmail(String username, String email);
	
	public Optional<User> findByUsername(String username);

}
