package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import manager.CleaningManager;
import manager.RoomTypeManager;
import rooms.Room;
import rooms.RoomStatus;
import rooms.RoomType;

public class CleaningManagerTest {

	private CleaningManager cleaningManager;
	private RoomTypeManager roomTypeManager; 
    private Room room1;
    private Room room2;
    private Room room3;

    @Before
    public void setUp() {
    	cleaningManager = new CleaningManager();
        roomTypeManager = new RoomTypeManager();
        
        roomTypeManager.add("Single", 1);
        roomTypeManager.add("Double", 2);
        roomTypeManager.add("Triple", 3);
    	
        room1 = new Room(roomTypeManager.get("Single"), 101, 1, RoomStatus.CLEANED, new ArrayList<>(), false);
        room2 = new Room(roomTypeManager.get("Double"), 102, 1, RoomStatus.CLEANED, new ArrayList<>(), false);
        room3 = new Room(roomTypeManager.get("Triple"), 103, 1, RoomStatus.CLEANED, new ArrayList<>(), false);

        cleaningManager.addRoom("janitor1", "2024-06-11", room1);
        cleaningManager.addRoom("janitor2", "2024-06-11", room2);
        cleaningManager.addRoom("janitor2", "2024-06-11", room3);
        
        cleaningManager.addDailyTask("janitor1", room1);
        cleaningManager.addDailyTask("janitor2", room2);
        cleaningManager.addDailyTask("janitor2", room3);
    }

    @Test
    public void testAddDailyTask() {
    	cleaningManager.addDailyTask("janitor1", room1);
        assertTrue(cleaningManager.getRoomsToBeCleaned().containsKey("janitor1"));
        assertTrue(cleaningManager.getRoomsToBeCleaned().get("janitor1").contains(room1));
    }

    @Test
    public void testCleanRoom() {
    	cleaningManager.cleanRoom("janitor1", room1);
        assertEquals(RoomStatus.AVAILABLE, room1.getStatus());
        assertFalse(cleaningManager.getRoomsToBeCleaned().get("janitor1").contains(room1));
        assertTrue(cleaningManager.getJanitorByDate().get("janitor1").get("2024-06-12").contains(room1));
    }

    @Test
    public void testAddRoom() {
        Room room4 = new Room(roomTypeManager.get("Single"), 104, 1, RoomStatus.CLEANED, new ArrayList<>(), false);
        cleaningManager.addRoom("janitor1", "2024-06-12", room4);
        assertTrue(cleaningManager.getJanitorByDate().get("janitor1").get("2024-06-12").contains(room4));
    }


}
