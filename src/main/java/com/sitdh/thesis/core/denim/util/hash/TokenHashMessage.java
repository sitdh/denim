package com.sitdh.thesis.core.denim.util.hash;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("TokenHashDigest")
@PropertySource("classpath:/graph.properties")
public class TokenHashMessage implements HashMessage {
	
	@Value("${denim.security.access-token.salt}")
	private String salt;

	@Override
	public String hash(String message) {
		
		Date date = new Date();
		
		return DigestUtils.sha512Hex("=" + message + date.toString() + "@{" + salt + "}");
	}

}
