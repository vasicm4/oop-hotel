package rooms;

import java.util.ArrayList;
import java.util.Objects;

import hotel.Service;

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
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
		return number == room.number && floor == room.floor && deleted == room.deleted
				&& Objects.equals(type, room.type) && status == room.status
				&& Objects.equals(additionalServices, room.additionalServices);
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
}
