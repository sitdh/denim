package com.sitdh.thesis.core.denim.util.hash;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component("GravatarHash")
public class GravatarEmailHashMessage implements HashMessage {

	@Override
	public String hash(String message) {
		return DigestUtils.md5Hex(message.toLowerCase().trim());
	}

}
