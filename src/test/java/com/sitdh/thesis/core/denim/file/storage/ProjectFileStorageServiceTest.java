package com.sitdh.thesis.core.denim.file.storage;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectFileStorageServiceTest {
	
	@Autowired
	private ProjectFileStorageService projectFileStorage;
	
	private InputStream zipStream;

	@Before
	public void setUp() throws Exception {
		String zipFileLocation = new ClassPathResource("/test.zip").getFile().getAbsolutePath();
		zipStream = new FileInputStream(new File(zipFileLocation));
	}

	@Test
	public void multipartFileStore() throws IOException {
		MultipartFile mf = mock(MultipartFile.class);
		when(mf.getInputStream()).thenReturn(zipStream);
		when(mf.getOriginalFilename()).thenReturn("test.zip");
		
		Optional<String> location = projectFileStorage.store(mf, "ola");
		assertTrue(location.isPresent());
		assertThat(location.get(), is(endsWith("ola")));
	}

}
