package ca.uwaterloo.iss4e.demand.model;

import java.util.Date;

import ca.uwaterloo.iss4e.demand.model.ieso.TimeOfUseRate;
import ca.uwaterloo.iss4e.demand.model.ieso.TimeOfUseSeason;

public interface Demand {
	public int getWallHourNum();

	public void setWallHourNum(int wallHourNum);

	public double getDemand();

	public void setDemand(double demand);

	public boolean isWeekend();

	public void setWeekend(boolean weekend);

	public Date getDateDst();

	public void setDateDst(Date dateDst);

	public TimeOfUseRate getTimeOfUseRate();

	public void setTimeOfUseRate(TimeOfUseRate timeOfUseRate);

	public TimeOfUseSeason getTimeOfUseSeason();

	public void setTimeOfUseSeason(TimeOfUseSeason timeOfUseSeason);

	public String getDayOfWeek();

	public void setDayOfWeek(String dayOfWeek);
}
