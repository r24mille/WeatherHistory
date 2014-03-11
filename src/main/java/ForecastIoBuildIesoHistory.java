import java.util.Calendar;
import java.util.List;
import java.util.TreeSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.client.RestTemplate;

import ca.uwaterloo.iss4e.demand.dao.ieso.ZonalDemandSummaryDAO;
import ca.uwaterloo.iss4e.demand.model.ieso.TransmissionZone;
import ca.uwaterloo.iss4e.demand.model.ieso.ZonalDemandSummary;
import ca.uwaterloo.iss4e.weather.dao.LocationDAO;
import ca.uwaterloo.iss4e.weather.dao.WundergroundObservationDAO;
import ca.uwaterloo.iss4e.weather.forecastio.api.response.DataPoint;
import ca.uwaterloo.iss4e.weather.forecastio.api.response.GivenTimeResponse;
import ca.uwaterloo.iss4e.weather.forecastio.api.util.GivenTime;

public class ForecastIoBuildIesoHistory {
	static ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	static ZonalDemandSummaryDAO zonalDemandSummaryDAO = (ZonalDemandSummaryDAO) context
			.getBean("zonalDemandSummaryDAO");
	static LocationDAO locationDAO = (LocationDAO) context
			.getBean("locationDAO");
	static WundergroundObservationDAO wundergroundObservationDAO = (WundergroundObservationDAO) context
			.getBean("wundergroundObservationDAO");
	static String forecastIoApiKey = (String) context
			.getBean("forecastIoApiKey");

	// Configure these each run
	static TransmissionZone transmissionZone = TransmissionZone.OTTAWA;

	public static void main(String[] args) throws InterruptedException {
		Calendar startCal = Calendar.getInstance();
		startCal.set(2003, Calendar.OCTOBER, 25, 0, 0, 0);
		startCal.set(Calendar.MILLISECOND, 0);
		Calendar endCal = Calendar.getInstance();
		endCal.set(2013, Calendar.DECEMBER, 31, 23, 59, 59);
		endCal.set(Calendar.MILLISECOND, 0);
		List<ZonalDemandSummary> demandSummaries = zonalDemandSummaryDAO
				.getZonalDemandSummariesInRangeMissingWeatherObservation(
						startCal.getTime(), endCal.getTime(),
						transmissionZone.getLocationId(), 1000);

		TreeSet<String> dateStrings = new TreeSet<String>();
		for (ZonalDemandSummary zonalDemandSummary : demandSummaries) {
			dateStrings.add(GivenTime.formatDateString(zonalDemandSummary
					.getDateDst()));
		}

		int reqCount = 0;
		for (String dateString : dateStrings) {
			givenTimeQueryAndInsert(forecastIoApiKey, dateString,
					transmissionZone);
			reqCount++;

			// Must obey 1000 queries per day according to terms of use
			if (reqCount >= 1000) {
				// Wait 25 hours until next batch
				Thread.sleep(90000000);
				reqCount = 0;
			}
		}
	}

	private static void givenTimeQueryAndInsert(String apiKey,
			String dateString, TransmissionZone transmissionZone) {
		RestTemplate restTemplate = new RestTemplate();

		try {
			GivenTimeResponse givenTimeResponse = restTemplate.getForObject(
					"https://api.forecast.io/forecast/" + apiKey + "/"
							+ transmissionZone.getLocationLatitude() + ","
							+ transmissionZone.getLocationLongitude() + ","
							+ dateString + "?units=ca&exclude=hourly,daily",
					GivenTimeResponse.class);
			System.out.println("Request URL: "
					+ "https://api.forecast.io/forecast/" + apiKey + "/"
					+ transmissionZone.getLocationLatitude() + ","
					+ transmissionZone.getLocationLongitude() + ","
					+ dateString + "?units=ca&exclude=hourly,daily");

			DataPoint currently = givenTimeResponse.getCurrently();
			System.out.println("GivenTime date(DST)="
					+ currently.getDate(givenTimeResponse.getOffset())
					+ ", tempMetric=" + currently.getTempMetric());

			wundergroundObservationDAO.insertDataPoint(
					transmissionZone.getLocationId(), givenTimeResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
