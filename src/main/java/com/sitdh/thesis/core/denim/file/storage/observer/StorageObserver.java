package com.sitdh.thesis.core.denim.file.storage.observer;

import java.nio.file.Path;

import com.sitdh.thesis.core.denim.database.entity.Project;

public interface StorageObserver {

	public void observedLocation(Path location, Project project);
	
}
