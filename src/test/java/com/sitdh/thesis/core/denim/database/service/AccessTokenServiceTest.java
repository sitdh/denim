package com.sitdh.thesis.core.denim.database.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.sitdh.thesis.core.denim.database.entity.User;
import com.sitdh.thesis.core.denim.form.entity.AuthenticatedInformationResponseEntity;
import com.sitdh.thesis.core.denim.util.hash.HashMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccessTokenServiceTest {
	
	@Autowired
	private AccessTokenService accessTokenService;
	
	@MockBean(name="TokenHashDigest")
	private HashMessage tokenHash;
	
	private User user;
	
	@BeforeClass
	public static void environmentSetup()  {
		System.setProperty("denim.security.access-token.salt", "hello-world");
	}

	@Before
	public void setUp() throws Exception {
		user = mock(User.class);
		when(user.getId()).thenReturn(1);
		when(user.getUsername()).thenReturn("johnd");
		when(user.getEmail()).thenReturn("email@example.com");
		when(user.getName()).thenReturn("John Doe");
		
		when(tokenHash.hash(Mockito.anyString())).thenReturn("hello-world-hashed");
	}

	@Test
	public void create_access_token_for_authenticated_user() {
		AuthenticatedInformationResponseEntity airEntity = this.accessTokenService.createAccessTokenForUser(user);
		
		assertThat(airEntity.getUsername(), is("johnd"));
		assertThat(airEntity.getEmail(), is("email@example.com"));
		assertThat(airEntity.getAccessToken(), is("hello-world-hashed"));
		assertTrue(airEntity.getClientName() != null);
	}

}
