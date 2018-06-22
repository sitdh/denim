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
import com.sitdh.thesis.core.denim.database.service.AccessTokenService;
import com.sitdh.thesis.core.denim.database.service.UserService;
import com.sitdh.thesis.core.denim.form.entity.AuthenticatedInformationResponseEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private AccessTokenService accessTokenService;
	
	private User user;
	
	private AuthenticatedInformationResponseEntity authEntity;
	
	@BeforeClass
	public static void environmentSetup() {
		System.setProperty("denim.security.salt", "hello");
	}

	@Before
	public void setUp() throws Exception {
		user = new User();
		user.setId(1);
		user.setName("John Doe");
		user.setUsername("johnd");
		user.setEmail("email@email.com");
		user.setCreatedDate(new Date());
		user.setLastestUpdate(new Date());
		
		authEntity = new AuthenticatedInformationResponseEntity();
		authEntity.setUsername(user.getUsername());
		authEntity.setId(1);
		authEntity.setClientName("Lorem ipsum");
		authEntity.setAccessToken("monkey-snake-rabbit");
		authEntity.setAccessedDate(new Date());
		authEntity.setEmail(user.getEmail());
		authEntity.setExpiredDate(new Date());
	}

	@Test
	public void create_new_user() throws Exception {
		when(userService.createNewUser(Mockito.any())).thenReturn(Optional.of(user));
		
		this.mockMvc
			.perform(
				post("/account/new")
					.param("username", "johnd")
					.param("password", "password")
					.param("confirmed_password", "password")
					.param("email", "email@email.com")
			).andDo(print())
			.andExpect(status().isAccepted())
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
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.title").exists())
		.andExpect(jsonPath("$.description").exists())
		;
	}
	
	@Test
	public void invalid_password_should_not_create() throws Exception {
		this.mockMvc
		.perform(
			post("/account/new")
				.param("username", "johnd")
				.param("password", "password")
				.param("confirmed_password", "another-password")
				.param("email", "email@email.com")
		).andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.title").exists())
		.andExpect(jsonPath("$.description").exists())
		;
	}
	
	@Test
	public void invalid_email_should_not_create() throws Exception {
		this.mockMvc
		.perform(
			post("/account/new")
				.param("username", "johnd")
				.param("password", "password")
				.param("confirmed_password", "password")
				.param("email", "email")
		).andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.title").exists())
		.andExpect(jsonPath("$.description").exists())
		;
	}
	
	@Test
	public void exist_user_should_be_fetch() throws Exception {
		when(userService.getUserFromUsernameAndPassword("johnd", "password"))
			.thenReturn(Optional.of(user));
		
		when(accessTokenService.createAccessTokenForUser(Mockito.any(User.class))).thenReturn(authEntity);
		
		this.mockMvc
		.perform(
			post("/account/auth")
				.param("username", "johnd")
				.param("password", "password")
		).andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.username").value(user.getUsername()))
		.andExpect(jsonPath("$.email").value(user.getEmail()))
		.andExpect(jsonPath("$.access_token").value("monkey-snake-rabbit"))
		.andExpect(jsonPath("$.accessed_date").exists())
		.andExpect(jsonPath("$.client_name").exists())
		.andExpect(jsonPath("$.expired_date").exists())
		.andExpect(jsonPath("$.token").doesNotExist())
		.andExpect(jsonPath("$.tokens").doesNotExist())
		.andExpect(jsonPath("$.project").doesNotExist())
		.andExpect(jsonPath("$.projects").doesNotExist())
		;
	}
	
	@Test
	public void unknown_user_should_be_rised_error_message() throws Exception {
		when(userService.getUserFromUsernameAndPassword("johnd", "password"))
			.thenReturn(Optional.of(user));
		
		when(accessTokenService.createAccessTokenForUser(Mockito.any(User.class))).thenReturn(authEntity);
		
		this.mockMvc
		.perform(
			post("/account/auth")
				.param("username", "janed")
				.param("password", "hello")
		).andDo(print())
		.andExpect(status().isUnauthorized())
		.andExpect(jsonPath("$.title").value("Unauthorized"))
		.andExpect(jsonPath("$.description").isNotEmpty())
		;
	}

}
