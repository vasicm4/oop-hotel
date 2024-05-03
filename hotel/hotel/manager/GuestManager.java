package manager;

import java.time.LocalDate;
import java.util.ArrayList;

import users.Guest;

public class GuestManager {
	ArrayList<Guest> guests;
	String fileName;
	String filePath;
	
	public GuestManager() {
		guests = new ArrayList<Guest>();
		fileName = "guests.txt";
		filePath = "data/";
	}
	
	public Guest find(String username) {
		for (Guest guest : guests) {
			if (guest.get_username().equals(username)) {
				return guest;
			}
		}
		System.out.println("Guest not found");
		return null;
	}
	
	public ArrayList<Guest> getGuests() {
		return guests;
	}
	
	public void add(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, boolean male) {
		this.guests.add(new Guest(username, password, name, surname, dateOfBirth, phoneNumber, male));
	}
	
	public void remove(Guest guest) {
		guest.delete();
	}
	
	
}
