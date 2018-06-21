package com.sitdh.thesis.core.denim.ws.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.sitdh.thesis.core.denim.database.entity.User;
import com.sitdh.thesis.core.denim.database.service.UserService;
import com.sitdh.thesis.core.denim.form.entity.UserEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@BeforeClass
	public static void environmentSetup() {
		System.setProperty("denim.security.salt", "hello");
	}

	@Before
	public void setUp() throws Exception {
		User user = new User();
		user.setId(1);
		user.setName("John Doe");
		user.setUsername("johnd");
		user.setEmail("email@email.com");
		user.setCreatedDate(new Date());
		user.setLastestUpdate(new Date());
		
		when(userService.createNewUser(Mockito.any(UserEntity.class))).thenReturn(Optional.of(user));
	}

	@Test
	public void create_new_user() throws Exception {
		
		this.mockMvc
			.perform(
				post("/account/new")
					.param("username", "johnd")
					.param("password", "password")
					.param("confirmed_password", "password")
					.param("email", "email@email.com")
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.password").doesNotExist())
			.andExpect(jsonPath("$.confirmedPassword").doesNotExist())
			.andExpect(jsonPath("$.username").value("johnd"))
			.andExpect(jsonPath("$.name").value("John Doe"))
			.andExpect(jsonPath("$.email").value("email@email.com"))
			.andExpect(jsonPath("$.created_date").isNotEmpty())
			.andExpect(jsonPath("$.lastest_update").isNotEmpty());
	}
	
	@Test
	public void invalid_data_format_should_not_create() throws Exception {
		this.mockMvc
		.perform(
			post("/account/new")
				.param("username", "jo")
				.param("password", "password")
				.param("confirmed_password", "password")
				.param("email", "email@email.com")
		).andDo(print())
		.andExpect(status().is4xxClientError())
		;
	}

}
