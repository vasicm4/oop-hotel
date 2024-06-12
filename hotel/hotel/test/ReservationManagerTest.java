package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import hotel.Service;
import manager.ManagerManager;
import manager.PriceListManager;
import manager.ReservationManager;
import rooms.Reservation;
import rooms.ReservationStatus;
import rooms.Room;
import rooms.RoomType;
import users.Gender;
import users.Guest;

public class ReservationManagerTest {

    private ReservationManager reservationManager;
    private Room room1, room2;
    private RoomType singleRoom, doubleRoom;
    private Guest guest1, guest2;
    private ArrayList<Service> services;
    private PriceListManager priceListManager;

    @Before
    public void setUp() {
        reservationManager = new ReservationManager();
        priceListManager = ManagerManager.getInstance().getPriceListManager();
        HashMap<Service, Double> services1 = new HashMap<>();
        services1.put(new Service("Breakfast", false), 10.0);
        services1.put(new Service("Lunch", false), 20.0);
        HashMap<RoomType, Double> roomPrices1 = new HashMap<>();
        roomPrices1.put(new RoomType("Single", 1), 100.0);
        roomPrices1.put(new RoomType("Double", 2), 200.0);
        priceListManager.add(1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 15), services1, roomPrices1, false);
        
        singleRoom = new RoomType("Single", 1);
        doubleRoom = new RoomType("Double", 2);
        
        room1 = new Room(singleRoom, 101, 1, new ArrayList<>());
        room2 = new Room(doubleRoom, 102, 1, new ArrayList<>());
        
        guest1 = new Guest("guest1", "password1", "John", "Doe", LocalDate.of(1990, 1, 1), 123456789, Gender.MALE);
        guest2 = new Guest("guest2", "password2", "Jane", "Doe", LocalDate.of(1992, 2, 2), 987654321, Gender.FEMALE);
        
        services = new ArrayList<>();
        services.add(new Service("WiFi"));
        
        reservationManager.add(LocalDate.of(2024, 6, 10), LocalDate.of(2024, 6, 20), room1, singleRoom, guest1, services, ReservationStatus.ACCEPTED, "1", false);
        reservationManager.add(LocalDate.of(2024, 6, 15), LocalDate.of(2024, 6, 25), room2, doubleRoom, guest2, services, ReservationStatus.ACCEPTED, "2", false);
    }

    @Test
    public void testFindReservationsByGuest() {
        ArrayList<Reservation> foundReservations = reservationManager.findReservations(guest1);
        assertEquals(1, foundReservations.size());
        assertEquals("1", foundReservations.get(0).getId());
    }

    @Test
    public void testFindReservationsByDateRange() {
        ArrayList<Reservation> foundReservations = reservationManager.findReservations(LocalDate.of(2024, 6, 10), LocalDate.of(2024, 6, 25));
        assertEquals(2, foundReservations.size());
    }

    @Test
    public void testGetAvailableReservations() {
        ArrayList<Reservation> availableReservations = reservationManager.getAvailableReservations();
        assertEquals(2, availableReservations.size());
    }

    @Test
    public void testGetDailyReservations() {
        HashMap<String, Reservation> dailyReservations = reservationManager.getDailyReservations(LocalDate.of(2024, 6, 10));
        assertTrue(dailyReservations.containsKey("1"));
    }

    @Test
    public void testGetRoomCount() {
        HashMap<Room, Integer> roomCount = reservationManager.getRoomCount(LocalDate.of(2024, 6, 10), LocalDate.of(2024, 6, 25));
        assertEquals(1, (int) roomCount.get(room1));
        assertEquals(1, (int) roomCount.get(room2));
    }

    @Test
    public void testFindReservation() {
        Reservation reservation = reservationManager.findReservation(guest1, LocalDate.of(2024, 6, 10), LocalDate.of(2024, 6, 20));
        assertNotNull(reservation);
        assertEquals("1", reservation.getId());
    }

    @Test
    public void testFindReservationString() {
        Reservation reservation = reservationManager.findReservationString("guest1", "2024-06-10", "2024-06-20");
        assertNotNull(reservation);
        assertEquals("1", reservation.getId());
    }

    @Test
    public void testGetOccupiedRooms() {
        int occupiedRooms = reservationManager.getOccupiedRooms(LocalDate.of(2024, 6, 15));
        assertEquals(2, occupiedRooms);
    }

    @Test
    public void testRoomAvailable() {
        HashMap<String, Room> rooms = new HashMap<>();
        rooms.put("101", room1);
        rooms.put("102", room2);
        
        Room availableRoom = reservationManager.roomAvailable(rooms, LocalDate.of(2024, 6, 26), LocalDate.of(2024, 7, 1), singleRoom);
        assertEquals(room1, availableRoom);
    }
    
    @Test
    public void testCancelExpiredReservations() {
        reservationManager.cancelExpiredReservations();
        for (Reservation reservation : reservationManager.getAvailableReservations()) {
            assertNotEquals(ReservationStatus.ON_HOLD, reservation.getStatus());
        }
    }

}
