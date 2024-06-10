 package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import rooms.Room;
import rooms.RoomStatus;
import rooms.RoomType;

public class RoomManager {
	private static RoomManager instance;
	protected HashMap<String, Room> rooms;
	protected String fileName;
	protected String filePath;
	
	public static RoomManager getInstance() {
		if (instance == null) {
			instance = new RoomManager();
		}
		return instance;
	}
	
	
	public RoomManager() {
		rooms = new HashMap<String, Room>();
		fileName = "rooms.csv";
		filePath = "data/";
	}
	
	public Room find( RoomType type) {
		for (Room room : rooms.values()) {
			if (room.getType().equals(type) && room.getStatus().equals(RoomStatus.AVAILABLE)) {
				return room;
			}
		}
		return null;
	}
	
	public Room find(int number) {
		if (rooms.containsKey(String.valueOf(number))) {
			return rooms.get(String.valueOf(number));
		} else {
			return null;
		}
	}
	
	public HashMap<String, Room> getRooms() {
		HashMap<String, Room> availableRooms = new HashMap<String, Room>();
		for (Room room : rooms.values()) {
			if (room.isDeleted() == false) {
				availableRooms.put(String.valueOf(room.getNumber()), room);
			}
		}
		return availableRooms;
	}
	
	public int generateNumber() {
		int number = 0;
		for (Room room : rooms.values()) {
			number = room.getNumber();
		}
		number++;
		return number;
	}
	
	public void add(RoomType type, int floor, ArrayList<String> additionalServices) {
		this.rooms.put(String.valueOf(this.generateNumber()), new Room(type, this.generateNumber(), floor, additionalServices));
	}
	
	
	public void add(RoomType type, int number, int floor, RoomStatus status, ArrayList<String> additionalServices, boolean deleted) {
		this.rooms.put(String.valueOf(number),new Room(type, number, floor, status, additionalServices, deleted));
	}
	
	public ArrayList<String> getAllAdditionalServices(){
		ArrayList<String> additionalServices = new ArrayList<String>();
		for (Room room : rooms.values()) {
			for (String service : room.getAdditionalServices()) {
				if (!additionalServices.contains(service)) {
					additionalServices.add(service);
				}
			}
		}
		return additionalServices;
	}
	
	public void remove(Room room) {
		room.delete();
	}
	
	public void update(Room room, RoomType type, int number, int floor, RoomStatus status) {
		room.setType(type);
		room.setNumber(number);
		room.setFloor(floor);
		room.setStatus(status);
		
	}
	
	public ArrayList<String> findRoomTypes(ArrayList<String> additionalServices) {
		ArrayList<String> roomTypes = new ArrayList<String>();
		for (Room room : rooms.values()) {
			if (room.getAdditionalServices().containsAll(additionalServices)) {
				roomTypes.add(room.getType().toString());
			}
		}
		return roomTypes;
	}
	
	
	public ArrayList<String> readAdditionalServices(String roomNumber) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath + "additionalServices.csv"));
			String line;
			ArrayList<String> additionalServices = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data[0].equals(roomNumber)) {
					additionalServices.add(data[1]);
				}
			}
			reader.close();
			return additionalServices;
		} catch (Exception e) {
			System.out.println("Error reading file additionalServices.csv");
			return null;
		}
	}
	
	public void readData(RoomTypeManager roomTypeManager) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath + fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				ArrayList<String> additionalServices = this.readAdditionalServices(data[1]);
				this.add(roomTypeManager.get(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), RoomStatus.getStatus(data[3]), additionalServices,  Boolean.parseBoolean(data[3]));
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Error reading file" + fileName);
		} catch (Exception e) {
			System.out.println("Error reading file" + fileName);
		}
	}
	
	public void writeAdditionalServices() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "additionalServices.csv"));
			for (Room room : rooms.values()) {
				for (String service : room.getAdditionalServices()) {
					writer.write(String.valueOf(room.getNumber()) + "," + service + "\n");
				}
			}
			writer.flush();
		} catch (Exception e) {
			System.out.println("Error writing file additionalServices.csv");
		}
	}
	
	public void writeData() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + fileName));
			this.writeAdditionalServices();
			for (Room room : rooms.values()) {
				writer.write(room.getType() + "," + String.valueOf(room.getNumber()) + "," + String.valueOf(room.getFloor()) + "," + RoomStatus.getStatus(room.getStatus()) + "," + String.valueOf(room.isDeleted()) + "\n");
			}
			writer.flush();
		} catch(Exception e){
			System.out.println("error" + fileName);
		}
	}

}
