package rooms;

public enum ReservationStatus {
	ON_HOLD, ACCEPTED, REJECTED, CANCELLED;

	public int getBonus(ReservationStatus status) {
		switch (status) {
		case ON_HOLD:
			return 1;
		case ACCEPTED:
			return 2;
		case REJECTED:
			return 3;
		case CANCELLED:
			return 4;
		default:
			return 0;
		}
	}
}
