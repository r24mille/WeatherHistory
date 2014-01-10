package ca.uwaterloo.iss4e.weather.wunderground.api.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * See additional documentation from Weather Underground API at
 * http://www.wunderground
 * .com/weather/api/d/docs?d=resources/phrase-glossary&MR=1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Observation {
	private Date date;
	private WundergroundDate wundergroundDate;
	private WundergroundDate wundergroundUtcDate;
	private double tempMetric;
	private double tempImperial;
	private double dewpointMetric;
	private double dewpointImperial;
	private double humidity;
	private double windSpeedMetric;
	private double windSpeedImperial;
	private double windGustMetric;
	private double windGustImperial;
	private double windDirectionDegrees;
	private String windDirectionDescription;
	private double visibilityMetric;
	private double visibilityImperial;
	private double pressureMetric;
	private double pressureImperial;
	private double windchillMetric;
	private double windchillImperial;
	private double heatindexMetric;
	private double heatindexImperial;
	private double precipitationMetric;
	private double precipitationImperial;
	private String conditionPhrase;
	private String icon;
	private int fog;
	private int rain;
	private int snow;
	private int hail;
	private int thunder;
	private int tornado;

	@JsonIgnore
	public Date getJavaDateFromWundergroundDate() throws ParseException {
		SimpleDateFormat prettyFormat = new SimpleDateFormat(
				WundergroundDate.PRETTY_DATE_PATTERN);
		Date localDate = prettyFormat.parse(this.getWundergroundDate()
				.getPretty());

		return localDate;
	}

	@JsonIgnore
	public Date getDate() {
		return date;
	}

	@JsonIgnore
	public void setDate(Date date) {
		this.date = date;
	}

	@JsonProperty("date")
	public WundergroundDate getWundergroundDate() {
		return wundergroundDate;
	}

	@JsonProperty("date")
	public void setWundergroundDate(WundergroundDate wundergroundDate) {
		this.wundergroundDate = wundergroundDate;
	}

	@JsonProperty("utcdate")
	public WundergroundDate getWundergroundUtcDate() {
		return wundergroundUtcDate;
	}

	@JsonProperty("utcdate")
	public void setWundergroundUtcDate(WundergroundDate wundergroundUtcDate) {
		this.wundergroundUtcDate = wundergroundUtcDate;
	}

	@JsonProperty("tempm")
	public double getTempMetric() {
		return tempMetric;
	}

	@JsonProperty("tempm")
	public void setTempMetric(double tempMetric) {
		this.tempMetric = tempMetric;
	}

	@JsonProperty("tempi")
	public double getTempImperial() {
		return tempImperial;
	}

	@JsonProperty("tempi")
	public void setTempImperial(double tempImperial) {
		this.tempImperial = tempImperial;
	}

	@JsonProperty("dewptm")
	public double getDewpointMetric() {
		return dewpointMetric;
	}

	@JsonProperty("dewptm")
	public void setDewpointMetric(double dewpointMetric) {
		this.dewpointMetric = dewpointMetric;
	}

	@JsonProperty("dewpti")
	public double getDewpointImperial() {
		return dewpointImperial;
	}

	@JsonProperty("dewpti")
	public void setDewpointImperial(double dewpointImperial) {
		this.dewpointImperial = dewpointImperial;
	}

	@JsonProperty("hum")
	public double getHumidity() {
		return humidity;
	}

	@JsonProperty("hum")
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	@JsonProperty("wspdm")
	public double getWindSpeedMetric() {
		return windSpeedMetric;
	}

	@JsonProperty("wspdm")
	public void setWindSpeedMetric(double windSpeedMetric) {
		this.windSpeedMetric = windSpeedMetric;
	}

	@JsonProperty("wspdi")
	public double getWindSpeedImperial() {
		return windSpeedImperial;
	}

	@JsonProperty("wspdi")
	public void setWindSpeedImperial(double windSpeedImperial) {
		this.windSpeedImperial = windSpeedImperial;
	}

	@JsonProperty("wgustm")
	public double getWindGustMetric() {
		return windGustMetric;
	}

	@JsonProperty("wgustm")
	public void setWindGustMetric(double windGustMetric) {
		this.windGustMetric = windGustMetric;
	}

	@JsonProperty("wgusti")
	public double getWindGustImperial() {
		return windGustImperial;
	}

	@JsonProperty("wgusti")
	public void setWindGustImperial(double windGustImperial) {
		this.windGustImperial = windGustImperial;
	}

	@JsonProperty("wdird")
	public double getWindDirectionDegrees() {
		return windDirectionDegrees;
	}

	@JsonProperty("wdird")
	public void setWindDirectionDegrees(double windDirectionDegrees) {
		this.windDirectionDegrees = windDirectionDegrees;
	}

	@JsonProperty("wdire")
	public String getWindDirectionDescription() {
		return windDirectionDescription;
	}

	@JsonProperty("wdire")
	public void setWindDirectionDescription(String windDirectionDescription) {
		this.windDirectionDescription = windDirectionDescription;
	}

	@JsonProperty("vism")
	public double getVisibilityMetric() {
		return visibilityMetric;
	}

	@JsonProperty("vism")
	public void setVisibilityMetric(double visibilityMetric) {
		this.visibilityMetric = visibilityMetric;
	}

	@JsonProperty("visi")
	public double getVisibilityImperial() {
		return visibilityImperial;
	}

	@JsonProperty("visi")
	public void setVisibilityImperial(double visibilityImperial) {
		this.visibilityImperial = visibilityImperial;
	}

	@JsonProperty("pressurem")
	public double getPressureMetric() {
		return pressureMetric;
	}

	@JsonProperty("pressurem")
	public void setPressureMetric(double pressureMetric) {
		this.pressureMetric = pressureMetric;
	}

	@JsonProperty("pressurei")
	public double getPressureImperial() {
		return pressureImperial;
	}

	@JsonProperty("pressurei")
	public void setPressureImperial(double pressureImperial) {
		this.pressureImperial = pressureImperial;
	}

	@JsonProperty("windchillm")
	public double getWindchillMetric() {
		return windchillMetric;
	}

	@JsonProperty("windchillm")
	public void setWindchillMetric(double windchillMetric) {
		this.windchillMetric = windchillMetric;
	}

	@JsonProperty("windchilli")
	public double getWindchillImperial() {
		return windchillImperial;
	}

	@JsonProperty("windchilli")
	public void setWindchillImperial(double windchillImperial) {
		this.windchillImperial = windchillImperial;
	}

	@JsonProperty("heatindexm")
	public double getHeatindexMetric() {
		return heatindexMetric;
	}

	@JsonProperty("heatindexm")
	public void setHeatindexMetric(double heatindexMetric) {
		this.heatindexMetric = heatindexMetric;
	}

	@JsonProperty("heatindexi")
	public double getHeatindexImperial() {
		return heatindexImperial;
	}

	@JsonProperty("heatindexi")
	public void setHeatindexImperial(double heatindexImperial) {
		this.heatindexImperial = heatindexImperial;
	}

	@JsonProperty("precipm")
	public double getPrecipitationMetric() {
		return precipitationMetric;
	}

	@JsonProperty("precipm")
	public void setPrecipitationMetric(double precipitationMetric) {
		this.precipitationMetric = precipitationMetric;
	}

	@JsonProperty("precipi")
	public double getPrecipitationImperial() {
		return precipitationImperial;
	}

	@JsonProperty("precipi")
	public void setPrecipitationImperial(double precipitationImperial) {
		this.precipitationImperial = precipitationImperial;
	}

	@JsonProperty("conds")
	public String getConditionPhrase() {
		return conditionPhrase;
	}

	@JsonProperty("conds")
	public void setConditionPhrase(String conditionPhrase) {
		this.conditionPhrase = conditionPhrase;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getFog() {
		return fog;
	}

	public void setFog(int fog) {
		this.fog = fog;
	}

	public int getRain() {
		return rain;
	}

	public void setRain(int rain) {
		this.rain = rain;
	}

	public int getSnow() {
		return snow;
	}

	public void setSnow(int snow) {
		this.snow = snow;
	}

	public int getHail() {
		return hail;
	}

	public void setHail(int hail) {
		this.hail = hail;
	}

	public int getThunder() {
		return thunder;
	}

	public void setThunder(int thunder) {
		this.thunder = thunder;
	}

	public int getTornado() {
		return tornado;
	}

	public void setTornado(int tornado) {
		this.tornado = tornado;
	}

}