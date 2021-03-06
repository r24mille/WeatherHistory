package ca.uwaterloo.iss4e.demand.model.ieso;

import java.util.Date;

import ca.uwaterloo.iss4e.demand.model.DemandAndWeather;

public class ZonalDemandAndWeather extends ZonalDemand implements
		DemandAndWeather {
	private double tempMetric;

	public ZonalDemandAndWeather(double tempMetric, Date dstDate,
			double demand, TransmissionZone transmissionZone) {
		super(dstDate, demand, transmissionZone);
		this.tempMetric = tempMetric;
	}

	public double getTempMetric() {
		return tempMetric;
	}

	public void setTempMetric(double tempMetric) {
		this.tempMetric = tempMetric;
	}
}
