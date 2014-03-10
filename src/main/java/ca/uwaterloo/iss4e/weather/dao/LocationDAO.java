package ca.uwaterloo.iss4e.weather.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class LocationDAO {
	private static DataSource iss4eDataSource;

	public LocationDAO(DataSource dataSource) {
		this.iss4eDataSource = dataSource;
	}

	public int getLocationId(String city, String provinceOrState, String country) {
		String sql = "select location_id from weathertables.location "
				+ "where city = ? and province_state = ? and country = ?";
		JdbcTemplate template = new JdbcTemplate(iss4eDataSource);
		int locationId = template.queryForObject(sql, new Object[] { city,
				provinceOrState, country }, Integer.class);
		return locationId;
	}

}
