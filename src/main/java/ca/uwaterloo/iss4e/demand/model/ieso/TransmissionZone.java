package ca.uwaterloo.iss4e.demand.model.ieso;

public enum TransmissionZone {
	NORTHWEST(10), NORTHEAST(9), OTTAWA(5), EAST(3), TORONTO(1), ESSA(8), BRUCE(
			7), SOUTHWEST(2), NIAGARA(6), WEST(13);

	private int locationId;

	TransmissionZone(int locationId) {
		this.locationId = locationId;
	}

	public int getLocationId() {
		return locationId;
	}
}
