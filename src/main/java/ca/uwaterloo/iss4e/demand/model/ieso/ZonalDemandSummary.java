package ca.uwaterloo.iss4e.demand.model.ieso;

import java.util.Date;
import java.util.HashMap;

import org.joda.time.DateTime;

public class ZonalDemandSummary {
	private int wallHourNum;
	private double totalOntarioDemand;
	private double totalZonesDemand;
	private double difference;
	private boolean weekend;
	private Date dateDst;
	private TimeOfUseRate timeOfUseRate;
	private TimeOfUseSeason timeOfUseSeason;
	private HashMap<TransmissionZone, ZonalDemand> zonalDemands;

	public ZonalDemandSummary(Date dstDate, double totalOntarioDemand,
			double totalZonesDemand, double difference,
			HashMap<TransmissionZone, ZonalDemand> zonalDemands) {
		DateTime dateTimeDst = new DateTime(dstDate);
		this.dateDst = dateTimeDst.toDate();

		this.totalOntarioDemand = totalOntarioDemand;
		this.totalZonesDemand = totalZonesDemand;
		this.difference = difference;
		this.wallHourNum = dateTimeDst.getHourOfDay();

		if (dateTimeDst.getDayOfWeek() > 5) {
			this.weekend = true;
		} else {
			this.weekend = false;
		}

		this.timeOfUseSeason = TimeOfUseSeason.valueOfDateTime(dateTimeDst);
		this.timeOfUseRate = TimeOfUseRate.valueOfHour(this.wallHourNum,
				this.timeOfUseSeason, this.weekend);

		this.zonalDemands = zonalDemands;
	}

	public int getWallHourNum() {
		return wallHourNum;
	}

	public void setWallHourNum(int wallHourNum) {
		this.wallHourNum = wallHourNum;
	}

	public double getTotalOntarioDemand() {
		return totalOntarioDemand;
	}

	public void setTotalOntarioDemand(double totalOntarioDemand) {
		this.totalOntarioDemand = totalOntarioDemand;
	}

	public double getTotalZonesDemand() {
		return totalZonesDemand;
	}

	public void setTotalZonesDemand(double totalZonesDemand) {
		this.totalZonesDemand = totalZonesDemand;
	}

	public double getDifference() {
		return difference;
	}

	public void setDifference(double difference) {
		this.difference = difference;
	}

	public boolean isWeekend() {
		return weekend;
	}

	public void setWeekend(boolean weekend) {
		this.weekend = weekend;
	}

	public Date getDateDst() {
		return dateDst;
	}

	public void setDateDst(Date dateDst) {
		this.dateDst = dateDst;
	}

	public TimeOfUseRate getTimeOfUseRate() {
		return timeOfUseRate;
	}

	public void setTimeOfUseRate(TimeOfUseRate timeOfUseRate) {
		this.timeOfUseRate = timeOfUseRate;
	}

	public TimeOfUseSeason getTimeOfUseSeason() {
		return timeOfUseSeason;
	}

	public void setTimeOfUseSeason(TimeOfUseSeason timeOfUseSeason) {
		this.timeOfUseSeason = timeOfUseSeason;
	}

	public HashMap<TransmissionZone, ZonalDemand> getZonalDemands() {
		return zonalDemands;
	}

	public void setZonalDemands(
			HashMap<TransmissionZone, ZonalDemand> zonalDemands) {
		this.zonalDemands = zonalDemands;
	}
}