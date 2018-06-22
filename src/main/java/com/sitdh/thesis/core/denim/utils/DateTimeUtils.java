package com.sitdh.thesis.core.denim.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtils {
	
	public static Date addDays(Date date, int days) {
		Instant localDate = date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime()
				.plusDays(days)
				.atZone(ZoneId.systemDefault())
				.toInstant();
		
		return Date.from(localDate);
	}
	
	public static Date addHours(Date date, int hours) {
		Instant localDate = date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime()
				.plusHours(hours)
				.atZone(ZoneId.systemDefault())
				.toInstant();
		
		return Date.from(localDate);
	}

}
