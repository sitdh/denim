package com.sitdh.thesis.core.denim.database.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.sitdh.thesis.core.denim.database.entity.User;
import com.sitdh.thesis.core.denim.database.repository.UserRepository;
import com.sitdh.thesis.core.denim.form.entity.UserEntity;
import com.sitdh.thesis.core.denim.util.hash.HashMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	@MockBean
	private UserRepository mockUserRepo;
	
	@MockBean @Qualifier("SHADigest")
	private HashMessage mockHashMessage;
	
	@Autowired
	private UserService userService;
	
	private User mockUser;
	
	private UserEntity formUser;

	@Before
	public void setUp() throws Exception {
		
		mockUser = new User();
		mockUser.setEmail("email@example.com");
		mockUser.setId(1);
		mockUser.setName("John Doe");
		mockUser.setPassword("password");
		mockUser.setUsername("johnd");
		
		formUser = new UserEntity();
		formUser.setUsername(mockUser.getUsername());
		formUser.setName(mockUser.getName());
		formUser.setPassword(mockUser.getPassword());
		formUser.setConfirmedPassword(mockUser.getPassword());
		formUser.setEmail(mockUser.getEmail());
		
		when(mockUserRepo.findByUsernameAndPassword("johnd", "hashed-password")).thenReturn(Optional.ofNullable(mockUser));
		when(mockHashMessage.hash("password")).thenReturn("hashed-password");
		when(mockUserRepo.save(Mockito.any(User.class))).thenReturn(mockUser);
	}
	
	@Test
	public void find_user_with_empty() {
		when(mockUserRepo.findAll()).thenReturn(java.util.Collections.emptyList());
		assertTrue(userService.findAll().isPresent());
		assertTrue(userService.findAll().get().isEmpty());
	}
	
	@Test
	public void all_user_should_be_found() {
		
		List<User> users = List.of(mockUser, mockUser);
		when(mockUserRepo.findAll()).thenReturn(users);
		
		assertTrue(userService.findAll().isPresent());
		assertTrue(userService.findAll().get().size() == 2);
	}
	
	@Test
	public void new_user_should_store_with_hash_password() {
		User u = mockUser;
		u.setPassword(this.mockHashMessage.hash(mockUser.getPassword()));
		u.setId(1);
		
		Optional<User> newUser = userService.createNewUser(formUser);
		
		assertTrue(newUser.isPresent());
		assertThat(newUser.get().getPassword(), is(u.getPassword()));
		assertThat(newUser.get().getPassword(), is(not("password")));
	}
	
	@Test
	public void existing_user_should_be_found() {
		
		Optional<User> user = userService.getUserFromUsernameAndPassword("johnd", "password");
		assertTrue(user.isPresent());
		if (user.isPresent()) {
			assertThat(user.get().getEmail(), is("email@example.com"));
		}
	}
	
	@Test
	public void unknown_user_should_not_be_found() {
		Optional<User> user = userService.getUserFromUsernameAndPassword("janed", "password");
		assertFalse(user.isPresent());
	}
	
	@Test
	public void user_existing() {
		when(mockUserRepo.findByUsernameOrEmail("johnd", "email@example.com")).thenReturn(Optional.of(mockUser));
		
		boolean foundResult = userService.isUsernameOrEmailExists("johnd", "email@example.com");
		assertTrue(foundResult);
		
		foundResult = userService.isUsernameOrEmailExists("janed", "email@example.com");
		assertFalse(foundResult);
		
		foundResult = userService.isUsernameOrEmailExists("janed", "mail@example.com");
		assertFalse(foundResult);
	}
	
	@Test
	public void invalid_user_information_should_not_save() {
		formUser.setUsername("a");
		Optional<User> user = this.userService.createNewUser(formUser);
		
		assertFalse(user.isPresent());
	}
	
	@Test
	public void invalid_user_too_long_should_not_save() {
		formUser.setUsername("abcdeabcdeabcdeabcdef");
		Optional<User> user = this.userService.createNewUser(formUser);
		
		assertFalse(user.isPresent());
	}
	
	@Test
	public void invalid_email_should_not_save() {
		formUser.setEmail("hello");
		Optional<User> user = this.userService.createNewUser(formUser);
		
		assertFalse(user.isPresent());
	}

	@Test
	public void invalid_password_not_save() {
		formUser.setEmail("hello");
		Optional<User> user = this.userService.createNewUser(formUser);
		
		assertFalse(user.isPresent());
	}
	
	@Test
	public void confirm_password_mismatch_should_not_save() {
		formUser.setConfirmedPassword("another-password");
		Optional<User> user = this.userService.createNewUser(formUser);
		
		assertFalse(user.isPresent());
	}
}
