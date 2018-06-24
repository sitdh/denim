package com.sitdh.thesis.core.denim.file.storage.validation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TargetFolderValidator {

	public static boolean validate(Path location) {
		try {
			return Files.list(location)
					.limit(5)
					.filter(TargetFolderValidator::targetFolderValidation)
					.findFirst()
					.isPresent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private static boolean targetFolderValidation(Path path) {
		return "target".equals(path.getFileName().toString());
	}
}
