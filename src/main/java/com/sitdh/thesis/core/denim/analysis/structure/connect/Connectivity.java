package com.sitdh.thesis.core.denim.analysis.structure.connect;

import java.io.IOException;
import java.util.Optional;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import com.sitdh.thesis.core.denim.database.entity.FileInformation;

public interface Connectivity {
	
	public void accept(FileInformation fileInfo);
	
	public default Optional<JavaClass> loadClass(String location) {
		JavaClass javaClass = null;
		
		try {
			javaClass = new ClassParser(location).parse();
		} catch (ClassFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return Optional.ofNullable(javaClass);
	}

}
