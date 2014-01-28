package ca.uwaterloo.iss4e.weather.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.weather.dao.mapper.WundergroundObservationMapper;
import ca.uwaterloo.iss4e.weather.wunderground.api.response.Observation;

public class WundergroundObservationDAO {
	public static final String OBSERVATION_STORAGE_TIMEZONE = "EST";
	private static DataSource weathertablesDataSource;

	public WundergroundObservationDAO(DataSource dataSource) {
		this.weathertablesDataSource = dataSource;
	}

	public void insertObservation(int locationId, Observation observation) {
		// Being more specific about the storage of time in MySQL
		// Matching storing everything as EST for ease of working with IESO data
		Date dateWithDST = observation.getDate();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone(OBSERVATION_STORAGE_TIMEZONE));
		String dateStringWithStandardTime = df.format(dateWithDST);

		String sql = "insert into wunderground_observation (location_id, observation_datetime_dst, "
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

		JdbcTemplate template = new JdbcTemplate(weathertablesDataSource);
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
				+ "from wunderground_observation "
				+ "where observation_datetime_standard >= ? and observation_datetime_standard <= ? "
				+ "order by observation_datetime_standard asc";

		JdbcTemplate template = new JdbcTemplate(weathertablesDataSource);
		List<Observation> observations = template.query(sql, new Object[] {
				startDate, endDate }, new WundergroundObservationMapper());
		return observations;
	}
}
