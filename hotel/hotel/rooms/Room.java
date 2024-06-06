package rooms;

public class Room {
	protected RoomType type;
	protected int number;
	protected int floor;
	protected RoomStatus status;
	protected boolean deleted;
	
	public Room(RoomType type, int number, int floor) {
		this.type = type;
		this.number = number;
		this.floor = floor;
		this.status = RoomStatus.AVAILABLE;
		this.deleted = false;
	}
	
	public Room(RoomType type, int number, int floor, RoomStatus status, boolean deleted) {
		this.type = type;
		this.number = number;
		this.floor = floor;
		this.status = status;
		this.deleted = deleted;
	}

	public RoomStatus getStatus() {
		return status;
	}
	
	public void setStatus(RoomStatus status) {
		this.status = status;
	}
	
	public RoomType getType() {
		return type;
	}
	
	public void setType(RoomType type) {
		this.type = type;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getFloor() {
		return floor;
	}
	
	public void setFloor(int floor) {
		this.floor = floor;
	}
	
	public void delete() {
		deleted = true;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	@Override
	public String toString() {
		return "Number: " + number + ", Type: " + type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Room)) {
			return false;
		}

		Room room = (Room) obj;
		return room.number == number && room.floor == floor;
	}
	
	
	
}
