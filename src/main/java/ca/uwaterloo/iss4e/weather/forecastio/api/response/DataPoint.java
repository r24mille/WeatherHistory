package ca.uwaterloo.iss4e.weather.forecastio.api.response;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * See additional documentation from forecast.io at
 * https://developer.forecast.io/docs/v2
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataPoint {
	Logger logger = LogManager.getLogger(this.getClass());
	
	private String summary;
	private String icon;
	private String precipitationType;
	private double tempMetric;
	private double apparentTempMetric;
	private double dewPointMetric;
	private double humidity;
	private double windSpeedMetric;
	private double windDirectionDegrees;
	private double visibilityMetric;
	private double cloudCoverPercentage;
	private double pressureMetric;
	private int unixSeconds;

	@JsonIgnore
	public Date getDate(int offset) {
		DateTimeZone zone = DateTimeZone.forOffsetHours(offset);
		long instant = this.unixSeconds * 1000L;
		logger.debug("DateTimeZone: " + zone);
		logger.debug("Instant: " + instant);
		DateTime dateTime = new DateTime(instant, zone);
		return dateTime.toDate();
	}

	@JsonProperty("time")
	public int getUnixSeconds() {
		return unixSeconds;
	}

	@JsonProperty("time")
	public void setUnixSeconds(int unixSeconds) {
		this.unixSeconds = unixSeconds;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@JsonProperty("precipType")
	public String getPrecipitationType() {
		return precipitationType;
	}

	@JsonProperty("precipType")
	public void setPrecipitationType(String precipitationType) {
		this.precipitationType = precipitationType;
	}

	@JsonProperty("temperature")
	public double getTempMetric() {
		return tempMetric;
	}

	@JsonProperty("temperature")
	public void setTempMetric(double tempMetric) {
		this.tempMetric = tempMetric;
	}

	@JsonProperty("apparentTemperature")
	public double getApparentTempMetric() {
		return apparentTempMetric;
	}

	@JsonProperty("apparentTemperature")
	public void setApparentTempMetric(double apparentTempMetric) {
		this.apparentTempMetric = apparentTempMetric;
	}

	@JsonProperty("dewPoint")
	public double getDewPointMetric() {
		return dewPointMetric;
	}

	@JsonProperty("dewPoint")
	public void setDewPointMetric(double dewPointMetric) {
		this.dewPointMetric = dewPointMetric;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	@JsonProperty("windSpeed")
	public double getWindSpeedMetric() {
		return windSpeedMetric;
	}

	@JsonProperty("windSpeed")
	public void setWindSpeedMetric(double windSpeedMetric) {
		this.windSpeedMetric = windSpeedMetric;
	}

	@JsonProperty("windBearing")
	public double getWindDirectionDegrees() {
		return windDirectionDegrees;
	}

	@JsonProperty("windBearing")
	public void setWindDirectionDegrees(double windDirectionDegrees) {
		this.windDirectionDegrees = windDirectionDegrees;
	}

	@JsonProperty("visibility")
	public double getVisibilityMetric() {
		return visibilityMetric;
	}

	@JsonProperty("visibility")
	public void setVisibilityMetric(double visibilityMetric) {
		this.visibilityMetric = visibilityMetric;
	}

	@JsonProperty("cloudCover")
	public double getCloudCoverPercentage() {
		return cloudCoverPercentage;
	}

	@JsonProperty("cloudCover")
	public void setCloudCoverPercentage(double cloudCoverPercentage) {
		this.cloudCoverPercentage = cloudCoverPercentage;
	}
	
	@JsonProperty("pressure")
	public double getPressureMetric() {
		return pressureMetric;
	}

	@JsonProperty("pressure")
	public void setPressureMetric(double pressureMetric) {
		this.pressureMetric = pressureMetric;
	}
}
