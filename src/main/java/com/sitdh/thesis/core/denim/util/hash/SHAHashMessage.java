package com.sitdh.thesis.core.denim.util.hash;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("SHADigest")
@PropertySource("classpath:/graph.properties")
public class SHAHashMessage implements HashMessage {
	
	private String salt;
	
	public SHAHashMessage(@Value("${denim.security.salt}") String salt) {
		this.salt = salt;
	}

	@Override
	public String hash(String message) {
		return DigestUtils.sha256Hex(message + "@[" + salt + "]");
	}

}
