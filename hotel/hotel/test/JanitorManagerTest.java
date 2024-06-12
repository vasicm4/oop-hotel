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

import manager.JanitorManager;
import users.Education;
import users.Gender;
import users.Janitor;

public class JanitorManagerTest {

    private JanitorManager janitorManager;
    private Janitor janitor1;
    private Janitor janitor2;
    private Janitor janitor3;

    @Before
    public void setUp() throws Exception {
        janitorManager = new JanitorManager();
        janitor1 = new Janitor("johnDoe", "johnDoe123", "John", "Doe", LocalDate.of(1980, 1, 1), 123456789, Gender.MALE, Education.BACHELOR, 5, 500);
        janitor2 = new Janitor("janeDoe", "janeDoe123", "Jane", "Doe", LocalDate.of(1982, 2, 2), 987654321, Gender.FEMALE, Education.MASTER, 7, 700);
        janitor3 = new Janitor("jackDoe", "jackDoe123", "Jack", "Doe", LocalDate.of(1984, 3, 3), 567890123, Gender.MALE, Education.PHD, 10, 1000, true);

        janitorManager.add("johnDoe", "johnDoe123", "John", "Doe", LocalDate.of(1980, 1, 1), 123456789, Gender.MALE, Education.BACHELOR, 5, 500);
        janitorManager.add("janeDoe", "janeDoe123", "Jane", "Doe", LocalDate.of(1982, 2, 2), 987654321, Gender.FEMALE, Education.MASTER, 7, 700);
        janitorManager.add("jackDoe", "jackDoe123", "Jack", "Doe", LocalDate.of(1984, 3, 3), 567890123, Gender.MALE, Education.PHD, 10, 1000, true);
    }

    @Test
    public void testFind() {
        assertEquals(janitor1, janitorManager.find("johnDoe"));
        assertEquals(janitor2, janitorManager.find("janeDoe"));
        assertNull(janitorManager.find("nonExistingUser"));
    }

    @Test
    public void testGetJanitors() {
        HashMap<String, Janitor> janitors = janitorManager.getJanitors();
        assertEquals(3, janitors.size());
        assertTrue(janitors.containsKey("johnDoe"));
        assertTrue(janitors.containsKey("janeDoe"));
        assertTrue(janitors.containsKey("jackDoe"));
    }

    @Test
    public void testGetAvailableJanitors() {
        HashMap<String, Janitor> availableJanitors = janitorManager.getAvailableJanitors();
        assertEquals(2, availableJanitors.size());
        assertTrue(availableJanitors.containsKey("johnDoe"));
        assertTrue(availableJanitors.containsKey("janeDoe"));
        assertFalse(availableJanitors.containsKey("jackDoe"));
    }

    @Test
    public void testAddJanitor() {
        janitorManager.add("newJanitor", "newPassword", "New", "Janitor", LocalDate.of(1990, 4, 4), 1122334455, Gender.MALE, Education.BACHELOR, 3, 300);
        Janitor newJanitor = janitorManager.find("newJanitor");
        assertNotNull(newJanitor);
        assertEquals(new Janitor("newJanitor", "newPassword", "New", "Janitor", LocalDate.of(1990, 4, 4), 1122334455, Gender.MALE, Education.BACHELOR, 3, 300), newJanitor);
    }

    @Test
    public void testAddJanitorWithDeleted() {
        janitorManager.add("deletedJanitor", "deletedPassword", "Deleted", "Janitor", LocalDate.of(1995, 5, 5), 55667788, Gender.FEMALE, Education.MASTER, 5, 500, true);
        Janitor deletedJanitor = janitorManager.find("deletedJanitor");
        assertNotNull(deletedJanitor);
        assertEquals(new Janitor("deletedJanitor", "deletedPassword", "Deleted", "Janitor", LocalDate.of(1995, 5, 5), 55667788, Gender.FEMALE,Education.MASTER, 5, 500, true), deletedJanitor);
    }

    @Test
    public void testRemoveJanitor() {
        janitorManager.remove(janitor1);
        assertFalse(janitorManager.getAvailableJanitors().containsKey("johnDoe"));
    }
}
