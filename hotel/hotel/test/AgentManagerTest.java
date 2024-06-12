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

import manager.AgentManager;
import users.Agent;
import users.Education;
import users.Gender;

public class AgentManagerTest {

    private AgentManager agentManager;
    private Agent agent1;
    private Agent agent2;
    private Agent agent3;

    @Before
    public void setUp() throws Exception {
        agentManager = new AgentManager();
        agent1 = new Agent("ilijam", "ilijam123", "Ilija", "Maskovic", LocalDate.of(1980, 1, 1), 123456789, Gender.MALE, Education.BACHELOR, 5, 500);
        agent2 = new Agent("marijam", "marijam123", "Marija", "Markovic", LocalDate.of(1982, 2, 2), 987654321, Gender.FEMALE, Education.MASTER, 7, 700);
        agent3 = new Agent("bobank", "bobank123", "Boban", "Kostic", LocalDate.of(1984, 3, 3), 567890123, Gender.MALE, Education.PHD, 10, 1000, true);

        agentManager.add("ilijam", "ilijam123", "Ilija", "Maskovic", LocalDate.of(1980, 1, 1), 123456789, Gender.MALE, Education.BACHELOR, 5, 500);
        agentManager.add("marijam", "marijam123", "Marija", "Markovic", LocalDate.of(1982, 2, 2), 987654321, Gender.FEMALE, Education.MASTER, 7, 700);
        agentManager.add("bobank", "bobank123", "Boban", "Kostic", LocalDate.of(1984, 3, 3), 567890123, Gender.MALE, Education.PHD, 10, 1000, true);
    }

    @Test
    public void testFind() {
        assertEquals(agent1, agentManager.find("ilijam"));
        assertEquals(agent2, agentManager.find("marijam"));
        assertNull(agentManager.find("nonExistingUser"));
    }

    @Test
    public void testGetAgents() {
        HashMap<String, Agent> agents = agentManager.getAgents();
        assertEquals(3, agents.size());
        assertTrue(agents.containsKey("ilijam"));
        assertTrue(agents.containsKey("marijam"));
        assertTrue(agents.containsKey("bobank"));
    }

    @Test
    public void testGetAvailableAgents() {
        HashMap<String, Agent> availableAgents = agentManager.getAvailableAgents();
        assertEquals(2, availableAgents.size());
        assertTrue(availableAgents.containsKey("ilijam"));
        assertTrue(availableAgents.containsKey("marijam"));
        assertFalse(availableAgents.containsKey("bobank"));
    }

    @Test
    public void testAddAgent() {
        agentManager.add("newAgent", "newPassword", "New", "Agent", LocalDate.of(1990, 4, 4), 1122334455, Gender.FEMALE, Education.BACHELOR, 3, 300);
        Agent newAgent = agentManager.find("newAgent");
        assertNotNull(newAgent);
        assertEquals(new Agent("newAgent", "newPassword", "New", "Agent", LocalDate.of(1990, 4, 4), 1122334455, Gender.FEMALE, Education.BACHELOR, 3, 300), newAgent);
    }

    @Test
    public void testRemoveAgent() {
        agentManager.remove(agent1);
        assertFalse(agentManager.getAvailableAgents().containsKey("ilijam"));
    }
       

}
