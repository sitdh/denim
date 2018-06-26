package com.sitdh.thesis.core.denim.file.storage;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.sitdh.thesis.core.denim.database.entity.Project;

public interface StorageService {

	public Optional<String> store(MultipartFile multipartFile, Project project);

	public Optional<String> load(String projectSlug);
}
