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

import manager.AdminManager;
import users.Admin;
import users.Education;
import users.Gender;

public class AdminManagerTest {

	private AdminManager adminManager;
	private Admin admin1;
	private Admin admin2;
	private Admin admin3;
	
	@Before
	public void setUp() throws Exception {
		adminManager = new AdminManager();
        admin1 = new Admin("ilijam", "ilijam123", "Ilija", "Maskovic", LocalDate.of(1980, 1, 1), 123456789, Gender.MALE, Education.BACHELOR, 5, 500);
        admin2 = new Admin("marijam", "marijam123", "Marija", "Markovic", LocalDate.of(1982, 2, 2), 987654321, Gender.FEMALE, Education.MASTER, 7, 700);
        admin3 = new Admin("bobank", "bobank123", "Boban", "Kostic", LocalDate.of(1984, 3, 3), 567890123, Gender.MALE, Education.PHD, 10, 1000, true);

        adminManager.add("ilijam", "ilijam123", "Ilija", "Maskovic", LocalDate.of(1980, 1, 1), 123456789, Gender.MALE, Education.BACHELOR, 5, 500);
        adminManager.add("marijam", "marijam123", "Marija", "Markovic", LocalDate.of(1982, 2, 2), 987654321, Gender.FEMALE, Education.MASTER, 7, 700);
        adminManager.add("bobank", "bobank123", "Boban", "Kostic", LocalDate.of(1984, 3, 3), 567890123, Gender.MALE, Education.PHD, 10, 1000, true);
	}


    @Test
    public void testFind() {
        assertEquals(admin1, adminManager.find("ilijam"));
        assertEquals(admin2, adminManager.find("marijam"));
        assertNull(adminManager.find("nonExistingUser"));
    }

    @Test
    public void testGetAdmins() {
        HashMap<String, Admin> admins = adminManager.getAdmins();
        assertEquals(3, admins.size());
        assertTrue(admins.containsKey("ilijam"));
        assertTrue(admins.containsKey("marijam"));
        assertTrue(admins.containsKey("bobank"));
    }

    @Test
    public void testGetAvailableAdmins() {
        HashMap<String, Admin> availableAdmins = adminManager.getAvailableAdmins();
        assertEquals(2, availableAdmins.size());
        assertTrue(availableAdmins.containsKey("ilijam"));
        assertTrue(availableAdmins.containsKey("marijam"));
        assertFalse(availableAdmins.containsKey("bobank"));
    }

    @Test
    public void testAddAdmin() {
    	adminManager.add("newAdmin", "newPassword", "New", "Admin", LocalDate.of(1990, 4, 4), 1122334455, Gender.MALE, Education.MASTER, 3, 30000);
        Admin newAdmin = adminManager.find("newAdmin");
        assertNotNull(newAdmin);
        assertEquals(new Admin("newAdmin", "newPassword", "New", "Admin", LocalDate.of(1990, 4, 4), 1122334455, Gender.MALE, Education.MASTER, 3, 30000), newAdmin);
    }

    @Test
    public void testAddAdminWithDeleted() {
    	adminManager.add("deletedAdmin", "deletedPassword", "Deleted", "Admin", LocalDate.of(1995, 5, 5), 556677880, Gender.FEMALE, Education.BACHELOR, 5, 50000, true);
        Admin deletedAdmin = adminManager.find("deletedAdmin");
        assertNotNull(deletedAdmin);
        assertEquals(new Admin("deletedAdmin", "deletedPassword", "Deleted", "Admin", LocalDate.of(1995, 5, 5), 556677880, Gender.FEMALE, Education.BACHELOR, 5, 50000, true), deletedAdmin);
    }

    @Test
    public void testRemoveAdmin() {
    	adminManager.remove(admin1);
        assertFalse(adminManager.getAvailableAdmins().containsKey("ilijam"));
    }

}
