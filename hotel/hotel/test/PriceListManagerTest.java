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

import hotel.PriceList;
import hotel.Service;
import manager.PriceListManager;
import rooms.RoomType;

public class PriceListManagerTest {

    private PriceListManager priceListManager;
    private PriceList priceList1;
    private PriceList priceList2;
    private PriceList priceList3;

    @Before
    public void setUp() {
        priceListManager = new PriceListManager();
        HashMap<Service, Double> services1 = new HashMap<>();
        services1.put(new Service("Breakfast", false), 10.0);
        services1.put(new Service("Lunch", false), 20.0);
        HashMap<RoomType, Double> roomPrices1 = new HashMap<>();
        roomPrices1.put(new RoomType("Single", 1), 100.0);
        roomPrices1.put(new RoomType("Double", 2), 200.0);
        HashMap<Service, Double> services2 = new HashMap<>();
        services2.put(new Service("Dinner", false), 30.0);
        services2.put(new Service("Spa", true), 40.0);
        HashMap<RoomType, Double> roomPrices2 = new HashMap<>();
        roomPrices2.put(new RoomType("Single", 1), 150.0);
        roomPrices2.put(new RoomType("Double", 2), 250.0);
        HashMap<Service, Double> services3 = new HashMap<>();
        services3.put(new Service("Laundry", false), 50.0);
        services3.put(new Service("Pool", true), 60.0);
        HashMap<RoomType, Double> roomPrices3 = new HashMap<>();
        roomPrices3.put(new RoomType("Single", 1), 200.0);
        roomPrices3.put(new RoomType("Double", 2), 300.0);
        priceList1 = new PriceList(1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 15), services1, roomPrices1, false);
        priceList2 = new PriceList(2, LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 15), services2, roomPrices2, false);
        priceList3 = new PriceList(3, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 15), services3, roomPrices3, true);

        priceListManager.add(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 15), services1, roomPrices1);
        priceListManager.add(2, LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 15), services2, roomPrices2, false);
        priceListManager.add(3, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 15), services3, roomPrices3, true);
    }

    @Test
    public void testFindPriceList() {
        assertEquals(priceList1, priceListManager.find(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 15)));
        assertNull(priceListManager.find(LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 15)));
    }

    @Test
    public void testFindPriceListInRange() {
        assertEquals(priceList1, priceListManager.findRange(LocalDate.of(2023, 12, 1), LocalDate.of(2024, 2, 28)));
        assertNull(priceListManager.findRange(LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 15)));
    }

    @Test
    public void testGenerateID() {
        assertEquals(4, priceListManager.generateID());
    }

    @Test
    public void testGetPriceLists() {
        HashMap<String, PriceList> priceLists = priceListManager.getPriceLists();
        assertEquals(3, priceLists.size());
        assertTrue(priceLists.containsKey("1"));
        assertTrue(priceLists.containsKey("2"));
        assertTrue(priceLists.containsKey("3"));
    }

    @Test
    public void testGetActivePriceLists() {
        HashMap<String, PriceList> activePriceLists = priceListManager.getActivePriceLists();
        assertEquals(2, activePriceLists.size());
        assertTrue(activePriceLists.containsKey("1"));
        assertTrue(activePriceLists.containsKey("2"));
        assertFalse(activePriceLists.containsKey("3"));
    }

    @Test
    public void testAddPriceList() {
        HashMap<Service, Double> services = new HashMap<>();
        HashMap<RoomType, Double> roomPrices = new HashMap<>();
        priceListManager.add(LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 15), services, roomPrices);
        assertNotNull(priceListManager.find(LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 15)));
    }

    @Test
    public void testAddPriceListWithDeleted() {
        HashMap<Service, Double> services = new HashMap<>();
        HashMap<RoomType, Double> roomPrices = new HashMap<>();
        priceListManager.add(4, LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 15), services, roomPrices, true);
    }

    @Test
    public void testRemovePriceList() {
        priceListManager.remove(priceList1);
        assertNull(priceListManager.find(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 15)));
    }

}
