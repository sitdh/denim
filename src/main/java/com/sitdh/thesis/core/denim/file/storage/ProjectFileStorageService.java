package com.sitdh.thesis.core.denim.file.storage;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sitdh.thesis.core.denim.file.archive.ArchiveManager;

@Service
@PropertySource("classpath:/graph.yml")
public class ProjectFileStorageService implements StorageService {
	
	private ArchiveManager archiver;
	
	@Autowired
	ProjectFileStorageService(ArchiveManager archiver) {
		this.archiver = archiver;
	}

	@Override
	public Optional<String> store(MultipartFile multipartFile, String projectSlug) {
		
		Optional<String> location = null;
		
		try {
			location = archiver.extract(multipartFile.getInputStream(), projectSlug);
			
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
