package com.sitdh.thesis.core.denim.database.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.sitdh.thesis.core.denim.database.entity.Project;
import com.sitdh.thesis.core.denim.database.entity.User;
import com.sitdh.thesis.core.denim.database.repository.ProjectRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {
	
	@Autowired
	private ProjectService projectService;
	
	@MockBean
	private ProjectRepository projectRepo;
	
	private User mockUser;
	
	private List<Project> aProject; //, projects, noProject, otherUserProjects;

	@Before
	public void setUp() throws Exception {
		
		mockUser = new User();
		mockUser.setEmail("email@example.com");
		mockUser.setId(1);
		mockUser.setName("John Doe");
		mockUser.setPassword("password");
		mockUser.setUsername("johnd");
		
		User otherUser = mockUser;
		otherUser.setUsername("janed");
		
		Project p = new Project();
		p.setPid(1);
		p.setProjectName("Lorem ipsum");
		p.setSlug("lorem-ipsum");
		p.setInterestedPackage("com.sitdh");
		p.setLocation("/tmp");
		p.setLastUpdate(new Date());
		p.setCreatedDate(new Date());
		p.setDescription("lorem ipsum dolor sit amet");
		
		Project p2 = p;
		p2.setPid(2);
		p2.setOwner(otherUser);
		
//		noProject = Lists.newArrayList();
		aProject = List.of(p);
//		projects = List.of(p, p2);
//		otherUserProjects = List.of(p2);
	}

	@Test
	public void fetch_project_for_user() {
		when(projectRepo.findAllByOwner(Mockito.any(User.class))).thenReturn(Optional.of(aProject));
		
		Optional<List<Project>> projects = this.projectService.fetchProjectForOwner(mockUser);
		assertTrue(projects.isPresent());
		projects.ifPresent(ps -> {
			assertEquals(1, ps.size());
			
			Project p = ps.get(0);
			assertThat(p.getOwner().getUsername(), is(mockUser.getUsername()));
		});
	}
	
	public void create_project_for_user() {
		
	}

}
