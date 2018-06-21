package com.sitdh.thesis.core.denim.utils;

public class ProjectUtils {
	
	public static String projectSlug(String projectName) {
		return projectName.toLowerCase().replaceAll("[^\\w\\s]","").replaceAll(" ", "-");
	}

}
