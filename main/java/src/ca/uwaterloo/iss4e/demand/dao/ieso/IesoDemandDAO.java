package ca.uwaterloo.iss4e.demand.dao.ieso;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demand.dao.ieso.mapper.IesoDemandMapper;
import ca.uwaterloo.iss4e.demand.model.ieso.IesoDemand;

public class IesoDemandDAO {
	private static DataSource iesoDataSource;

	public IesoDemandDAO(DataSource dataSource) {
		this.iesoDataSource = dataSource;
	}

	public List<IesoDemand> getDemandForYear(int year) {
		String sql = "select timestamp, total, ontario from ontario.Hourly_Demand "
				+ "where year(timestamp) = ? order by timestamp asc";
		JdbcTemplate template = new JdbcTemplate(iesoDataSource);
		List<IesoDemand> demands = template.query(sql, new Object[] { year },
				new IesoDemandMapper());
		return demands;
	}

	public List<IesoDemand> getDemandForRange(Date startDate, Date endDate) {
		String sql = "select timestamp, total, ontario from ontario.Hourly_Demand "
				+ "where timestamp >= ? and timestamp <= ? order by timestamp asc";
		JdbcTemplate template = new JdbcTemplate(iesoDataSource);
		List<IesoDemand> demands = template.query(sql, new Object[] { startDate, endDate },
				new IesoDemandMapper());
		return demands;
	}
}
