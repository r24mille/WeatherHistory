package ca.uwaterloo.iss4e.weather.wunderground.api.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryDate {
	private static final SimpleDateFormat historyFormat = new SimpleDateFormat(
			"yyyyMMdd");

	public static String formatDateString(Date date) {
		return historyFormat.format(date);
	}
}
