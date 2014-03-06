package ca.uwaterloo.iss4e.demand.dao.ieso.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demand.model.ieso.ZonalDemand;

public class ZonalDemandMapper implements RowMapper<ZonalDemand> {
	private DateFormat datetimeWithTimezoneFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss z");

	@Override
	public ZonalDemand mapRow(ResultSet rs, int rowNum) throws SQLException {
		ZonalDemand zonalDemand = new ZonalDemand();

		try {
			Date demandDate = datetimeWithTimezoneFormat.parse(rs
					.getString("demand_datetime_with_timezone"));
			// System.out.println(demandDate);
			zonalDemand.setDate(demandDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		zonalDemand.setHourNum(rs.getInt("zonal_demand.hour"));
		zonalDemand.setTotalOntario(rs.getDouble("zonal_demand.total_ontario"));
		zonalDemand.setTotalZones(rs.getDouble("zonal_demand.total_zones"));
		zonalDemand.setDifference(rs.getDouble("zonal_demand.difference"));
		zonalDemand.setNorthWest(rs.getDouble("zonal_demand.northwest"));
		zonalDemand.setNorthEast(rs.getDouble("zonal_demand.northeast"));
		zonalDemand.setOttawa(rs.getDouble("zonal_demand.ottawa"));
		zonalDemand.setEast(rs.getDouble("zonal_demand.east"));
		zonalDemand.setToronto(rs.getDouble("zonal_demand.toronto"));
		zonalDemand.setEssa(rs.getDouble("zonal_demand.essa"));
		zonalDemand.setBruce(rs.getDouble("zonal_demand.bruce"));
		zonalDemand.setSouthWest(rs.getDouble("zonal_demand.southwest"));
		zonalDemand.setNiagara(rs.getDouble("zonal_demand.niagara"));
		zonalDemand.setWest(rs.getDouble("zonal_demand.west"));

		return zonalDemand;
	}

}
