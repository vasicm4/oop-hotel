package rooms;

import java.util.Objects;

import hotel.Service;

public class RoomType {
	
	protected String type;
	protected int capacity;
	protected boolean deleted;
	
	public RoomType(String type, int capacity) {
		this.type = type;
		this.capacity = capacity;
		this.deleted = false;
	}
	
	public RoomType(String type, int capacity, boolean deleted) {
		this.type = type;
		this.capacity = capacity;
		this.deleted = deleted;
	}
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomType roomType = (RoomType) o;
		return capacity == roomType.capacity && deleted == roomType.deleted 
				&& Objects.equals(type, roomType.type);
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public String getType() {
		return this.type;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public void delete() {
		this.deleted = true;
	}
	
	public boolean isDeleted() {
		return this.deleted;
	}
	
	@Override
	public String toString() {
		return this.type;
	}
}
