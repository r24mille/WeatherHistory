package ca.uwaterloo.iss4e.demand.dao.ieso;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demand.dao.ieso.mapper.ZonalDemandAndWeatherMapper;
import ca.uwaterloo.iss4e.demand.model.ieso.TransmissionZone;
import ca.uwaterloo.iss4e.demand.model.ieso.ZonalDemand;
import ca.uwaterloo.iss4e.demand.model.ieso.ZonalDemandAndWeather;

public class ZonalDemandAndWeatherDAO {
	private static DataSource iss4eDataSource;

	public ZonalDemandAndWeatherDAO(DataSource dataSource) {
		this.iss4eDataSource = dataSource;
	}

	public List<ZonalDemandAndWeather> getZonalDemandAndWeather(
			TransmissionZone transmissionZone, Date startDate, Date endDate) {
		String zoneColumn = "zonal_demand."
				+ transmissionZone.toString().toLowerCase();
		String sql = "select concat(date_format(demand_datetime_standard, '%Y-%m-%d %H:%i:%s'), ' ', demand_timezone) as demand_datetime_with_timezone, "
				+ "hour, " + zoneColumn + ", temp_metric "
				+ "from ontario.zonal_demand "
				+ "inner join weathertables.wunderground_observation on observation_datetime_standard = demand_datetime_standard "
				+ "where demand_datetime_dst >= ? and demand_datetime_dst <= ? "
				+ "and location_id = ? and temp_metric != -9999 "
				+ "order by demand_datetime_dst asc";
		JdbcTemplate template = new JdbcTemplate(iss4eDataSource);
		List<ZonalDemandAndWeather> zonalDemandAndWeathers = template.query(
				sql, new Object[] { startDate, endDate, transmissionZone.getLocationId() },
				new ZonalDemandAndWeatherMapper(transmissionZone, zoneColumn));

		return zonalDemandAndWeathers;
	}
}
