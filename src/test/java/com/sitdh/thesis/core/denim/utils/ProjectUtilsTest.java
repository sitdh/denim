package com.sitdh.thesis.core.denim.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProjectUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void special_charactore_should_remove() {
		String name = "Hello, World";
		
		assertThat(ProjectUtils.projectSlug(name), is("hello-world"));
	}
	
	@Test
	public void extream_special_character() {
		String name = "Lor** ip&=1 $^#8- s1t am3t";
		assertThat(ProjectUtils.projectSlug(name), is("lor-ip1-8-s1t-am3t"));
	}

}
