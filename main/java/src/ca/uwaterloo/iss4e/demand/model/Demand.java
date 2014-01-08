package ca.uwaterloo.iss4e.demand.model;

import java.util.Date;

public interface Demand {
	public Date getTimestamp();

	public void setTimestamp(Date timestamp);

	public double getTotalMW();

	public void setTotalMW(double totalMW);
}
