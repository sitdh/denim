package com.sitdh.thesis.core.denim.ws.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectReceiverServiceControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private InputStream zip;
	
	@Before
	public void setUp() throws Exception {
		String zipFileLocation = new ClassPathResource("/green.zip").getFile().getAbsolutePath();
		zip = new FileInputStream(new File(zipFileLocation));
	}

	public void project_should_crate_with_welled_form_information() throws Exception {
		MockMultipartFile mmf = new MockMultipartFile("green.zip", "", "multipart/form-data", zip);
		mockMvc.perform(
				MockMvcRequestBuilders.multipart("/project/new")
					.file("file", mmf.getBytes())
					.characterEncoding("UTF-8")
					.param("project", "Lorem")
					.param("description", "ipsum")
					.param("pkg", "com.sitdh.demo")
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.slug").value("lorem"))
		.andExpect(jsonPath("$.location").doesNotExist());
	
	}

}
