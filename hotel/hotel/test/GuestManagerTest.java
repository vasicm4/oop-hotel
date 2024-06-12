package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import manager.GuestManager;
import users.Gender;
import users.Guest;

public class GuestManagerTest {
	
	private GuestManager guestManager;
	private Guest guest1;
	private Guest guest2;
	private Guest guest3;
	private Guest guest4;
	
	@Before
    public void setUp() {
		
		guestManager = new GuestManager();
        guest1 = new Guest("ilijam@gmail.com", "123456789", "Ilija", "Maskovic", LocalDate.of(1990, 1, 1), 123456789, Gender.MALE);
        guest2 = new Guest("marijam@gmail.com", "156574154", "Marija", "Maric", LocalDate.of(1992, 2, 2), 987654321, Gender.FEMALE);
        guest3 = new Guest("bobank@gmail.com", "854167284", "Boban", "Kulic", LocalDate.of(1994, 3, 3), 567890123, Gender.MALE, true);
        guest4 = new Guest("barbarab@gmail.com", "854167284", "Barbara", "Bobak", LocalDate.of(1994,5,5), 567891234,Gender.FEMALE, true);
        
        guestManager.add("ilijam@gmail.com", "123456789", "Ilija", "Maskovic", LocalDate.of(1990, 1, 1), 123456789, Gender.MALE);
        guestManager.add("marijam@gmail.com", "156574154", "Marija", "Maric", LocalDate.of(1992, 2, 2), 987654321, Gender.FEMALE);
        guestManager.add("bobank@gmail.com", "854167284", "Boban", "Kulic", LocalDate.of(1994, 3, 3), 567890123, Gender.MALE, true);
        guestManager.add("barbarab@gmail.com", "854167284", "Barbara", "Bobak", LocalDate.of(1994,5,5), 567891234,Gender.FEMALE, true);
        
	}

	@Test
	public void testFind() {
		assertEquals(guest1, guestManager.find("ilijam@gmail.com"));
		assertEquals(guest2, guestManager.find("marijam@gmail.com"));
		assertEquals(guest3, guestManager.find("bobank@gmail.com"));
		assertEquals(guest4, guestManager.find("barbarab@gmail.com"));
		assertNull(guestManager.find("markoperkovic@gmail.com"));
	}
	
	@Test 
	public void testGetGuests() {
		HashMap<String, Guest> guests = guestManager.getGuests();
		assertEquals(4, guests.keySet().size());
		assertEquals(guest1, guests.get("ilijam@gmail.com"));
		assertEquals(guest2, guests.get("marijam@gmail.com"));
		assertEquals(guest3, guests.get("bobank@gmail.com"));
		assertEquals(guest4, guests.get("barbarab@gmail.com"));
		assertNull(guests.get("maksimv@gmail.com"));
	}
	
	@Test
	public void testGetAvailableGuests() {
        HashMap<String, Guest> guests = guestManager.getAvailableGuests();
        assertEquals(2, guests.size());
		assertEquals(guest1, guests.get("ilijam@gmail.com"));
		assertEquals(guest2 ,guests.get("marijam@gmail.com"));
		assertNull(guests.get("bobank@gmail.com"));
		assertNull(guests.get("barbarab@gmail.com"));
	}
	
	@Test
    public void testAddGuest() {
        guestManager.add("newUser", "newPassword", "New", "User", LocalDate.of(2000, 4, 4), 1122334455, Gender.MALE);
        Guest newGuest = guestManager.find("newUser");
        assertNotNull(newGuest);
        assertEquals("newUser", newGuest.getUsername());
        assertEquals("newPassword", newGuest.getPassword());
        assertEquals("New", newGuest.getName());
        assertEquals("User", newGuest.getSurname());
        assertEquals(LocalDate.of(2000, 4, 4), newGuest.getDateOfBirth());
        assertEquals(1122334455, newGuest.getPhoneNumber());
        assertEquals(Gender.MALE, newGuest.getGender());
        
        guestManager.add("newUser2", "newPassword2", "New2", "User2", LocalDate.of(2000, 4, 4), 112155111, Gender.FEMALE);
        Guest newGuest2 = guestManager.find("newUser2");
        assertNotNull(newGuest2);
        assertEquals("newUser2", newGuest2.getUsername());
        assertEquals("newPassword2", newGuest2.getPassword());
        assertEquals("New2", newGuest2.getName());
        assertEquals("User2", newGuest2.getSurname());
        assertEquals(LocalDate.of(2000, 4, 4), newGuest2.getDateOfBirth());
        assertEquals(112155111, newGuest2.getPhoneNumber());
        assertEquals(Gender.FEMALE, newGuest2.getGender());
    }

	@Test
	public void testAddDeletedGuests() {
		guestManager.add("deletedUser", "deletedPassword", "Deleted", "User", LocalDate.of(1995, 5, 5), 556678899, Gender.FEMALE, true);
        Guest deletedGuest = guestManager.find("deletedUser");
        assertNotNull(deletedGuest);
        assertTrue(deletedGuest.isDeleted());
        
        guestManager.add("deletedUser2", "deletedPassword2", "Deleted2", "User2", LocalDate.of(1995, 5, 5), 55667899, Gender.MALE, false);
        Guest deletedGuest2 = guestManager.find("deletedUser2");
        assertNotNull(deletedGuest2);
        assertFalse(deletedGuest2.isDeleted()); 
	}
	
	@Test
	public void testRemoveGuest() {
		guestManager.remove(guest1);
		guestManager.remove(guest2);
		
		HashMap<String, Guest> guests = guestManager.getAvailableGuests();
		assertFalse(guests.containsKey("ilijam@gmail.com"));
		assertFalse(guests.containsKey("marijam@gmail.com"));
	}
}
