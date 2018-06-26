package com.sitdh.thesis.core.denim.utils;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;

public class FileListingUtils {
	
	public static List<File> listingFile(Path location, Optional<String> extension) {
		
		return Lists.newArrayList(
				FileUtils.iterateFiles(
						location.toFile(), 
						new String[] { extension.orElse("class") }, 
						true)
			)
				.stream()
				.collect(Collectors.toList());
	}

}
