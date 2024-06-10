package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import users.Education;
import users.Gender;
import users.Guest;

public class GuestManager {
	private static GuestManager instance;
	protected HashMap<String, Guest> guests;
	protected String fileName;
	protected String filePath;
	
	public static GuestManager getInstance() {
		if (instance == null) {
			instance = new GuestManager();
		}
		return instance;
		
	}
	
	public GuestManager() {
		this.guests = new HashMap<String, Guest>();
		this.fileName = "guests.csv";
		this.filePath = "data/";
	}
	
	public Guest find(String username) {
		if (guests.containsKey(username)) {
			return guests.get(username);
		} else {
			return null;
		}
	}
	
	public HashMap<String, Guest> getGuests() {
		return guests;
	}
	
	public HashMap<String, Guest> getAvailableGuests(){
		HashMap<String, Guest> guests = new HashMap<String, Guest>();
		for (Guest guest : this.guests.values()) {
			if (guest.isDeleted() == false) {
				guests.put(guest.getUsername(), guest);
			}
		}
		return guests;
	}
	
	public void add(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, Gender gender) {
		this.guests.put(username, new Guest(username, password, name, surname, dateOfBirth, phoneNumber, gender));
	}
	
	public void add(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, Gender gender, Boolean deleted) {
		this.guests.put(username, new Guest(username, password, name, surname, dateOfBirth, phoneNumber, gender, deleted));
	}
	
	public void remove(Guest guest) {
		guest.delete();
	}
	
	public void readData() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath + fileName));
			String line;
			while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
				this.add(data[0], data[1], data[2], data[3], LocalDate.parse(data[4]), Integer.parseInt(data[5]), Gender.valueOf(data[6]), Boolean.valueOf(data[7]));
			}
		}catch (Exception e) {
			System.out.println("Error reading file" + fileName);
		}
	
	}
	public void writeData() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + fileName));
			for (Guest guest : guests.values()) {
				writer.write(guest.getUsername() + "," + guest.getPassword() + "," + guest.getName() + "," + guest.getSurname() + "," + guest.getDateOfBirth().toString() + "," + String.valueOf(guest.getPhoneNumber()) + "," + String.valueOf(guest.getGender()) + "," + String.valueOf(guest.isDeleted()) +"\n");
			}
			writer.flush();
		} catch (Exception e) {
			System.out.println("Error writing to file" + fileName);
		}
	}
	
	
}
