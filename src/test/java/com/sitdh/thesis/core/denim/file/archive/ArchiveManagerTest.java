package com.sitdh.thesis.core.denim.file.archive;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource("classpath:/graph.properties")
public class ArchiveManagerTest {
	
	@Autowired
	private ArchiveManager unzip;
	
	@Value("${denim.project.workspace}")
	private String workspace;
	
	String classPathResource;
	
	String zipFileLocation = "";
	
	InputStream zipFile = null;
	
	InputStream tarFile = null;

	@Before
	public void setUp() throws Exception {
		zipFileLocation = new ClassPathResource("/test.zip").getFile().getAbsolutePath();
		File f = new File(zipFileLocation);
		zipFile = new FileInputStream(f); 
		
		classPathResource = new ClassPathResource("/test.tar.bz2").getFile().getAbsolutePath();
		f = new File(classPathResource);
		tarFile = new FileInputStream(f); 
	}
	
	@After
	public void tearDown() throws IOException {
		FileUtils.deleteDirectory(new File(workspace));
	}
	
	@Test
	public void customStream() throws FileNotFoundException {
		Optional<String> location = unzip.extract(zipFileLocation, "hello");
		
		assertTrue(location.isPresent());
		assertTrue(location.get().endsWith("hello"));
	}
	
	@Test
	public void inputStreamShouldBeExtracted() throws FileNotFoundException {
		Optional<String> location = unzip.extract(new FileInputStream(new File(zipFileLocation)), "hello");
		
		assertTrue(location.isPresent());
		assertTrue(location.get().endsWith("hello"));
	}

}
