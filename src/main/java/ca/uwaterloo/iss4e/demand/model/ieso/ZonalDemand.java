package ca.uwaterloo.iss4e.demand.model.ieso;

import java.util.Date;

import org.joda.time.DateTime;

public class ZonalDemand {
	private int wallHourNum;
	private double demand;
	private boolean weekend;
	private Date dateDst;
	private TransmissionZone transmissionZone;
	private TimeOfUseRate timeOfUseRate;
	private TimeOfUseSeason timeOfUseSeason;

	public ZonalDemand(Date dstDate, double demand, String columnName) {
		DateTime dateTimeDst = new DateTime(dstDate);
		this.dateDst = dateTimeDst.toDate();

		this.wallHourNum = dateTimeDst.getHourOfDay();
		this.demand = demand;
		this.transmissionZone = TransmissionZone.valueOf(columnName
				.toUpperCase());

		if (dateTimeDst.getDayOfWeek() > 5) {
			this.weekend = true;
		} else {
			this.weekend = false;
		}

		this.timeOfUseSeason = TimeOfUseSeason.valueOfDateTime(dateTimeDst);
		this.timeOfUseRate = TimeOfUseRate.valueOfHour(this.wallHourNum,
				this.timeOfUseSeason, this.weekend);

	}

	public int getWallHourNum() {
		return wallHourNum;
	}

	public void setWallHourNum(int wallHourNum) {
		this.wallHourNum = wallHourNum;
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

	public Date getDateDst() {
		return dateDst;
	}

	public void setDateDst(Date dateDst) {
		this.dateDst = dateDst;
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