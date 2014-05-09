package ca.uwaterloo.iss4e.demand.model.ieso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public enum TimeOfUseRate {
	OFF_PEAK, MID_PEAK, ON_PEAK;

	/**
	 * Given a DateTime object, this constructor returns the proper
	 * TimeOfUseRate enum.
	 * 
	 * Starting April 1, 2005 the Ontario Energy Board permitted local
	 * distribution companies to implement Time-of-Use pricing. See the
	 * following document:
	 * http://www.ontarioenergyboard.ca/documents/communications
	 * /pressreleases/2005/press_release_110305_bg.pdf
	 * 
	 * On May 1, 2011 the Time-of-Use hours were adjusted by adding two
	 * additional off-peak hours. See the following document:
	 * http://www.ontarioenergyboard
	 * .ca/OEB/_Documents/Press%20Releases/rpp_Backgrounder__20110419.pdf
	 * 
	 * @param dateTimeDst
	 * @param timeOfUseSeason
	 * @param weekend
	 * @return
	 */
	public static TimeOfUseRate valueOfDateTime(DateTime dateTimeDst,
			TimeOfUseSeason timeOfUseSeason, boolean weekend) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormat
				.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime rppStart = dateTimeFormatter
				.parseDateTime("2005-04-01 00:00:00");
		DateTime touHourAdjust = dateTimeFormatter
				.parseDateTime("2011-05-01 00:00:00");

		
		if (dateTimeDst.isBefore(rppStart)) {
			// Everything prior to April 1, 2005 is "off-peak"
			return OFF_PEAK;
		} else if ((dateTimeDst.isEqual(rppStart) || dateTimeDst
				.isAfter(rppStart)) && dateTimeDst.isBefore(touHourAdjust)) {
			// Off-peak starts at 9pm
			if (weekend || dateTimeDst.getHourOfDay() < 7
					|| dateTimeDst.getHourOfDay() >= 21) {
				return OFF_PEAK;
			} else if (dateTimeDst.getHourOfDay() >= 11
					&& dateTimeDst.getHourOfDay() < 17) {
				if (timeOfUseSeason == TimeOfUseSeason.SUMMER) {
					return ON_PEAK; // Summer
				} else {
					return MID_PEAK; // Winter
				}
			} else {
				if (timeOfUseSeason == TimeOfUseSeason.SUMMER) {
					return MID_PEAK; // Summer
				} else {
					return ON_PEAK; // Winter
				}
			}
		}
		else {
			// Off-peak starts at 7pm
			if (weekend || dateTimeDst.getHourOfDay() < 7
					|| dateTimeDst.getHourOfDay() >= 19) {
				return OFF_PEAK;
			} else if (dateTimeDst.getHourOfDay() >= 11
					&& dateTimeDst.getHourOfDay() < 17) {
				if (timeOfUseSeason == TimeOfUseSeason.SUMMER) {
					return ON_PEAK; // Summer
				} else {
					return MID_PEAK; // Winter
				}
			} else {
				if (timeOfUseSeason == TimeOfUseSeason.SUMMER) {
					return MID_PEAK; // Summer
				} else {
					return ON_PEAK; // Winter
				}
			}
		}
	}
}
