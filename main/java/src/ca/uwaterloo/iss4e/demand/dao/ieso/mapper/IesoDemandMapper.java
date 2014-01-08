package ca.uwaterloo.iss4e.demand.dao.ieso.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demand.model.ieso.IesoDemand;

public class IesoDemandMapper implements RowMapper<IesoDemand> {

	public IesoDemand mapRow(ResultSet rs, int rowNum) throws SQLException {
		IesoDemand iesoDemand = new IesoDemand();
		iesoDemand.setTimestamp(rs.getTimestamp("Hourly_Demand.timestamp"));
		iesoDemand.setTotalMW(rs.getDouble("Hourly_Demand.total"));
		iesoDemand.setTotalOntarioMW(rs.getDouble("Hourly_Demand.ontario"));
		return iesoDemand;
	}
}
