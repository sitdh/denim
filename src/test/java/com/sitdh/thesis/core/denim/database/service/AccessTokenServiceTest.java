package com.sitdh.thesis.core.denim.database.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.sitdh.thesis.core.denim.database.entity.AccessToken;
import com.sitdh.thesis.core.denim.database.entity.User;
import com.sitdh.thesis.core.denim.database.repository.AccessTokenRepository;
import com.sitdh.thesis.core.denim.form.entity.AuthenticatedInformationResponseEntity;
import com.sitdh.thesis.core.denim.util.hash.HashMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccessTokenServiceTest {
	
	@Autowired
	private AccessTokenService accessTokenService;
	
	@MockBean(name="TokenHashDigest")
	private HashMessage tokenHash;
	
	@MockBean
	private AccessTokenRepository accessTokenRepo;
	
	private User user;
	
	private AccessToken accessToken;
	
	@BeforeClass
	public static void environmentSetup()  {
		System.setProperty("denim.security.access-token.salt", "hello-world");
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
		
		when(tokenHash.hash(Mockito.anyString())).thenReturn("hello-world-hashed");
		
		accessToken = new AccessToken();
		accessToken.setAti(1);
		accessToken.setClientName("Lorem Ipsum");
		accessToken.setToken("ant-horse-rock");
		accessToken.setCreatedDate(new Date());
		accessToken.setExpiredDate(new Date());
		accessToken.setOwner(user);
		
		
	}
	
	public void create_access_token_for_authenticated_user() {
		AuthenticatedInformationResponseEntity airEntity = this.accessTokenService.createAccessTokenForUser(user);
		
		assertThat(airEntity.getUsername(), is("johnd"));
		assertThat(airEntity.getEmail(), is("email@example.com"));
		assertThat(airEntity.getAccessToken(), is("hello-world-hashed"));
		assertTrue(airEntity.getClientName() != null);
	}
	
	@Test
	public void get_access_token_from_user() {
		when(this.accessTokenRepo.findByTokenAndExpiredDateAfter(Mockito.anyString(), Mockito.any(Date.class)))
			.thenReturn(Optional.ofNullable(accessToken));
		
		Optional<AccessToken> t = this.accessTokenService
				.accessTokenForUserCredential("lorem-ipsum-dolor", accessToken.getOwner().getUsername());
		
		assertTrue(t.isPresent());
	}
	
	@Test
	public void unauthorized_user_should_not_get_access_token() {
		when(this.accessTokenRepo.findByTokenAndExpiredDateAfter(Mockito.anyString(), Mockito.any(Date.class)))
			.thenReturn(Optional.ofNullable(null));
		
		Optional<AccessToken> t = this.accessTokenService
				.accessTokenForUserCredential("lorem-ipsum-dolor", accessToken.getOwner().getUsername());
		
		assertFalse(t.isPresent());
	}
	
	@Test
	public void authorized_but_not_owner() {
		accessToken.getOwner().setUsername("other-user");
		when(this.accessTokenRepo.findByTokenAndExpiredDateAfter(Mockito.anyString(), Mockito.any(Date.class)))
			.thenReturn(Optional.ofNullable(accessToken));
		
		Optional<AccessToken> t = this.accessTokenService
				.accessTokenForUserCredential("lorem-ipsum-dolor", "johnd");
		
		assertFalse(t.isPresent());
	}

}
