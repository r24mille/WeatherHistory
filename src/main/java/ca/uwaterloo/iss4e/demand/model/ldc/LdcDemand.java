package ca.uwaterloo.iss4e.demand.model.ldc;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ca.uwaterloo.iss4e.demand.model.Demand;
import ca.uwaterloo.iss4e.demand.model.ieso.TimeOfUseRate;
import ca.uwaterloo.iss4e.demand.model.ieso.TimeOfUseSeason;

public class LdcDemand implements Demand {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd HH:mm:ss Z");
	public static final DateTime TOU_BILLING_START = dateTimeFormatter
			.parseDateTime("2011-11-01 00:00:00 -0400"); // Nov 1, 2011 is EDT

	private int wallHourNum;
	private double demand;
	private boolean touBillingActive;
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

		if (dateTimeDst.isBefore(TOU_BILLING_START)) {
			this.touBillingActive = false;
		} else {
			this.touBillingActive = true;
		}

		this.timeOfUseSeason = TimeOfUseSeason.valueOfDateTime(dateTimeDst);
		this.timeOfUseRate = TimeOfUseRate.valueOfDateTime(dateTimeDst,
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

	public boolean isTouBillingActive() {
		return touBillingActive;
	}

	public void setTouBillingActive(boolean touBillingActive) {
		this.touBillingActive = touBillingActive;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
}
