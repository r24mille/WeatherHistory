import java.util.Calendar;
import java.util.List;
import java.util.TreeSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.client.RestTemplate;

import ca.uwaterloo.iss4e.demand.dao.ieso.IesoDemandDAO;
import ca.uwaterloo.iss4e.demand.model.ieso.IesoDemand;
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
	static final String city = "Toronto";
	static final String provinceOrState = "ON";
	static final String country = "Canada";

	public static void main(String[] args) throws InterruptedException {
		int locationId = locationDAO.getLocationId(city, provinceOrState,
				country);

		Calendar startCal = Calendar.getInstance();
		startCal.set(2003, Calendar.JANUARY, 2, 0, 0, 0);
		startCal.set(Calendar.MILLISECOND, 0);
		Calendar endCal = Calendar.getInstance();
		endCal.set(2003, Calendar.JANUARY, 4, 23, 59, 59);
		endCal.set(Calendar.MILLISECOND, 0);
		List<IesoDemand> demands = iesoDemandDAO.getDemandForRange(
				startCal.getTime(), endCal.getTime());
		TreeSet<String> dateStrings = new TreeSet<String>();
		for (IesoDemand iesoDemand : demands) {
			dateStrings.add(HistoryDate.formatDateString(iesoDemand
					.getTimestamp()));
		}

		int interMinCount = 0;
		int interDayCount = 0;
		for (String dateString : dateStrings) {
			weatherHistoryQueryAndInsert(apiKey, dateString, locationId);
			interMinCount++;
			interDayCount++;

			if (interMinCount >= 10) {
				Thread.sleep(60000);
				interMinCount = 0;
			}
			if (interDayCount >= 500) {
				break;
			}
		}
	}

	private static void weatherHistoryQueryAndInsert(String apiKey,
			String dateString, int locationId) {
		RestTemplate restTemplate = new RestTemplate();

		try {
			HistoryResponse historyResponse = restTemplate.getForObject(
					"http://api.wunderground.com/api/" + apiKey + "/history_"
							+ dateString + "/q/Canada/Toronto.json",
					HistoryResponse.class);
			System.out.println("Request URL: "
					+ "http://api.wunderground.com/api/" + apiKey + "/history_"
					+ dateString + "/q/" + country + "/" + city + ".json");
			for (Observation observation : historyResponse.getHistory()
					.getObservations()) {
				observation.setTimestamp(observation
						.getJavaDateFromWundergroundDate());
				wundergroundObservationDAO.insertObservation(locationId,
						observation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
