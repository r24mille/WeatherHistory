import java.util.Calendar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ca.uwaterloo.iss4e.weather.dao.WundergroundObservationDAO;

public class QueryWundergroundObservationRange {
	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	static WundergroundObservationDAO wundergroundObservationDAO = (WundergroundObservationDAO) context
			.getBean("wundergroundObservationDAO");

	public static void main(String[] args) {
		Calendar startCal = Calendar.getInstance();
		startCal.set(2010, Calendar.MARCH, 14, 0, 0, 0);
		startCal.set(Calendar.MILLISECOND, 0);
		Calendar endCal = Calendar.getInstance();
		endCal.set(2010, Calendar.MARCH, 14, 23, 59, 59);
		endCal.set(Calendar.MILLISECOND, 0);

		wundergroundObservationDAO
				.getObservationRange(startCal.getTime(), endCal.getTime());
	}
}
