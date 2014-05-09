package ca.uwaterloo.iss4e.demand.model.ldc;

import java.util.Date;

import ca.uwaterloo.iss4e.demand.model.DemandAndWeather;

public class LdcDemandAndWeather extends LdcDemand implements
		DemandAndWeather {
	private double tempMetric;

	public LdcDemandAndWeather(double tempMetric, Date dstDate, double demand) {
		super(dstDate, demand);
		this.tempMetric = tempMetric;
	}

	public double getTempMetric() {
		return tempMetric;
	}

	public void setTempMetric(double tempMetric) {
		this.tempMetric = tempMetric;
	}
}
