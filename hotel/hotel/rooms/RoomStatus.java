package rooms;

public enum RoomStatus {
	AVAILABLE, OCCUPIED, CLEANED;

	public static String getStatus(RoomStatus status) {
		switch (status) {
		case AVAILABLE:
			return "AVAILABLE";
		case CLEANED:
			return "CLEANED";
		case OCCUPIED:
			return "OCCUPIED";
		default:
			return null;
		}
	}
	
	public static RoomStatus getStatus(String status) {
		switch (status) {
		case "AVAILABLE":
			return AVAILABLE;
		case "CLEANED":
			return CLEANED;
		case "OCCUPIED":
			return OCCUPIED;
		default:
			return null;
		}
	}
	
}
