package ca.uwaterloo.iss4e.weather.wunderground.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryResponse {
	private History history;

	public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	}
	
	
}
