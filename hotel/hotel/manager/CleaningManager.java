package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import rooms.Room;
import rooms.RoomStatus;

public class CleaningManager {
	private static CleaningManager instance;
	protected HashMap<String, ArrayList<Room>>  roomsByDate;
	protected HashMap<String, HashMap<String, ArrayList<Room>>> janitorByDate;
	protected HashMap<String, ArrayList<Room>> roomsToBeCleaned;
	protected String filePath;
	
	public static CleaningManager getInstance() {
		if (instance == null) {
			instance = new CleaningManager();
		}
		return instance;
	}
	
	public CleaningManager() {
		roomsByDate = new HashMap<String, ArrayList<Room>>();
        janitorByDate = new HashMap<String, HashMap<String, ArrayList<Room>>>();
        roomsToBeCleaned = new HashMap<String, ArrayList<Room>>();
        filePath = "data/";
	}
	
	public ArrayList<Room> getRoomsToBeCleaned(String janitor) {
		if (roomsToBeCleaned == null) {
			return null;
		} else if (roomsToBeCleaned.containsKey(janitor)) {
			return roomsToBeCleaned.get(janitor);
		}
		return null;
	}
	
	public void addDailyTask(String username, Room room) {
		if (!roomsToBeCleaned.containsKey(username)) {
			roomsToBeCleaned.put(username, new ArrayList<Room>());
		}
		roomsToBeCleaned.get(username).add(room);
	}
	
	public void cleanRoom(String username, Room room) {
		room.setStatus(RoomStatus.AVAILABLE);
		roomsToBeCleaned.get(username).remove(room);
		this.addRoom(username, String.valueOf(LocalDate.now()), room);
	}
	
	public void addRoom(String username,String date, Room room) {
		if (!janitorByDate.containsKey(username)) {
			janitorByDate.put(username, new HashMap<String, ArrayList<Room>>());
		}
		if (!janitorByDate.get(username).containsKey(date)) {
			janitorByDate.get(username).put(date, new ArrayList<Room>());
		}
		janitorByDate.get(username).get(date).add(room);
	}
	
	public void removeRoom(String username, String date, Room room) {
		if (janitorByDate.containsKey(username) && janitorByDate.get(username).containsKey(date)) {
			janitorByDate.get(username).get(date).remove(room);
		}
	}
	
	public ArrayList<Room> getRoomsByDate(String janitor,String date) {
		return janitorByDate.get(janitor).get(date);
	}
	
	public HashMap<String, HashMap<String, ArrayList<Room>>> getJanitorByDate() {
		return janitorByDate;
	}
	
	public HashMap<String, ArrayList<Room>> getRoomsToBeCleaned() {
		return roomsToBeCleaned;
	}
	
	public HashMap<String, Integer> getCleanedRoomsCount(LocalDate startDate, LocalDate endDate) {
		HashMap<String, Integer> cleanedRoomsByJanitorCount = new HashMap<String, Integer>();
		for (String janitor: this.janitorByDate.keySet()) {
			int count = 0;
			for (String date : this.janitorByDate.get(janitor).keySet()) {
				LocalDate localDate = LocalDate.parse(date);
				if (localDate.isAfter(startDate) && localDate.isBefore(endDate)) {
					count += this.janitorByDate.get(janitor).get(date).size();
				}
			}
			cleanedRoomsByJanitorCount.put(janitor, count);
		}
		return cleanedRoomsByJanitorCount;
	}
	
	public String findFreeJanitor() {
		HashMap<String, Integer> janitorRoomCount = new HashMap<String, Integer>();
		int smallest = Integer.MAX_VALUE;
		String janitorFound = "";
		for (String janitor : janitorByDate.keySet()) {
            int count = 0;
            for (String date : janitorByDate.get(janitor).keySet()) {
                count += janitorByDate.get(janitor).get(date).size();
            }
            janitorRoomCount.put(janitor, count);
        }
		for (String janitor : janitorRoomCount.keySet()) {
            if (janitorRoomCount.get(janitor) < smallest) {
                smallest = janitorRoomCount.get(janitor);
                janitorFound = janitor;
            }
        }
		return janitorFound;
	}
	
	public void readRoomsToBeCleaned(RoomManager roomManager) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath + "roomsToBeCleaned.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String user= data[0];
                Room room = roomManager.find(Integer.parseInt(data[1]));
                if (room != null) {
                    if (!roomsToBeCleaned.containsKey(user)) {
                        roomsToBeCleaned.put(user, new ArrayList<Room>());
                    }
                    roomsToBeCleaned.get(user).add(room);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading file roomsToBeCleaned.csv");
        }
    }
	
	public void readData(RoomManager roomManager) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath + "cleanedRooms.csv"));
			String line;
			this.readRoomsToBeCleaned(roomManager);
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				String janitor = data[0];
				String date = data[1];
				int roomNumber = Integer.parseInt(data[2]);
				Room room = roomManager.find(roomNumber);
				if (room != null) {
                    this.addRoom(janitor, date, room);
				}
			}
		} catch (Exception e) {
			System.out.println("Error reading file cleanedRooms.csv");
		}
	}
	
	public void writeRoomsToBeCleaned() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "roomsToBeCleaned.csv"));
			String line;
			for (String janitor : roomsToBeCleaned.keySet()) {
				for (Room room : roomsToBeCleaned.get(janitor)) {
					line = janitor + "," + String.valueOf(room.getNumber());
					writer.write(line);
					writer.newLine();
				}
			}
		} catch (Exception e) {
			System.out.println("Error writing to file roomsToBeCleaned.csv");
		}
	}
	
	public void writeData() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "cleanedRooms.csv"));
			String line;
			this.writeRoomsToBeCleaned();
			for (String janitor : janitorByDate.keySet()) {
                for (String date : janitorByDate.get(janitor).keySet()) {
                    for (Room room : janitorByDate.get(janitor).get(date)) {
                        line = janitor + "," + date + "," + String.valueOf(room.getNumber());
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
		} catch (Exception e) {
			System.out.println("Error writing to file");
		}	}
	
	
	
	
}
