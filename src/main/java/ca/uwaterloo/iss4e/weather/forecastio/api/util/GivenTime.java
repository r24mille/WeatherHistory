package ca.uwaterloo.iss4e.weather.forecastio.api.util;

import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class GivenTime {
	private static final DateTimeFormatter GIVEN_TIME_FORMATTER = DateTimeFormat
			.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");

	public static String formatDateString(Date date) {
		return GIVEN_TIME_FORMATTER.print(date.getTime());
	}
}
