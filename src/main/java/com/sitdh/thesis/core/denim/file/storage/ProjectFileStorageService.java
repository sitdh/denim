package com.sitdh.thesis.core.denim.file.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sitdh.thesis.core.denim.database.entity.Project;
import com.sitdh.thesis.core.denim.file.archive.ArchiveManager;
import com.sitdh.thesis.core.denim.file.storage.observer.StorageObserver;
import com.sitdh.thesis.core.denim.file.storage.validation.SourcecodeFolderValidator;
import com.sitdh.thesis.core.denim.file.storage.validation.TargetFolderValidator;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@PropertySource("classpath:/graph.yml")
public class ProjectFileStorageService implements StorageService {
	
	private ArchiveManager archiver;
	
	private List<StorageObserver> observers;
	
	@Autowired
	ProjectFileStorageService(
			ArchiveManager archiver, 
			List<StorageObserver> observers) {
		
		this.archiver = archiver;
		this.observers = observers;
	}

	@Override
	public Optional<String> store(@NonNull MultipartFile multipartFile, @NonNull Project project) {
		
		Optional<String> location = null;
		
		try {
			location = archiver.extract(multipartFile.getInputStream(), project.getSlug());
			
			Optional<Path> projectLocation = this.pathListing(location.get())
				.stream()
				.filter(SourcecodeFolderValidator::validate)
				.filter(TargetFolderValidator::validate)
				.findFirst();
			
			location = projectLocation.isPresent() ? Optional.of(projectLocation.get().toAbsolutePath().toString()) : Optional.empty() ;
			log.debug("File was saved");
			
			this.notifyObserver(location, project);
			
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		return location;
	}

	@Override
	public Optional<String> load(String projectSlug) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private List<Path> pathListing(String location) {
		List<Path> paths = Lists.newArrayList();
		
		try {
			paths = Files.list(new File(location).toPath())
					.limit(10)
					.filter(this::excludeMacArchivedFolderFilter)
					.peek(p -> log.debug(p.toString()))
					.collect(Collectors.toList())
					;
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
			
		return paths;
	}
	
	private boolean excludeMacArchivedFolderFilter(Path path) {
		return ! "__MACOSX".equals(path.getFileName().toString());
	}
	
	private void notifyObserver(Optional<String> location, Project project) {
		log.debug("Notify to observers");
		observers.stream().forEach(o -> {
			o.observedLocation(new File(location.get()).toPath(), project);
		});
	}

}
