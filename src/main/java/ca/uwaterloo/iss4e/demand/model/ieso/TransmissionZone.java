package ca.uwaterloo.iss4e.demand.model.ieso;

public enum TransmissionZone {
	NORTHWEST(10, 48.38, -89.29), NORTHEAST(9, 46.49, -81.0), OTTAWA(5, 45.42,
			-75.7), EAST(3, 44.23, -76.5), TORONTO(1, 43.7, -79.4), ESSA(8,
			44.41, -79.68), BRUCE(7, 44.43, -81.39), SOUTHWEST(2, 43.45, -80.48), NIAGARA(
			6, 43.12, -79.07), WEST(13, 42.28, -83), TOTAL_ONTARIO(1);

	private int locationId;
	private double locationLatitude;
	private double locationLongitude;

	TransmissionZone(int locationId) {
		this.locationId = locationId;
	}

	TransmissionZone(int locationId, double locationLatitude,
			double locationLongitude) {
		this.locationId = locationId;
		this.locationLatitude = locationLatitude;
		this.locationLongitude = locationLongitude;
	}

	public int getLocationId() {
		return locationId;
	}

	public double getLocationLatitude() {
		return locationLatitude;
	}

	public double getLocationLongitude() {
		return locationLongitude;
	}
}
