package com.sitdh.thesis.core.denim.ws.controller;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sitdh.thesis.core.denim.ws.error.ErrorMessageResponse;

public interface DefaultServiceController {
	
	public default ResponseEntity<?> responseError(
			String title, 
			String description, 
			HttpHeaders headers,
			HttpStatus httpSttatus) {
		
		return new ResponseEntity<ErrorMessageResponse>(ErrorMessageResponse.builder()
				.title(title)
				.description(description)
				.timestamp(new Date())
				.build(), 
			headers, 
			httpSttatus);
	}

}
