package ca.uwaterloo.iss4e.demand.model.ieso;

import java.util.Date;

import org.joda.time.DateTime;

public class ZonalDemand {
	private int estHourNum;
	private int wallHourNum;
	private int zonalDemandId;
	private double demand;
	private boolean weekend;
	private DateTime dateTime;
	private TransmissionZone transmissionZone;
	private TimeOfUseRate timeOfUseRate;
	private TimeOfUseSeason timeOfUseSeason;

	public ZonalDemand(int zonalDemandId, int estHourNum, Date dstDate,
			double demand, String columnName) {
		this.zonalDemandId = zonalDemandId;
		this.estHourNum = estHourNum;
		this.dateTime = new DateTime(dstDate);
		this.wallHourNum = dateTime.getHourOfDay();
		this.demand = demand;
		this.transmissionZone = TransmissionZone.valueOf(columnName
				.toUpperCase());

		if (dateTime.getDayOfWeek() > 5) {
			this.weekend = true;
		} else {
			this.weekend = false;
		}

		this.timeOfUseSeason = TimeOfUseSeason.valueOfDateTime(this.dateTime);
		this.timeOfUseRate = TimeOfUseRate.valueOfHour(this.wallHourNum,
				this.timeOfUseSeason, this.weekend);

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

	public double getDemand() {
		return demand;
	}

	public void setDemand(double demand) {
		this.demand = demand;
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

	public TransmissionZone getTransmissionZone() {
		return transmissionZone;
	}

	public void setTransmissionZone(TransmissionZone transmissionZone) {
		this.transmissionZone = transmissionZone;
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

}