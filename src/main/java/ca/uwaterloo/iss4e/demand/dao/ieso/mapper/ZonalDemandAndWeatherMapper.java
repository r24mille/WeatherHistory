package ca.uwaterloo.iss4e.demand.dao.ieso.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demand.model.ieso.TransmissionZone;
import ca.uwaterloo.iss4e.demand.model.ieso.ZonalDemandAndWeather;

public class ZonalDemandAndWeatherMapper implements
		RowMapper<ZonalDemandAndWeather> {
	private DateFormat datetimeWithTimezoneFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss z");
	private TransmissionZone transmissionZone;
	private String zoneColumn;

	public ZonalDemandAndWeatherMapper(TransmissionZone transmissionZone,
			String zoneColumn) {
		this.transmissionZone = transmissionZone;
		this.zoneColumn = zoneColumn;
	}

	@Override
	public ZonalDemandAndWeather mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		// TODO Find a better way to parse the Date into correct zone reliably
		Date dstDate = null;
		try {
			dstDate = datetimeWithTimezoneFormat.parse(rs
					.getString("demand_datetime_with_timezone"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double tempMetric = rs
				.getDouble("wunderground_observation.temp_metric");

		return new ZonalDemandAndWeather(tempMetric, dstDate,
				rs.getDouble(zoneColumn), transmissionZone);
	}
}
