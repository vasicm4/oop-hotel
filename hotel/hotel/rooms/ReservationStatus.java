package rooms;

public enum ReservationStatus {
	ON_HOLD, ACCEPTED, REJECTED, CHECKED_IN, CHECKED_OUT, CANCELLED;

	public static String getStatus(ReservationStatus status) {
		switch (status) {
		case ON_HOLD:
			return "ON_HOLD";
		case ACCEPTED:
			return "ACCEPTED";
		case REJECTED:
			return "REJECTED";
		case CHECKED_IN:
			return "CHECKED_IN";
		case CHECKED_OUT:
			return "CHECKED_OUT";
		case CANCELLED:
			return "CANCELLED";
		default:
			return null;
		}
	}
	
	public static ReservationStatus getStatus(String status) {
		switch (status) {
		case "ON_HOLD":
			return ON_HOLD;
		case "ACCEPTED":
			return ACCEPTED;
		case "REJECTED":
			return REJECTED;
		case "CHECKED_IN":
			return CHECKED_IN;
		case "CHECKED_OUT":
			return CHECKED_OUT;
		case "CANCELLED":
			return CANCELLED;
		default:
			return null;
		}
	}
	
}
