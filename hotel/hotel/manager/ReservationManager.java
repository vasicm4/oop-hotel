package manager;

import java.time.LocalDate;
import java.util.ArrayList;

import rooms.Reservation;
import rooms.Room;
import users.Guest;

public class ReservationManager {
	protected ArrayList<Reservation> reservations;
	protected String fileName;
	protected String filePath;
	
	public ReservationManager() {
		reservations = new ArrayList<Reservation>();
		fileName = "reservations.txt";
		filePath = "data" + System.getProperty("file.separator");
	}
	
	public void add(LocalDate CheckIn, LocalDate CheckOut, Room room, Guest guest) {
        this.reservations.add(new Reservation(CheckIn, CheckOut, room, guest));
    }
	
	public ArrayList<Reservation> findReservations(Guest guest) {
		ArrayList<Reservation> reservationsFound = new ArrayList<Reservation>();
		for (Reservation reservation : reservations) {
			if (reservation.getGuest().equals(guest) && !reservation.isDeleted()) {
				reservationsFound.add(reservation);
			}
		}
		return reservationsFound;
	}
	
	public ArrayList<Reservation> findReservations(LocalDate CheckIn,LocalDate CheckOut) {
		ArrayList<Reservation> reservationsFound = new ArrayList<Reservation>();
		for (Reservation reservation : reservations) {
			if (reservation.getCheckIn().compareTo(CheckIn)>0  && reservation.getCheckOut().compareTo(CheckOut)<0 && !reservation.isDeleted()) {
				reservationsFound.add(reservation);
			}
		}
		return reservationsFound;
	}
	
	public Reservation findReservation(Guest guest, LocalDate CheckIn,LocalDate CheckOut) {
		for (Reservation reservation : reservations) {
			if (reservation.getGuest().equals(guest) && reservation.getCheckIn().compareTo(CheckIn)>0  && reservation.getCheckOut().compareTo(CheckOut)<0 && !reservation.isDeleted()) {
				return reservation;
			}
		}
		return null;
	}
	
	public ArrayList<Reservation> getReservations() {
		return reservations;
	}
	
	public ArrayList<Room> roomsAvailable(ArrayList<Room> rooms, LocalDate CheckIn, LocalDate CheckOut) {
		ArrayList<Room> roomsAvailable = new ArrayList<Room>();
		ArrayList<Reservation> reservations = findReservations(CheckIn, CheckOut);
		for (Reservation reservation:reservations ) {
			for (Room room: rooms) {
				if (!room.equals(reservation.getRoom())) {
					roomsAvailable.add(room);
				}
			}
		}
		if (reservations.isEmpty()) {
            return rooms;
        }
		return roomsAvailable;
	}
	
}
