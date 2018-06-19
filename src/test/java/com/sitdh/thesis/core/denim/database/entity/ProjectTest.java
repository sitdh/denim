package com.sitdh.thesis.core.denim.database.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sitdh.thesis.core.denim.database.repository.ProjectRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectTest {
	
	@Autowired
	private ProjectRepository projectRepo;
	
	private Project project;

	@Before
	public void setUp() throws Exception {
		project = new Project();
		project.setProjectName("Hello");
	}
	
	@After
	public void teardown() {
		projectRepo.deleteAll();
	}

	@Test
	public void canStoreProject() {
		
		
		Project newProject = projectRepo.save(project);
		
		assertThat(newProject.getProjectName(), is("Hello"));
		assertTrue(newProject.getPid() > 0);
	}
	
	@Test
	public void timeShouldset() {
		Project newProject = projectRepo.save(project);
		
		assertTrue(newProject.getCreatedDate() != null);
		assertTrue(newProject.getLastUpdate() != null);
	}

}
