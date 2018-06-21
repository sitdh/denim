package com.sitdh.thesis.core.denim.util.hash;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SHAHashMessageTest {
	
	@Autowired @Qualifier("SHADigest")
	private HashMessage hashMessage;
	
	private static final String PASSWORD = "password";
	
	// password@[salt]
	private static final String EXPECTED_SHA_HASH = "133f0972cfe6bef76541dfd029704e4476c8666eda576eefc37bc7c9faf367e7";
	
	@BeforeClass
	public static void environmentSetup() {
		System.setProperty("denim.security.salt", "message-salt");
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void message_should_hash() {
		String digest = this.hashMessage.hash(SHAHashMessageTest.PASSWORD);
		assertThat(digest, is(SHAHashMessageTest.EXPECTED_SHA_HASH));
	}

}
