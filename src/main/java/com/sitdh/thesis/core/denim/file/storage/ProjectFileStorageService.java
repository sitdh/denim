package com.sitdh.thesis.core.denim.file.storage;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sitdh.thesis.core.denim.file.archive.ArchiveManager;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@PropertySource("classpath:/graph.yml")
public class ProjectFileStorageService implements StorageService {
	
	private ArchiveManager archiver;
	
	@Autowired
	ProjectFileStorageService(ArchiveManager archiver) {
		this.archiver = archiver;
	}

	@Override
	public Optional<String> store(@NonNull MultipartFile multipartFile,@NonNull String projectSlug) {
		
		Optional<String> location = null;
		
		try {
			location = archiver.extract(multipartFile.getInputStream(), projectSlug);
			log.debug("File was saved");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return location;
	}

	@Override
	public Optional<String> load(String projectSlug) {
		// TODO Auto-generated method stub
		return null;
	}

}
