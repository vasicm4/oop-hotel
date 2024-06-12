package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import hotel.Service;
import manager.ServiceManager;

public class ServiceManagerTest {

	 private ServiceManager serviceManager;
	    private Service service1;
	    private Service service2;
	    private Service service3;

	    @Before
	    public void setUp() {
	        serviceManager = new ServiceManager();
	        service1 = new Service("WiFi", false);
	        service2 = new Service("Breakfast", false);
	        service3 = new Service("Gym", true);

	        serviceManager.add("WiFi");
	        serviceManager.add("Breakfast", false);
	        serviceManager.add("Gym", true);
	    }

	    @Test
	    public void testFindService() {
	        assertEquals(service1, serviceManager.find("WiFi"));
	        assertNull(serviceManager.find("Spa"));
	    }

	    @Test
	    public void testGetServices() {
	        HashMap<String, Service> services = serviceManager.getServices();
	        assertEquals(3, services.size());
	        assertTrue(services.containsKey("WiFi"));
	        assertTrue(services.containsKey("Breakfast"));
	        assertTrue(services.containsKey("Gym"));
	    }

	    @Test
	    public void testGetActiveServices() {
	        HashMap<String, Service> activeServices = serviceManager.getActiveServices();
	        assertEquals(2, activeServices.size());
	        assertTrue(activeServices.containsKey("WiFi"));
	        assertTrue(activeServices.containsKey("Breakfast"));
	        assertFalse(activeServices.containsKey("Gym"));
	    }

	    @Test
	    public void testAddService() {
	        serviceManager.add("Spa");
	        assertNotNull(serviceManager.find("Spa"));
	    }

	    @Test
	    public void testAddServiceWithDeleted() {
	        serviceManager.add("Pool", true);
	        assertNotNull(serviceManager.find("Pool"));
	    }

	    @Test
	    public void testRemoveService() {
	        serviceManager.remove(service1);
	        HashMap<String, Service> services = serviceManager.getActiveServices();
	        assertNull(services.get("WiFi"));
	    }

}
