package ca.uwaterloo.iss4e.weather.wunderground.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WundergroundDate {
	private String pretty;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private String tzname;

	public String getPretty() {
		return pretty;
	}

	public void setPretty(String pretty) {
		this.pretty = pretty;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@JsonProperty("mon")
	public int getMonth() {
		return month;
	}

	@JsonProperty("mon")
	public void setMonth(int month) {
		this.month = month;
	}

	@JsonProperty("mday")
	public int getDay() {
		return day;
	}

	@JsonProperty("mday")
	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	@JsonProperty("min")
	public int getMinute() {
		return minute;
	}

	@JsonProperty("min")
	public void setMinute(int minute) {
		this.minute = minute;
	}

	public String getTzname() {
		return tzname;
	}

	public void setTzname(String tzname) {
		this.tzname = tzname;
	}
}
