package com.sitdh.thesis.core.denim.file.storage;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	public Optional<String> store(MultipartFile multipartFile, String projectSlug);

	public Optional<String> load(String projectSlug);
}
