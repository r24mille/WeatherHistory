package ca.uwaterloo.iss4e.demand.model.ieso;

import java.util.Date;
import java.util.HashMap;

import org.joda.time.DateTime;

public class ZonalDemandSummary {
	private int estHourNum;
	private int wallHourNum;
	private int zonalDemandId;
	private double totalOntarioDemand;
	private double totalZonesDemand;
	private double difference;
	private boolean weekend;
	private DateTime dateTime;
	private TimeOfUseRate timeOfUseRate;
	private TimeOfUseSeason timeOfUseSeason;
	private HashMap<TransmissionZone, ZonalDemand> zonalDemands;

	public ZonalDemandSummary(int zonalDemandId, int estHourNum, Date dstDate,
			double totalOntarioDemand, double totalZonesDemand,
			double difference,
			HashMap<TransmissionZone, ZonalDemand> zonalDemands) {
		this.zonalDemandId = zonalDemandId;
		this.estHourNum = estHourNum;
		this.dateTime = new DateTime(dstDate);
		this.totalOntarioDemand = totalOntarioDemand;
		this.totalZonesDemand = totalZonesDemand;
		this.difference = difference;
		this.wallHourNum = dateTime.getHourOfDay();

		if (dateTime.getDayOfWeek() > 5) {
			this.weekend = true;
		} else {
			this.weekend = false;
		}

		this.timeOfUseSeason = TimeOfUseSeason.valueOfDateTime(this.dateTime);
		this.timeOfUseRate = TimeOfUseRate.valueOfHour(this.wallHourNum,
				this.timeOfUseSeason, this.weekend);

		this.zonalDemands = zonalDemands;
	}

	public int getEstHourNum() {
		return estHourNum;
	}

	public void setEstHourNum(int estHourNum) {
		this.estHourNum = estHourNum;
	}

	public int getWallHourNum() {
		return wallHourNum;
	}

	public void setWallHourNum(int wallHourNum) {
		this.wallHourNum = wallHourNum;
	}

	public int getZonalDemandId() {
		return zonalDemandId;
	}

	public void setZonalDemandId(int zonalDemandId) {
		this.zonalDemandId = zonalDemandId;
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

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
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