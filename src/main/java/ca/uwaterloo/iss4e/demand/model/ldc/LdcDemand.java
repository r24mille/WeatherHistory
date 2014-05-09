package ca.uwaterloo.iss4e.demand.model.ldc;

import java.util.Date;

import org.joda.time.DateTime;

import ca.uwaterloo.iss4e.demand.model.Demand;
import ca.uwaterloo.iss4e.demand.model.ieso.TimeOfUseRate;
import ca.uwaterloo.iss4e.demand.model.ieso.TimeOfUseSeason;

public class LdcDemand implements Demand {
	private int wallHourNum;
	private double demand;
	private boolean weekend;
	private Date dateDst;
	private TimeOfUseRate timeOfUseRate;
	private TimeOfUseSeason timeOfUseSeason;
	private String dayOfWeek;

	public LdcDemand(Date dstDate, double demand) {
		DateTime dateTimeDst = new DateTime(dstDate);
		this.dateDst = dateTimeDst.toDate();

		this.wallHourNum = dateTimeDst.getHourOfDay();
		this.demand = demand;

		if (dateTimeDst.getDayOfWeek() > 5) {
			this.weekend = true;
		} else {
			this.weekend = false;
		}

		this.timeOfUseSeason = TimeOfUseSeason.valueOfDateTime(dateTimeDst);
		this.timeOfUseRate = TimeOfUseRate.valueOfHour(this.wallHourNum,
				this.timeOfUseSeason, this.weekend);

		this.dayOfWeek = dateTimeDst.dayOfWeek().getAsText();
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

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
}
