package ca.uwaterloo.iss4e.demand.model.ieso;

import java.util.Date;

import ca.uwaterloo.iss4e.demand.model.Demand;

public class IesoDemand implements Demand {
	private Date timestamp;
	private double totalMW;
	private double totalOntarioMW;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public double getTotalMW() {
		return totalMW;
	}

	public void setTotalMW(double totalMW) {
		this.totalMW = totalMW;
	}

	public double getTotalOntarioMW() {
		return totalOntarioMW;
	}

	public void setTotalOntarioMW(double totalOntarioMW) {
		this.totalOntarioMW = totalOntarioMW;
	}
}
