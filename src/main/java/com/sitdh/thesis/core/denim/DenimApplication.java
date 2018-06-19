package com.sitdh.thesis.core.denim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@EntityScan
@SpringBootApplication
@Import({DenimApplicationConfig.class})
public class DenimApplication {

	public static void main(String[] args) {
		SpringApplication.run(DenimApplication.class, args);
	}
}
