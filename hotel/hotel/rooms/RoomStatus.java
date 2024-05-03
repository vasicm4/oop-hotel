package rooms;

public enum RoomStatus {
	AVAILABLE, OCCUPIED, CLEANED;

	public int getStatus(RoomStatus status) {
		switch (status) {
		case AVAILABLE:
			return 1;
		case CLEANED:
			return 2;
		case OCCUPIED:
			return 3;
		default:
			return 0;
		}
	}
}
