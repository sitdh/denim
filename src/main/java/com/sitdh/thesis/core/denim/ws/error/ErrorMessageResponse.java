package com.sitdh.thesis.core.denim.ws.error;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ErrorMessageResponse {
	
	private String message;
	
	private String description;
	
	@JsonFormat(locale="th", shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Bangkok")
	private Date timestamp;

}
