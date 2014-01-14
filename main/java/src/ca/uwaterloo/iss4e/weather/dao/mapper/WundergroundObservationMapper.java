package ca.uwaterloo.iss4e.weather.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.weather.wunderground.api.response.Observation;

public class WundergroundObservationMapper implements RowMapper<Observation> {
	private DateFormat datetimeWithTimezoneFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss z");

	@Override
	public Observation mapRow(ResultSet rs, int rowNum) throws SQLException {
		Observation observation = new Observation();

		try {
			String observationDatetimeString = rs
					.getString("observation_datetime_with_timezone");
			observation.setDate(datetimeWithTimezoneFormat
					.parse(observationDatetimeString));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		observation.setTempMetric(rs
				.getDouble("wunderground_observation.temp_metric"));
		observation.setTempImperial(rs
				.getDouble("wunderground_observation.temp_imperial"));
		observation.setDewpointMetric(rs
				.getDouble("wunderground_observation.dewpoint_metric"));
		observation.setDewpointImperial(rs
				.getDouble("wunderground_observation.dewpoint_imperial"));
		observation.setHumidity(rs
				.getDouble("wunderground_observation.humidity"));
		observation.setWindSpeedMetric(rs
				.getDouble("wunderground_observation.windspeed_metric"));
		observation.setWindSpeedImperial(rs
				.getDouble("wunderground_observation.windspeed_imperial"));
		observation.setWindGustMetric(rs
				.getDouble("wunderground_observation.wind_gust_metric"));
		observation.setWindGustImperial(rs
				.getDouble("wunderground_observation.wind_gust_imperial"));
		observation.setWindDirectionDegrees(rs
				.getDouble("wunderground_observation.wind_direction_degrees"));
		observation
				.setWindDirectionDescription(rs
						.getString("wunderground_observation.wind_direction_description"));
		observation.setVisibilityMetric(rs
				.getDouble("wunderground_observation.visibility_metric"));
		observation.setVisibilityImperial(rs
				.getDouble("wunderground_observation.visibility_imperial"));
		observation.setPressureMetric(rs
				.getDouble("wunderground_observation.pressure_metric"));
		observation.setPressureImperial(rs
				.getDouble("wunderground_observation.pressure_imperial"));
		observation.setWindchillMetric(rs
				.getDouble("wunderground_observation.windchill_metric"));
		observation.setWindchillImperial(rs
				.getDouble("wunderground_observation.windchill_imperial"));
		observation.setHeatindexMetric(rs
				.getDouble("wunderground_observation.heat_index_metric"));
		observation.setHeatindexImperial(rs
				.getDouble("wunderground_observation.heat_index_imperial"));
		observation.setPrecipitationMetric(rs
				.getDouble("wunderground_observation.precipitation_metric"));
		observation.setPrecipitationImperial(rs
				.getDouble("wunderground_observation.precipitation_imperial"));
		observation.setConditionPhrase(rs
				.getString("wunderground_observation.condition_phrase"));
		observation.setIcon(rs.getString("wunderground_observation.icon"));
		observation.setFog(rs.getInt("wunderground_observation.fog"));
		observation.setRain(rs.getInt("wunderground_observation.rain"));
		observation.setSnow(rs.getInt("wunderground_observation.snow"));
		observation.setHail(rs.getInt("wunderground_observation.hail"));
		observation.setThunder(rs.getInt("wunderground_observation.thunder"));
		observation.setTornado(rs.getInt("wunderground_observation.tornado"));

		return observation;
	}
}
