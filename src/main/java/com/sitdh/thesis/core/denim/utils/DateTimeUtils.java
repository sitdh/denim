package com.sitdh.thesis.core.denim.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtils {
	
	public static Date addDays(Date date, int days) {
		Instant localDateTime = date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime()
				.plusDays(days)
				.atZone(ZoneId.systemDefault())
				.toInstant()
				;
		
		return Date.from(localDateTime);
	}
	
	public static Date addHours(Date date, int hours) {
		Instant localDateTime = date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime()
				.plusHours(hours)
				.atZone(ZoneId.systemDefault())
				.toInstant()
				;
		
		return Date.from(localDateTime);
	}
	
	public static int compareDate(Date from, Date to) {
		
		LocalDateTime fromLocalDate = from.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
		LocalDateTime toLocalDate = to.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
		
		Duration dateDiff = Duration.between(fromLocalDate, toLocalDate);
		
		return (int) dateDiff.toDays();
	}

}
