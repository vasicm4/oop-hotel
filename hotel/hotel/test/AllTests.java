package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AdminManagerTest.class, AgentManagerTest.class, CleaningManagerTest.class, GuestManagerTest.class,
		JanitorManagerTest.class, PriceListManagerTest.class, ReservationManagerTest.class, RoomManagerTest.class,
		RoomTypeManagerTest.class, ServiceManagerTest.class })
public class AllTests {

}
