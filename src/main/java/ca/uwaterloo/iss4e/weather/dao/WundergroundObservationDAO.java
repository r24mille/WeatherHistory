package ca.uwaterloo.iss4e.weather.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.google.gson.Gson;

import ca.uwaterloo.iss4e.weather.dao.mapper.WundergroundObservationMapper;
import ca.uwaterloo.iss4e.weather.forecastio.api.response.DataPoint;
import ca.uwaterloo.iss4e.weather.forecastio.api.response.GivenTimeResponse;
import ca.uwaterloo.iss4e.weather.wunderground.api.response.Observation;

public class WundergroundObservationDAO {
	public static final String OBSERVATION_STORAGE_TIMEZONE = "EST";
	private static DataSource iss4eDataSource;

	public WundergroundObservationDAO(DataSource dataSource) {
		this.iss4eDataSource = dataSource;
	}

	public void insertObservation(int locationId, Observation observation) {
		// Being more specific about the storage of time in MySQL
		// Matching storing everything as EST for ease of working with IESO data
		Date dateWithDST = observation.getDate();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone(OBSERVATION_STORAGE_TIMEZONE));
		String dateStringWithStandardTime = df.format(dateWithDST);

		String sql = "insert into weathertables.wunderground_observation (location_id, observation_datetime_dst, "
				+ "observation_datetime_standard, observation_timezone, "
				+ "temp_metric, temp_imperial, dewpoint_metric, dewpoint_imperial, humidity, "
				+ "windspeed_metric, windspeed_imperial, wind_gust_metric, wind_gust_imperial, "
				+ "wind_direction_degrees, wind_direction_description, visibility_metric, "
				+ "visibility_imperial, pressure_metric, pressure_imperial, windchill_metric, "
				+ "windchill_imperial, heat_index_metric, heat_index_imperial, "
				+ "precipitation_metric, precipitation_imperial, condition_phrase, icon, fog, "
				+ "rain, snow, hail, thunder, tornado, metar) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(iss4eDataSource);
		template.update(
				sql,
				new Object[] { locationId, observation.getDate(),
						dateStringWithStandardTime,
						OBSERVATION_STORAGE_TIMEZONE,
						observation.getTempMetric(),
						observation.getTempImperial(),
						observation.getDewpointMetric(),
						observation.getDewpointImperial(),
						observation.getHumidity(),
						observation.getWindSpeedMetric(),
						observation.getWindSpeedImperial(),
						observation.getWindGustMetric(),
						observation.getWindGustImperial(),
						observation.getWindDirectionDegrees(),
						observation.getWindDirectionDescription(),
						observation.getVisibilityMetric(),
						observation.getVisibilityImperial(),
						observation.getPressureMetric(),
						observation.getPressureImperial(),
						observation.getWindchillMetric(),
						observation.getWindchillImperial(),
						observation.getHeatindexMetric(),
						observation.getHeatindexImperial(),
						observation.getPrecipitationMetric(),
						observation.getPrecipitationImperial(),
						observation.getConditionPhrase(),
						observation.getIcon(), observation.getFog(),
						observation.getRain(), observation.getSnow(),
						observation.getHail(), observation.getThunder(),
						observation.getTornado(), observation.getMetar() });
	}

	public List<Observation> getObservationRange(Date startDate, Date endDate) {
		String sql = "select concat(date_format(observation_datetime_standard, '%Y-%m-%d %H:%i:%s'), ' ', observation_timezone) as observation_datetime_with_timezone, "
				+ "wunderground_observation.* "
				+ "from weathertables.wunderground_observation "
				+ "where observation_datetime_standard >= ? and observation_datetime_standard <= ? "
				+ "order by observation_datetime_standard asc";

		JdbcTemplate template = new JdbcTemplate(iss4eDataSource);
		List<Observation> observations = template.query(sql, new Object[] {
				startDate, endDate }, new WundergroundObservationMapper());
		return observations;
	}

	/**
	 * Method for inserting a forecast.io {@link DataPoint} as an
	 * {@link Observation} into the database using their common features.
	 * 
	 * @param locationId
	 * @param dataPoint
	 * @param offset
	 *            GMT offset for localized Date
	 */
	public void insertDataPoint(int locationId, GivenTimeResponse givenTimeResponse) {
		int offset = givenTimeResponse.getOffset();
		DataPoint dataPoint = givenTimeResponse.getCurrently();
		
		// Being more specific about the storage of time in MySQL
		// Matching storing everything as EST for ease of working with IESO data
		Date dateWithDST = dataPoint.getDate(offset);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone(OBSERVATION_STORAGE_TIMEZONE));
		String dateStringWithStandardTime = df.format(dateWithDST);

		String sql = "insert into weathertables.wunderground_observation (location_id, observation_datetime_dst, "
				+ "observation_datetime_standard, observation_timezone, "
				+ "temp_metric, humidity, windspeed_metric, "
				+ "wind_direction_degrees, visibility_metric, "
				+ "pressure_metric, condition_phrase, icon, metar) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(iss4eDataSource);
		Gson gson = new Gson();
		template.update(
				sql,
				new Object[] { locationId, dataPoint.getDate(offset),
						dateStringWithStandardTime,
						OBSERVATION_STORAGE_TIMEZONE,
						dataPoint.getTempMetric(), dataPoint.getHumidity(),
						dataPoint.getWindSpeedMetric(),
						dataPoint.getWindDirectionDegrees(),
						dataPoint.getVisibilityMetric(),
						dataPoint.getPressureMetric(), dataPoint.getSummary(),
						dataPoint.getIcon(), "forecast.io sources: " + gson.toJson(givenTimeResponse.getFlags().getSources()) });
	}
}
