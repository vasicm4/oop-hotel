package rooms;

import java.time.LocalDate;
import java.util.ArrayList;

import hotel.Service;
import users.Guest;

public class Reservation {
	protected LocalDate CheckIn;
	protected LocalDate CheckOut;
	protected Room room;
	protected boolean deleted;
	protected Guest guest;
	protected ArrayList<Service> services;
	protected ReservationStatus status;
	
	public Reservation(LocalDate CheckIn, LocalDate CheckOut, Room room, Guest guest) {
		this.CheckIn = CheckIn;
		this.CheckOut = CheckOut;
		this.room = room;
		this.deleted = false;
		this.guest = guest;
		this.services = new ArrayList<Service>();
		this.status = ReservationStatus.ON_HOLD;
	}
	
	public ReservationStatus getStatus() {
		return status;
	}
	
	public void setStatus(ReservationStatus status) {
		this.status = status;
	}
	
	public LocalDate getCheckIn() {
		return CheckIn;
	}
	
	public void addService(Service service) {
		this.services.add(service);
	}
	
	public ArrayList<Service> getServices() {
		return services;
	}
	
	public void setCheckIn(LocalDate CheckIn) {
		this.CheckIn = CheckIn;
	}
	
	public LocalDate getCheckOut() {
		return CheckOut;
	}
	
	public void setCheckOut(LocalDate CheckOut) {
		this.CheckOut = CheckOut;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	
	public Guest getGuest() {
		return guest;
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public void delete() {
		this.deleted = true;
	}
	
	
}
