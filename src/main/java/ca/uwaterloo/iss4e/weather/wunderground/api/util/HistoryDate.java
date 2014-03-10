package ca.uwaterloo.iss4e.weather.wunderground.api.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class HistoryDate {
	private static final DateTimeFormatter HISTORY_FORMATTER = DateTimeFormat
			.forPattern("yyyyMMdd");

	public static String formatDateString(Date dateDst) {
		return HISTORY_FORMATTER.print(dateDst.getTime());
	}
}
