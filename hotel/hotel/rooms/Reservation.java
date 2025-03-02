package rooms;

import java.time.LocalDate;
import java.util.ArrayList;

import hotel.PriceList;
import hotel.Service;
import manager.PriceListManager;
import users.Guest;

public class Reservation {
	String id;
	protected LocalDate CheckIn;
	protected LocalDate CheckOut;
	protected RoomType roomType;
	protected Room room;
	protected Guest guest;
	protected ArrayList<Service> services;
	protected ReservationStatus status;
	protected boolean deleted;

	
	public Reservation(LocalDate CheckIn, LocalDate CheckOut, Room room, Guest guest, String id) {
		this.CheckIn = CheckIn;
		this.CheckOut = CheckOut;
		this.room = room;
		this.deleted = false;
		this.roomType = null;
		this.guest = guest;
		this.services = new ArrayList<Service>();
		this.status = ReservationStatus.ON_HOLD;
		this.id = id;
	}
	
	public Reservation(LocalDate CheckIn, LocalDate CheckOut, Room room, Guest guest, ArrayList<Service> services, String id) {
		this.CheckIn = CheckIn;
		this.CheckOut = CheckOut;
		this.room = room;
		this.deleted = false;
		this.guest = guest;
		this.roomType = null;
		this.services = services;
		this.status =  ReservationStatus.ON_HOLD;
		this.id = id;
	}
	
	public Reservation(LocalDate CheckIn, LocalDate CheckOut, RoomType roomType, Guest guest, ArrayList<Service> services, ReservationStatus status, String id, boolean deleted) {
		this.CheckIn = CheckIn;
		this.CheckOut = CheckOut;
		this.room = null;
		this.deleted = deleted;
		this.guest = guest;
		this.roomType = roomType;
		this.services = services;
		this.status = status;
		this.id = id;
	}
		
	public Reservation(LocalDate CheckIn, LocalDate CheckOut, Room room, RoomType roomType, Guest guest, ArrayList<Service> services, ReservationStatus status, String id, boolean deleted) {
		this.CheckIn = CheckIn;
		this.CheckOut = CheckOut;
		this.room = room;
		this.deleted = deleted;
		this.guest = guest;
		this.roomType = roomType;
		this.services = services;
		this.status = status;
		this.id = id;
	}
	
	public String toString() {
		return "Reservation [CheckIn=" + CheckIn + ", CheckOut=" + CheckOut + ", room=" + room + ", roomType="
				+ roomType + ", guest=" + guest + ", services=" + services + ", status=" + status + ", id=" + id + "]";
	}
	
	public double getPrice(PriceListManager priceListManager) {
		double price = 0;
		if (this.getStatus() == ReservationStatus.REJECTED) {
			return 0;
		}
		//TODO implement price change by date
		int days = CheckOut.getDayOfYear() - CheckIn.getDayOfYear();
		PriceList priceList = priceListManager.findRange(CheckIn, CheckOut);
		price += priceList.getRoomPrice(roomType);
		for (Service service : services) {
			price += priceList.getServicePrice(service);
			price *= roomType.getCapacity();
		}
		return days * price;
	}
	
	public void accept() {
		if (this.status != ReservationStatus.ON_HOLD) {
			return;
		}
		this.status = ReservationStatus.ACCEPTED;
	}
	
	public boolean checkIn(Room room) {
		if (this.room.getStatus() == RoomStatus.CLEANED || this.room.getStatus() == RoomStatus.OCCUPIED || this.status != ReservationStatus.CHECKED_IN || this.status != ReservationStatus.CHECKED_OUT) {
			return false;
		}
		this.room = room;
		this.room.setStatus(RoomStatus.OCCUPIED);
		this.status = ReservationStatus.CHECKED_IN;
		return true;
	}
	
	public void checkOut() {
		if (this.status != ReservationStatus.CHECKED_IN) {
			return;
		}
		this.room.setStatus(RoomStatus.CLEANED);
		this.status = ReservationStatus.CHECKED_OUT;
	}
	
	public void reject() {
		if (this.status != ReservationStatus.ON_HOLD) {
			return;
		}
		this.status = ReservationStatus.REJECTED;
	}
	
	public ReservationStatus getStatus() {
		return status;
	}
	
	public void setStatus(ReservationStatus status) {
		this.status = status;
	}
	
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	
	public RoomType getRoomType() {
		return roomType;
	}
	
	public LocalDate getCheckIn() {
		return CheckIn;
	}
	
	public void addService(Service service) {
		this.services.add(service);
	}
	
	public void setServices(ArrayList<Service> services) {
		this.services = services;
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
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public void delete() {
		this.deleted = true;
	}

	public void cancel() {
		if (this.status == ReservationStatus.CHECKED_IN || this.status == ReservationStatus.CHECKED_OUT) {
			return;
		}
		this.setStatus(ReservationStatus.CANCELLED);
	}
}
