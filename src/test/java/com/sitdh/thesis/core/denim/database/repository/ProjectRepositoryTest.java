package com.sitdh.thesis.core.denim.database.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sitdh.thesis.core.denim.database.entity.Project;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProjectRepositoryTest {
	
	@Autowired
	private ProjectRepository projectRepo;

	@Test
	void createNewProject() {
		Project project = new Project();
		project.setProjectName("Hello");
		
		Project p = projectRepo.save(project);
		
		assertThat(p.getProjectName(), is("Hello"));
	}

}
