package com.sitdh.thesis.core.denim.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@EnableConfigurationProperties
@ConfigurationProperties(prefix="denim")
@Component
public class DenimAppConfiguration {

	@Getter @Setter
	private String name;
}
