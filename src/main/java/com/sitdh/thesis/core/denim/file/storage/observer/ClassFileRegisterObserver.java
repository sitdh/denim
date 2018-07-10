package com.sitdh.thesis.core.denim.file.storage.observer;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sitdh.thesis.core.denim.analysis.collector.DataCollectionManager;
import com.sitdh.thesis.core.denim.database.entity.FileInformation;
import com.sitdh.thesis.core.denim.database.entity.Project;
import com.sitdh.thesis.core.denim.database.service.FileInformationService;
import com.sitdh.thesis.core.denim.utils.FileListingUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClassFileRegisterObserver implements StorageObserver {
	
	private FileInformationService fileIinfoService;
	
	private DataCollectionManager dataCollectionManager;
	
	@Autowired
	public ClassFileRegisterObserver(
			FileInformationService fileIinfoService,
			DataCollectionManager dataCollectionManager
			) {
		
		this.fileIinfoService = fileIinfoService;
		this.dataCollectionManager = dataCollectionManager;
	}

	@Override
	public void observedLocation(Path location, Project project) {
		
		log.debug("Location entered");
		FileListingUtils.listingFile(location, Optional.of("class"))
		.stream()
		.forEach(f -> {
			log.debug("File location: " + f.getName());
			FileInformation fileInfo = new FileInformation();
			
			fileInfo.setClassName(
					f.getName()
						.toString()
						.replace(".class", "")
					);
			fileInfo.setLocation(f.getAbsolutePath());
			this.javaSourceFile(f.toPath()).ifPresent(p -> {
				fileInfo.setSourceLocation(p.toFile().getAbsolutePath());
			});
			
			String path = this.extractPackageName(f.getAbsolutePath(), f.getName());
			fileInfo.setPackageName(path);
			
			log.debug("Send to collector manager");
			this.dataCollectionManager.accept(
					this.fileIinfoService
						.addFileToProject(fileInfo, project)
					);
		});
		
	}
	
	private String extractPackageName(String absolutePath, String filename) {
		return absolutePath.substring(
				absolutePath.indexOf("target/classes"),
				absolutePath.length())
			.replace("target/classes/", "")
			.replace("/" + filename, "")
			.replaceAll("/", ".")
			;
	}
	
	private Optional<Path> javaSourceFile(Path classPath) {
		Optional<Path> path = Optional.ofNullable(null);
		String location = classPath.toString()
				.replaceAll("target/classes", "src/main/java")
				.replaceAll(".class", ".java")
				;
		
		File sourceFile = new File(location);
		if (sourceFile.exists() && sourceFile.isFile()) {
			path = Optional.ofNullable(sourceFile.toPath());
		}
		
		return path;
	}

}
