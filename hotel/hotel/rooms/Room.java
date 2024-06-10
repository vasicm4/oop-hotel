package rooms;

import java.util.ArrayList;

public class Room {
	protected RoomType type;
	protected int number;
	protected int floor;
	protected RoomStatus status;
	protected boolean deleted;
	protected ArrayList<String> additionalServices;
	
	public Room(RoomType type, int number, int floor, ArrayList<String> additionalServices) {
		this.type = type;
		this.number = number;
		this.floor = floor;
		this.status = RoomStatus.AVAILABLE;
		this.deleted = false;
		this.additionalServices = additionalServices;
	}
	
	public Room(RoomType type, int number, int floor, RoomStatus status, ArrayList<String> additionalServices, boolean deleted) {
		this.type = type;
		this.number = number;
		this.floor = floor;
		this.status = status;
		this.additionalServices = additionalServices;
		this.deleted = deleted;
	}

	public ArrayList<String> getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(ArrayList<String> additionalServices) {
		this.additionalServices = additionalServices;
	}
	
	public void addService(String service) {
		additionalServices.add(service);
	}
	
	public void removeService(String service) {
		additionalServices.remove(service);
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
