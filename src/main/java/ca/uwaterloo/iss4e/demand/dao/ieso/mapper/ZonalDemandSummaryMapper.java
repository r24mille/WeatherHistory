package ca.uwaterloo.iss4e.demand.dao.ieso.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demand.model.ieso.TransmissionZone;
import ca.uwaterloo.iss4e.demand.model.ieso.ZonalDemand;
import ca.uwaterloo.iss4e.demand.model.ieso.ZonalDemandSummary;

public class ZonalDemandSummaryMapper implements RowMapper<ZonalDemandSummary> {
	private DateFormat datetimeWithTimezoneFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss z");

	@Override
	public ZonalDemandSummary mapRow(ResultSet rs, int rowNum)
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

		double totalOntarioDemand = rs.getDouble("zonal_demand.total_ontario");
		double totalZonesDemand = rs.getDouble("zonal_demand.total_zones");
		double difference = rs.getDouble("zonal_demand.difference");

		HashMap<TransmissionZone, ZonalDemand> zonalDemands = new HashMap<TransmissionZone, ZonalDemand>(
				10);

		zonalDemands.put(TransmissionZone.NORTHWEST, new ZonalDemand(dstDate,
				rs.getDouble("zonal_demand.northwest"),
				TransmissionZone.NORTHWEST));
		zonalDemands.put(TransmissionZone.NORTHEAST, new ZonalDemand(dstDate,
				rs.getDouble("zonal_demand.northeast"),
				TransmissionZone.NORTHEAST));
		zonalDemands.put(TransmissionZone.OTTAWA,
				new ZonalDemand(dstDate, rs.getDouble("zonal_demand.ottawa"),
						TransmissionZone.OTTAWA));
		zonalDemands.put(TransmissionZone.EAST,
				new ZonalDemand(dstDate, rs.getDouble("zonal_demand.east"),
						TransmissionZone.EAST));
		zonalDemands.put(TransmissionZone.TORONTO,
				new ZonalDemand(dstDate, rs.getDouble("zonal_demand.toronto"),
						TransmissionZone.TORONTO));
		zonalDemands.put(TransmissionZone.ESSA,
				new ZonalDemand(dstDate, rs.getDouble("zonal_demand.essa"),
						TransmissionZone.ESSA));
		zonalDemands.put(TransmissionZone.BRUCE,
				new ZonalDemand(dstDate, rs.getDouble("zonal_demand.bruce"),
						TransmissionZone.BRUCE));
		zonalDemands.put(TransmissionZone.SOUTHWEST, new ZonalDemand(dstDate,
				rs.getDouble("zonal_demand.southwest"),
				TransmissionZone.SOUTHWEST));
		zonalDemands.put(TransmissionZone.NIAGARA,
				new ZonalDemand(dstDate, rs.getDouble("zonal_demand.niagara"),
						TransmissionZone.NIAGARA));
		zonalDemands.put(TransmissionZone.WEST,
				new ZonalDemand(dstDate, rs.getDouble("zonal_demand.west"),
						TransmissionZone.WEST));

		ZonalDemandSummary zonalDemandSummary = new ZonalDemandSummary(dstDate,
				totalOntarioDemand, totalZonesDemand, difference, zonalDemands);

		return zonalDemandSummary;
	}
}
