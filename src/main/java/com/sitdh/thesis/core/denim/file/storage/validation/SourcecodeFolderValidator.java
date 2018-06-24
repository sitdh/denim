package com.sitdh.thesis.core.denim.file.storage.validation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SourcecodeFolderValidator {

	public static boolean validate(Path location) {
		try {
			return Files.list(location)
					.limit(10)
					.filter(SourcecodeFolderValidator::sourceCodeFolderValidator)
					.findFirst()
					.isPresent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private static boolean sourceCodeFolderValidator(Path path) {
		return path.toAbsolutePath().toString().endsWith("src");
	}

}
