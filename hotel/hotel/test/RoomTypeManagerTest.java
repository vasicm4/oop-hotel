package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import manager.RoomTypeManager;
import rooms.RoomType;

public class RoomTypeManagerTest {

    private RoomTypeManager roomTypeManager;
    private RoomType roomType1;
    private RoomType roomType2;
    private RoomType roomType3;

    @Before
    public void setUp() {
        roomTypeManager = new RoomTypeManager();
        roomType1 = new RoomType("Single", 1, false);
        roomType2 = new RoomType("Double", 2, false);
        roomType3 = new RoomType("Triple", 3, true);

        roomTypeManager.add("Single", 1);
        roomTypeManager.add("Double", 2, false);
        roomTypeManager.add("Triple", 3, true);
    }

    @Test
    public void testGetRoomType() {
        assertEquals(roomType1, roomTypeManager.get("Single"));
        assertNull(roomTypeManager.get("Quadruple"));
    }

    @Test
    public void testGetAvailableRoomTypes() {
        HashMap<String, RoomType> availableRoomTypes = roomTypeManager.getAvailableRoomTypes();
        assertEquals(2, availableRoomTypes.size());
        assertTrue(availableRoomTypes.containsKey("Single"));
        assertTrue(availableRoomTypes.containsKey("Double"));
        assertFalse(availableRoomTypes.containsKey("Triple"));
    }

    @Test
    public void testAddRoomType() {
        roomTypeManager.add("Quadruple", 4);
        assertNotNull(roomTypeManager.get("Quadruple"));
    }

    @Test
    public void testAddRoomTypeWithDeleted() {
        roomTypeManager.add("Suite", 5, true);
        assertNotNull(roomTypeManager.get("Suite"));
    }

    @Test
    public void testRemoveRoomType() {
        roomTypeManager.remove(roomType1);
        assertNull(roomTypeManager.getAvailable("Single"));
    }

    @Test
    public void testChangeRoomType() {
        roomTypeManager.change("Single", 100);
        assertEquals(100, roomTypeManager.get("Single").getCapacity());
    }
}
