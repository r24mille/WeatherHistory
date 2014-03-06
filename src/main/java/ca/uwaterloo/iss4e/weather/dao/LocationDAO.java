package ca.uwaterloo.iss4e.weather.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class LocationDAO {
	private static DataSource weathertablesDataSource;

	public LocationDAO(DataSource dataSource) {
		this.weathertablesDataSource = dataSource;
	}

	public int getLocationId(String city, String provinceOrState, String country) {
		String sql = "select location_id from location "
				+ "where city = ? and province_state = ? and country = ?";
		JdbcTemplate template = new JdbcTemplate(weathertablesDataSource);
		int locationId = template.queryForObject(sql, new Object[] { city,
				provinceOrState, country }, Integer.class);
		return locationId;
	}

}
