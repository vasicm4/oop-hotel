package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import manager.RoomManager;
import manager.RoomTypeManager;
import rooms.Room;
import rooms.RoomStatus;
import rooms.RoomType;

public class RoomManagerTest {

	private RoomManager roomManager;
	private RoomTypeManager roomTypeManager; 
    private Room room1;
    private Room room2;
    private Room room3;

    @Before
    public void setUp() {
        roomManager = new RoomManager();
        roomTypeManager = new RoomTypeManager();
        
        roomTypeManager.add("Single", 1);
        roomTypeManager.add("Double", 2);
        roomTypeManager.add("Triple", 3);
        roomTypeManager.add("Quadruple", 4);
        
        room1 = new Room(roomTypeManager.get("Single"), 1, 1, RoomStatus.AVAILABLE, new ArrayList<>(), false);
        room2 = new Room(roomTypeManager.get("Double"), 2, 1, RoomStatus.OCCUPIED, new ArrayList<>(), false);
        room3 = new Room(roomTypeManager.get("Triple"), 3, 1, RoomStatus.AVAILABLE, new ArrayList<>(), true);

        roomManager.add(roomTypeManager.get("Single"), 1, 1, RoomStatus.AVAILABLE, new ArrayList<>(), false);
        roomManager.add(roomTypeManager.get("Double"), 2, 1, RoomStatus.OCCUPIED, new ArrayList<>(), false);
        roomManager.add(roomTypeManager.get("Triple"), 3, 1, RoomStatus.AVAILABLE, new ArrayList<>(), true);
    }

    @Test
    public void testFindRoomByNumber() {
        assertEquals(room1, roomManager.find(1));
        assertNull(roomManager.find(4));
    }

    @Test
    public void testGetRooms() {
        HashMap<String, Room> rooms = roomManager.getRooms();
        assertEquals(2, rooms.size());
        assertTrue(rooms.containsKey("1"));
        assertTrue(rooms.containsKey("2"));
        assertFalse(rooms.containsKey("3")); // Room 3 is marked as deleted
    }

    @Test
    public void testGenerateNumber() {
        assertEquals(4, roomManager.generateNumber());
    }

    @Test
    public void testAddRoom() {
        roomManager.add(roomTypeManager.get("Quadruple"), 4, new ArrayList<>());
        assertNotNull(roomManager.find(4));
    }

    @Test
    public void testAddRoomWithDeleted() {
        roomManager.add(roomTypeManager.get("Single"), 5, 1, RoomStatus.AVAILABLE, new ArrayList<>(), true);
        assertNotNull(roomManager.find(5));
    }

    @Test
    public void testGetAllAdditionalServices() {
        ArrayList<String> additionalServices = roomManager.getAllAdditionalServices();
        assertEquals(0, additionalServices.size()); // No additional services initially
    }

    @Test
    public void testRemoveRoom() {
        roomManager.remove(room1);
        HashMap<String, Room> rooms = roomManager.getRooms();
        assertEquals(1, rooms.size());
        assertFalse(rooms.containsKey("1"));
    }

    @Test
    public void testFindRoomTypes() {
        ArrayList<String> additionalServices = new ArrayList<>();
        additionalServices.add("WiFi");
        additionalServices.add("Breakfast");

        ArrayList<String> roomTypes = roomManager.findRoomTypes(additionalServices);
        assertEquals(0, roomTypes.size()); // No room with both WiFi and Breakfast initially
    }
}
