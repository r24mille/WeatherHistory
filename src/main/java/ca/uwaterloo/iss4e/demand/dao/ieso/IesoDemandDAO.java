package ca.uwaterloo.iss4e.demand.dao.ieso;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demand.dao.ieso.mapper.ZonalDemandSummaryMapper;
import ca.uwaterloo.iss4e.demand.model.ieso.ZonalDemandSummary;

public class IesoDemandDAO {
	private static DataSource iesoDataSource;

	public IesoDemandDAO(DataSource dataSource) {
		this.iesoDataSource = dataSource;
	}

	public List<ZonalDemandSummary> getZonalDemandSummariesForRange(Date startDate, Date endDate) {
		String sql = "select concat(date_format(demand_datetime_standard, '%Y-%m-%d %H:%i:%s'), ' ', demand_timezone) as demand_datetime_with_timezone, "
				+ "zonal_demand_id, hour, total_ontario, total_zones, difference, northwest, northeast, "
				+ "ottawa, east, toronto, essa, bruce, southwest, niagara, west "
				+ "from zonal_demand where demand_datetime_dst >= ? and "
				+ "demand_datetime_dst <= ?";
		JdbcTemplate template = new JdbcTemplate(iesoDataSource);
		List<ZonalDemandSummary> zonalDemandSummaries = template.query(sql, new Object[] {
				startDate, endDate }, new ZonalDemandSummaryMapper());
		return zonalDemandSummaries;
	}
}
