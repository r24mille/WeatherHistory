package ca.uwaterloo.iss4e.demand.model.ieso;

import java.util.Date;

public class ZonalDemandAndWeather extends ZonalDemand {
	private double tempMetric;

	public ZonalDemandAndWeather(double tempMetric, Date dstDate, double demand,
			TransmissionZone transmissionZone) {
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
