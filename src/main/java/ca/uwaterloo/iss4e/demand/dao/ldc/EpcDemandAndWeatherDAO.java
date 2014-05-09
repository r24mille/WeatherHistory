package ca.uwaterloo.iss4e.demand.dao.ldc;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demand.dao.ldc.mapper.EpcDemandAndWeatherMapper;
import ca.uwaterloo.iss4e.demand.model.ldc.LdcDemandAndWeather;

public class EpcDemandAndWeatherDAO {
	private static DataSource epcDataSource;

	public EpcDemandAndWeatherDAO(DataSource dataSource) {
		this.epcDataSource = dataSource;
	}

	/**
	 * TODO Remove single MeterID TODO Create an aggregate demand table for
	 * summed SmartMeterReadings under consideration
	 * 
	 * 
	 * @param transmissionZone
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<LdcDemandAndWeather> getEpcDemandAndWeather(Date startDate,
			Date endDate) {
		String sql = "select concat(date_format(SmartMeterReadings.read_datetime, '%Y-%m-%d %H:%i:%s'), ' EST') as reading_datetime_with_timezone, "
				+ "SmartMeterReadings.Reading, wunderground_observation.temp_metric "
				+ "from SmartMeterReadings "
				+ "inner join weathertables.wunderground_observation "
				+ "   on wunderground_observation.observation_datetime_standard = SmartMeterReadings.read_datetime "
				+ "where SmartMeterReadings.read_datetime >= ? "
				+ "   and SmartMeterReadings.read_datetime <= ? "
				+ "   and wunderground_observation.location_id = 13 and temp_metric != -9999 "
				+ "   and SmartMeterReadings.MeterID = 39238 "
				+ "order by SmartMeterReadings.read_datetime asc";
		JdbcTemplate template = new JdbcTemplate(epcDataSource);
		List<LdcDemandAndWeather> ldcDemandAndWeathers = template.query(
				sql, new Object[] { startDate, endDate },
				new EpcDemandAndWeatherMapper());

		return ldcDemandAndWeathers;
	}
}
