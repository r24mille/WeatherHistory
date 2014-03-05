import java.util.Calendar;
import java.util.List;
import java.util.TreeSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.client.RestTemplate;

import ca.uwaterloo.iss4e.demand.dao.ieso.IesoDemandDAO;
import ca.uwaterloo.iss4e.demand.model.ieso.ZonalDemand;
import ca.uwaterloo.iss4e.weather.dao.LocationDAO;
import ca.uwaterloo.iss4e.weather.dao.WundergroundObservationDAO;
import ca.uwaterloo.iss4e.weather.wunderground.api.response.HistoryResponse;
import ca.uwaterloo.iss4e.weather.wunderground.api.response.Observation;
import ca.uwaterloo.iss4e.weather.wunderground.api.util.HistoryDate;

public class BuildIesoHistory {
	static ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	static IesoDemandDAO iesoDemandDAO = (IesoDemandDAO) context
			.getBean("iesoDemandDAO");
	static LocationDAO locationDAO = (LocationDAO) context
			.getBean("locationDAO");
	static WundergroundObservationDAO wundergroundObservationDAO = (WundergroundObservationDAO) context
			.getBean("wundergroundObservationDAO");
	static String apiKey = (String) context.getBean("apiKey");

	// Configure these each run
	static final String city = "Niagara Falls";
	static final String provinceOrState = "ON";
	static final String country = "Canada";

	public static void main(String[] args) throws InterruptedException {
		int locationId = locationDAO.getLocationId(city, provinceOrState,
				country);

		Calendar startCal = Calendar.getInstance();
		startCal.set(2003, Calendar.JANUARY, 1, 0, 0, 0);
		startCal.set(Calendar.MILLISECOND, 0);
		Calendar endCal = Calendar.getInstance();
		endCal.set(2013, Calendar.DECEMBER, 31, 23, 59, 59);
		endCal.set(Calendar.MILLISECOND, 0);
		List<ZonalDemand> demands = iesoDemandDAO.getZonalDemandRange(
				startCal.getTime(), endCal.getTime());
		TreeSet<String> dateStrings = new TreeSet<String>();
		for (ZonalDemand zonalDemand : demands) {
			dateStrings
					.add(HistoryDate.formatDateString(zonalDemand.getDate()));
		}

		int interMinCount = 0;
		int interDayCount = 0;
		for (String dateString : dateStrings) {
			weatherHistoryQueryAndInsert(apiKey, dateString, locationId);
			interMinCount++;
			interDayCount++;

			// Must obey 10 queries per minute, 500 queries per day API key
			// terms-of-use
			if (interMinCount >= 10) {
				// The 10 queries will finish fairly quickly. Just wait a minute
				// for simplicity
				Thread.sleep(60000);
				interMinCount = 0;
			}
//			if (interDayCount >= 500) {
//				// 500 queries / 10 query batches = 50 batches
//				// 50 batches * 60 seconds = 3000 seconds for all batches
//				// 86400 seconds - 3000 seconds = 83400 seconds necessary to
//				// obey daily limit
//				// 83400 seconds = 83400000 milliseconds
//				Thread.sleep(83400000);
//				interDayCount = 0;
//			}
		}
	}

	private static void weatherHistoryQueryAndInsert(String apiKey,
			String dateString, int locationId) {
		RestTemplate restTemplate = new RestTemplate();

		String cityForURL = city.replaceAll(" ", "_");
		try {
			HistoryResponse historyResponse = restTemplate.getForObject(
					"http://api.wunderground.com/api/" + apiKey + "/history_"
							+ dateString + "/q/" + country + "/" + cityForURL + ".json",
					HistoryResponse.class);
			System.out.println("Request URL: "
					+ "http://api.wunderground.com/api/" + apiKey + "/history_"
					+ dateString + "/q/" + country + "/" + cityForURL + ".json");
			for (Observation observation : historyResponse.getHistory()
					.getObservations()) {
				observation.setDate(observation
						.getJavaDateFromWundergroundDate());
				
				/* METAR observations are scheduled observations http://www.wunderground.com/metarFAQ.asp
				 * and should prevent duplicates on the hour from separate stations 
				 * and unneeded inter-hour observations.
				 */
				if (observation.getMetar().startsWith("METAR")) {
					wundergroundObservationDAO.insertObservation(locationId,
							observation);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
