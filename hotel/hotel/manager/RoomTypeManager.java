package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import rooms.RoomType;

public class RoomTypeManager {
	private static RoomTypeManager instance;
	protected HashMap<String, RoomType> roomTypes;
	protected String fileName;
	protected String filePath;
	
	public static RoomTypeManager getInstance() {
		if (instance == null) {
			instance = new RoomTypeManager();
		}
		return instance;
	}
	
	public RoomTypeManager() {
		roomTypes = new HashMap<String, RoomType>();
		fileName = "roomTypes.csv";
		filePath = "data" + System.getProperty("file.separator");
	}
		
	public RoomType get(String roomType) {
		return roomTypes.get(roomType);
	}
	
	public RoomType getAvailable(String roomType) {
		if (roomTypes.containsKey(roomType) && !roomTypes.get(roomType).isDeleted()) {
			return roomTypes.get(roomType);
		} else {
			return null;
		}
	}
	
	public HashMap<String, RoomType> getAvailableRoomTypes() {
		HashMap<String, RoomType> availableRoomTypes = new HashMap<String, RoomType>();
		for (RoomType roomType : roomTypes.values()) {
			if (!roomType.isDeleted()) {
				availableRoomTypes.put(roomType.getType(), roomType);
			}
		}
		return availableRoomTypes;
	}
	
	public void add(String type, int number) {
		roomTypes.put(type, new RoomType(type, number));
	}
	
	public void add(String type, int number, boolean deleted) {
		roomTypes.put(type, new RoomType(type, number, deleted));
	}
	
	public void remove(RoomType roomType) {
		for (RoomType room : roomTypes.values()) {
			if (room.equals(roomType)) {
				room.delete();
			}
		}
	}
	
	public void change(String type, int number) {
		roomTypes.replace(type, new RoomType(type, number));
	}
	
	public RoomType getRoomType(String type) {
		return roomTypes.get(type);
	}
	
	public HashMap<String, RoomType> getRoomTypes() {
		return roomTypes;
	}
	
	@Override
	public String toString() {
        return roomTypes.toString();
	}
	
	public String toString(String type) {
		return roomTypes.get(type).toString();
	}
	
	public void readData() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath + fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				this.add(data[0], Integer.parseInt(data[1]), Boolean.parseBoolean(data[2]));
			}
		}catch (Exception e) {
			System.out.println("Error reading file" + fileName);
        }
		
	}
	
	public void writeData() {
		try {
			FileWriter writer = new FileWriter(filePath + fileName);
            for (RoomType roomType : roomTypes.values()) {
                writer.write(roomType.getType() + "," + String.valueOf(roomType.getCapacity()) + "," + String.valueOf(roomType.isDeleted()) + "\n");
            }
			writer.flush();
		} catch (Exception e) {
			System.out.println("Error writing to file" + fileName);
		}
	}	
}

