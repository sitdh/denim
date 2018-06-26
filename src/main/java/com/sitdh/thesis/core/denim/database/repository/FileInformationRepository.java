package com.sitdh.thesis.core.denim.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sitdh.thesis.core.denim.database.entity.FileInformation;

@Repository
public interface FileInformationRepository extends JpaRepository<FileInformation, Integer> {

}
