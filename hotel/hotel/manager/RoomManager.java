package manager;

import java.util.ArrayList;

import rooms.Room;
import rooms.RoomType;

public class RoomManager {
	protected ArrayList<Room> _rooms;
	protected String _fileName;
	protected String _filePath;
	
	public RoomManager() {
		_rooms = new ArrayList<Room>();
		_fileName = "_rooms.txt";
		_filePath = "data/";
	}
	
	public Room find(RoomType type) {
		for (Room room : _rooms) {
			if (room.getType().equals(type)) {
				return room;
			}
		}
		System.out.println("Room not found");
		return null;
	}
	
	public ArrayList<Room> getRooms() {
		return _rooms;
	}
	
	public void add(RoomType type, int number, int floor) {
		this._rooms.add(new Room(type, number, floor));
	}
	
	public void remove(Room room) {
		room.delete();
	}
	
}
