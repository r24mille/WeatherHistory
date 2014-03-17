package ca.uwaterloo.iss4e.demand.web.command;

import java.util.ArrayList;
import java.util.List;

public class ZonalDemandCommand {
	private List<String> dayFilters;
	
	public ZonalDemandCommand() {
		dayFilters = new ArrayList<String>(7);
		dayFilters.add("Monday");
		dayFilters.add("Tuesday");
		dayFilters.add("Wednesday");
		dayFilters.add("Thursday");
		dayFilters.add("Friday");
		dayFilters.add("Saturday");
		dayFilters.add("Sunday");
	}

	public List<String> getDayFilters() {
		return dayFilters;
	}

	public void setDayFilters(List<String> dayFilters) {
		this.dayFilters = dayFilters;
	}
}