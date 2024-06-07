 package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
	
	public void add(RoomType type, int number, int floor) {
		this.rooms.put(String.valueOf(number),new Room(type, number, floor));
	}
	
	public void add(RoomType type, int floor) {
		this.rooms.put(String.valueOf(this.generateNumber()), new Room(type, this.generateNumber(), floor));
	}
	
	
	public void add(RoomType type, int number, int floor, RoomStatus status, boolean deleted) {
		this.rooms.put(String.valueOf(number),new Room(type, number, floor, status, deleted));
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
	
	
	public void readData(RoomTypeManager roomTypeManager) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath + fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				this.add(roomTypeManager.get(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), RoomStatus.getStatus(data[3]) ,  Boolean.parseBoolean(data[3]));
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Error reading file" + fileName);
		} catch (Exception e) {
			System.out.println("Error reading file" + fileName);
		}
	}
	
	public void writeData() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + fileName));	
			for (Room room : rooms.values()) {
				writer.write(room.getType() + "," + String.valueOf(room.getNumber()) + "," + String.valueOf(room.getFloor()) + "," + RoomStatus.getStatus(room.getStatus()) + "," + String.valueOf(room.isDeleted()) + "\n");
			}
		} catch(Exception e){
			System.out.println("error");
		}
	}

}
