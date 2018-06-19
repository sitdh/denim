package com.sitdh.thesis.core.denim.config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DenimAppConfigurationTest {

	@Autowired
	DenimAppConfiguration denimConf;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void nameShouldExist() {
		assertThat(denimConf.getName(), is("Hello"));
	}

}
