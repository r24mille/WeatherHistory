import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import ca.uwaterloo.iss4e.weather.wunderground.api.response.WundergroundDate;

public class Main {

	public static void main(String[] args) throws ParseException {
		String edtString = "3:00 AM EST on October 26, 2003";
		SimpleDateFormat prettyFormat = new SimpleDateFormat(
				WundergroundDate.PRETTY_DATE_PATTERN);
		Date edtDate = prettyFormat.parse(edtString);
		System.out.println("Parsed Date: " + edtDate);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("EST"));
		System.out.println("Formatted: " + df.format(edtDate));
	}

}
