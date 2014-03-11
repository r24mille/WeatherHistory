package ca.uwaterloo.iss4e.weather.forecastio.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GivenTimeResponse {
	private DataPoint currently;
	private int offset;
	private Flags flags;

	public DataPoint getCurrently() {
		return currently;
	}

	public void setCurrently(DataPoint currently) {
		this.currently = currently;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public Flags getFlags() {
		return flags;
	}

	public void setFlags(Flags flags) {
		this.flags = flags;
	}
}