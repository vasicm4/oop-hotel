package manager;

public class ManagerManager {
	private static ManagerManager instance;
	
	//loading all users
	public static GuestManager guestManager = GuestManager.getInstance();
	public static AdminManager adminManager = AdminManager.getInstance();
	public static AgentManager agentManager = AgentManager.getInstance();
	public static JanitorManager janitorManager = JanitorManager.getInstance();
	public static ServiceManager serviceManager = ServiceManager.getInstance();
	public static RoomTypeManager roomTypeManager = RoomTypeManager.getInstance();
	public static RoomManager roomManager = RoomManager.getInstance();
	public static PriceListManager priceListManager = PriceListManager.getInstance();
	public static ReservationManager reservationManager = ReservationManager.getInstance();
	public static CleaningManager cleaningManager = CleaningManager.getInstance();
	
	public static GuestManager getGuestManager() {
		return guestManager;
	}

	public static AdminManager getAdminManager() {
		return adminManager;
	}

	public static AgentManager getAgentManager() {
		return agentManager;
	}

	public static JanitorManager getJanitorManager() {
		return janitorManager;
	}

	public static ServiceManager getServiceManager() {
		return serviceManager;
	}

	public static RoomTypeManager getRoomTypeManager() {
		return roomTypeManager;
	}

	public static RoomManager getRoomManager() {
		return roomManager;
	}

	public static PriceListManager getPriceListManager() {
		return priceListManager;
	}

	public static ReservationManager getReservationManager() {
		return reservationManager;
	}
	
	public static CleaningManager getCleaningManager() {
		return cleaningManager;
	}

	//loading all services
	public static void loadServices() {
		serviceManager.readData();
		adminManager.readData();
		agentManager.readData();
		guestManager.readData();
		janitorManager.readData();
		roomTypeManager.readData();
		roomManager.readData(roomTypeManager);
		priceListManager.readData(serviceManager, roomTypeManager);
		reservationManager.readData(roomManager, guestManager, serviceManager, roomTypeManager);
		cleaningManager.readData(roomManager);
	}
	
	//saving all services
	public static void saveServices() {
		serviceManager.writeData();
		adminManager.writeData();
		agentManager.writeData();
		guestManager.writeData();
		janitorManager.writeData();
		roomTypeManager.writeData();
		roomManager.writeData();
		priceListManager.writeData();
		reservationManager.writeData();
		cleaningManager.writeData();
	}
	
	public static String login(String username, char[] password) {
		if (ManagerManager.adminManager.find(username) != null && ManagerManager.adminManager.find(username).isDeleted() == false) {
			if (ManagerManager.adminManager.find(username).getPassword().equals(String.valueOf(password))) {
				return "admin";
			} else {
				return "incorrect password";
			}
		}
		if (ManagerManager.agentManager.find(username) != null && ManagerManager.agentManager.find(username).isDeleted() == false) {
			if (ManagerManager.agentManager.find(username).getPassword().equals(String.valueOf(password))) {
				return "agent";
			} else {
				return "incorrect password";
			}
		}
		if (ManagerManager.janitorManager.find(username) != null && ManagerManager.janitorManager.find(username).isDeleted() == false) {
			if (ManagerManager.janitorManager.find(username).getPassword().equals(String.valueOf(password))) {
				return "janitor";
			} else {
				return "incorrect password";
			}
		}
		if (ManagerManager.guestManager.find(username) != null && ManagerManager.guestManager.find(username).isDeleted() == false) {
			if (ManagerManager.guestManager.find(username).getPassword().equals(String.valueOf(password))) {
				return "guest";
			} else {
				return "password";
			}
		}
		return "username";
	}
	
	public static ManagerManager getInstance() {
		if (instance == null) {
			instance = new ManagerManager();
		}
		return instance;
	}
	
}
