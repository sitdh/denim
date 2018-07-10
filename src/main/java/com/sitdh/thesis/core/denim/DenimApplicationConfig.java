package com.sitdh.thesis.core.denim;

import java.util.List;

import org.apache.bcel.Const;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@PropertySource("classpath:/graph.yml")
public class DenimApplicationConfig {
	
	@Bean
	public HttpHeaders httpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		return headers;
	}
	
	@Bean
	public List<Short> interestedInvoke() {
		
		return List.of(
				Const.INVOKEDYNAMIC,
				Const.INVOKEINTERFACE);
	}

}
