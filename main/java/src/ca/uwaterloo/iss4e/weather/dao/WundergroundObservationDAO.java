package ca.uwaterloo.iss4e.weather.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.weather.wunderground.api.response.Observation;

public class WundergroundObservationDAO {
	private static DataSource weathertablesDataSource;

	public WundergroundObservationDAO(DataSource dataSource) {
		this.weathertablesDataSource = dataSource;
	}

	public void insertObservation(int locationId, Observation observation) {
		String sql = "insert into wunderground_observation (location_id, observation_timestamp, "
				+ "temp_metric, temp_imperial, dewpoint_metric, dewpoint_imperial, humidity, "
				+ "windspeed_metric, windspeed_imperial, wind_gust_metric, wind_gust_imperial, "
				+ "wind_direction_degrees, wind_direction_description, visibility_metric, "
				+ "visibility_imperial, pressure_metric, pressure_imperial, windchill_metric, "
				+ "windchill_imperial, heat_index_metric, heat_index_imperial, "
				+ "precipitation_metric, precipitation_imperial, condition_phrase, icon, fog, "
				+ "rain, snow, hail, thunder, tornado) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(weathertablesDataSource);
		template.update(
				sql,
				new Object[] { locationId, observation.getTimestamp(),
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
						observation.getTornado() });
	}
}
