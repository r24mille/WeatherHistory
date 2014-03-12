package ca.uwaterloo.iss4e.demand.model.ieso;

public enum TransmissionZone {
	NORTHWEST(10, 48.38, -89.29), NORTHEAST(9, 46.49, -81.0), OTTAWA(5, 45.42, -75.7), EAST(3), TORONTO(1), ESSA(8), BRUCE(
			7), SOUTHWEST(2), NIAGARA(6), WEST(13);

	private int locationId;
	private double locationLatitude;
	private double locationLongitude;

	TransmissionZone(int locationId) {
		this.locationId = locationId;
	}
	
	TransmissionZone(int locationId, double locationLatitude, double locationLongitude) {
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
