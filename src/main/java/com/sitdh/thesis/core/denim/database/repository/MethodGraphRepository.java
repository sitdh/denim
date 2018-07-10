package com.sitdh.thesis.core.denim.database.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sitdh.thesis.core.denim.database.entity.MethodGraph;
import com.sitdh.thesis.core.denim.database.entity.MethodInformation;

@Repository
public interface MethodGraphRepository extends JpaRepository<MethodGraph, Integer> {

	public Optional<MethodGraph> findByMethodNameAndLineNumber(MethodInformation methodInfo, String lineNumber);

}
