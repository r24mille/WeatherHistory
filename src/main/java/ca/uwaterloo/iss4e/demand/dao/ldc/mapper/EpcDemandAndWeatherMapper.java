package ca.uwaterloo.iss4e.demand.dao.ldc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demand.model.ldc.LdcDemandAndWeather;

public class EpcDemandAndWeatherMapper implements
		RowMapper<LdcDemandAndWeather> {
	private DateFormat datetimeWithTimezoneFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss z");

	@Override
	public LdcDemandAndWeather mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		// TODO Find a better way to parse the Date into correct zone reliably
		Date dstDate = null;
		try {
			dstDate = datetimeWithTimezoneFormat.parse(rs
					.getString("reading_datetime_with_timezone"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double tempMetric = rs
				.getDouble("wunderground_observation.temp_metric");
		double demand = rs.getDouble("SmartMeterReadings.Reading");

		return new LdcDemandAndWeather(tempMetric, dstDate, demand);
	}

}
