package rooms;

public enum RoomType {
	SINGLE, DOUBLE, TRIPLE, SUITE;
	
	public int getType(RoomType type) {
		switch (type) {
		case SINGLE:
			return 1;
		case DOUBLE:
			return 2;
		case TRIPLE:
			return 3;
		case SUITE:
			return 4;
		default:
			return 0;
		}
	}
}
