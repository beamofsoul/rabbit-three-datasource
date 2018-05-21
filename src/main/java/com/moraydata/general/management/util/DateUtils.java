package com.moraydata.general.management.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	public static LocalDateTime dateToLocalDateTime(Date date) {
	    Instant instant = date.toInstant();
	    ZoneId zone = ZoneId.systemDefault();
	    return LocalDateTime.ofInstant(instant, zone);
	}

	public static LocalDate dateToLocalDate(Date date) {
	    Instant instant = date.toInstant();
	    ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
	    return localDateTime.toLocalDate();
	}

	public static LocalTime dateToLocalTime(Date date) {
	    Instant instant = date.toInstant();
	    ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
	    return localDateTime.toLocalTime();
	}

	public static Date localDateTimeToUdate(LocalDateTime localDateTime) {
	    ZoneId zone = ZoneId.systemDefault();
	    Instant instant = localDateTime.atZone(zone).toInstant();
	    return Date.from(instant);
	}


	public static Date localDateToUdate(LocalDate localDate) {
	    ZoneId zone = ZoneId.systemDefault();
	    Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
	    return Date.from(instant);
	}

	public static Date localTimeToUdate(LocalTime localTime, LocalDate localDate) {
	    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
	    ZoneId zone = ZoneId.systemDefault();
	    Instant instant = localDateTime.atZone(zone).toInstant();
	    return Date.from(instant);
	}
	
	public static LocalDateTime stringToLocalDateTime(String GMT) {
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss", Locale.US);
		Instant instant = formatter.parse(GMT, new ParsePosition(0)).toInstant();
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}
}
