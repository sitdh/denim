package com.sitdh.thesis.core.denim.database.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sitdh.thesis.core.denim.database.entity.Project;
import com.sitdh.thesis.core.denim.database.entity.User;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	public Optional<List<Project>> findAllByOwner(User owner);
	
}
