package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import hotel.Service;
import rooms.Reservation;
import rooms.ReservationStatus;
import rooms.Room;
import rooms.RoomType;
import users.Guest;

public class ReservationManager {
	private static ReservationManager instance;
	protected HashMap<String, Reservation> reservations;
	protected String fileName;
	protected String filePath;
	protected String fileServices;
	
	public static ReservationManager getInstance() {
		if (instance == null) {
			instance = new ReservationManager();
		}
		return instance;
	}
	
	public HashMap<String, Reservation> getReservations() {
		return reservations;
	}
	
	
	public ReservationManager() {
		reservations = new HashMap<String, Reservation>();
		fileName = "reservations.csv";
		filePath = "data" + System.getProperty("file.separator");
		fileServices = "reservationService.csv";
	}
		
	public String generateId() {
		return String.valueOf(reservations.size() + 1);
	}
	
	public void add(LocalDate CheckIn, LocalDate CheckOut, Room room, RoomType roomType, Guest guest, ArrayList<Service> services, ReservationStatus status, String id, boolean deleted) {
        this.reservations.put(id, new Reservation(CheckIn, CheckOut, room, roomType, guest, services, status, id, deleted));
	}

	public void add(LocalDate CheckIn, LocalDate CheckOut, RoomType roomType, Guest guest, ArrayList<Service> services, ReservationStatus status, boolean deleted) {
		String id = this.generateId();
		this.reservations.put(id, new Reservation(CheckIn, CheckOut, roomType, guest, services, status, id, deleted));
	}

	public ArrayList<Reservation> findReservations(Guest guest) {
		ArrayList<Reservation> reservationsFound = new ArrayList<Reservation>();
		for (Reservation reservation : reservations.values()) {
			if (reservation.getGuest().equals(guest) && !reservation.isDeleted()) {
				reservationsFound.add(reservation);
			}
		}
		return reservationsFound;
	}
	
	public ArrayList<Reservation> findReservations(LocalDate CheckIn,LocalDate CheckOut) {
		ArrayList<Reservation> reservationsFound = new ArrayList<Reservation>();
		for (Reservation reservation : reservations.values()) {
			if (reservation.getCheckIn().compareTo(CheckIn)>=0  && reservation.getCheckOut().compareTo(CheckOut)<=0 && !reservation.isDeleted()) {
				reservationsFound.add(reservation);
			}
		}
		return reservationsFound;
	}
	
	public ArrayList<Reservation> getAvailableReservations() {
		ArrayList<Reservation> reservationsFound = new ArrayList<Reservation>();
		for (Reservation reservation : reservations.values()) {
			if (!reservation.isDeleted()) {
				reservationsFound.add(reservation);
			}
		}
		return reservationsFound;
	}
	
	public HashMap<String, Reservation> getDailyReservations(LocalDate date) {
		HashMap<String, Reservation> reservationsFound = new HashMap<String, Reservation>();
		for (Reservation reservation : reservations.values()) {
			if (reservation.getStatus() == ReservationStatus.CHECKED_IN || reservation.getStatus() == ReservationStatus.CHECKED_OUT || reservation.getStatus() == ReservationStatus.ACCEPTED) {
				if (reservation.getCheckIn().compareTo(date) == 0 || reservation.getCheckOut().compareTo(date) == 0) {
					reservationsFound.put(reservation.getId(), reservation);
				}
			}
		}
		return reservationsFound;
	}
	
	public HashMap<Room, Integer> getRoomCount(LocalDate startDate, LocalDate endDate){
		HashMap<Room, Integer> roomCount = new HashMap<Room, Integer>();
		for (Reservation reservation : reservations.values()) {
			if (reservation.getCheckIn().compareTo(startDate) >= 0 && reservation.getCheckOut().compareTo(endDate) <= 0) {
				if (roomCount.containsKey(reservation.getRoom())) {
					roomCount.put(reservation.getRoom(), roomCount.get(reservation.getRoom()) + 1);
				} else {
					roomCount.put(reservation.getRoom(), 1);
				}
			}
		}
		for (Room room : ManagerManager.getRoomManager().getRooms().values()) {
			if (roomCount.get(room) == null) {
				roomCount.put(room, 0);
			}
		}
		return roomCount;
	}
	
	public Reservation findReservation(Guest guest, LocalDate CheckIn,LocalDate CheckOut) {
		for (Reservation reservation :reservations.values()) {
			if (reservation.getGuest().equals(guest) && reservation.getCheckIn().compareTo(CheckIn)==0  && reservation.getCheckOut().compareTo(CheckOut)==0 && !reservation.isDeleted()) {
				return reservation;
			}
		}
		return null;
	}
	
	public Reservation findReservationString(String username, String CheckIn, String CheckOut) {
        for (Reservation reservation :reservations.values()) {
            if (reservation.getGuest().getUsername().equals(username) && reservation.getCheckIn().toString().equals(CheckIn) && reservation.getCheckOut().toString().equals(CheckOut) && !reservation.isDeleted()) {
                return reservation;
            }
        }
        return null;
    }
	
	public int getOccupiedRooms(LocalDate date) {
		int occupiedRooms = 0;
		for (Reservation reservation : reservations.values()) {
			if (reservation.getCheckIn().compareTo(date) <= 0 && reservation.getCheckOut().compareTo(date) >= 0) {
				occupiedRooms++;
			}
		}
		return occupiedRooms;
	}

	public Double getTotalEarnings(LocalDate startDate,LocalDate endDate) {
		double totalEarnings = 0;
		for (Reservation reservation : reservations.values()) {
			if (!reservation.getCheckIn().isBefore(startDate) && reservation.getCheckOut().isAfter(endDate) && !reservation.isDeleted() ) {
				totalEarnings += reservation.getPrice(ManagerManager.getPriceListManager());
			}
		}
		return totalEarnings;
	}
	
	public Room roomAvailable(HashMap<String, Room> rooms, LocalDate CheckIn, LocalDate CheckOut, RoomType roomType) {
	    for (Room room : rooms.values()) {
	        if (room.getType().equals(roomType)) {
	            boolean isAvailable = true;
	            for (Reservation reservation : this.reservations.values()) {
	                if (reservation.getRoom() == room && !(reservation.getCheckOut().isBefore(CheckIn) || reservation.getCheckIn().isAfter(CheckOut))) {
	                    isAvailable = false;
	                    break;
	                }
	            }
	            if (isAvailable) {
	                return room;
	            }
	        }
	    }
	    return null;
	}
	
	public HashMap<Room,Double> getRoomsRevenue(LocalDate startDate, LocalDate endDate){
		HashMap<Room, Double> roomsRevenue = new HashMap<>();
		boolean found = false;

		// Populate roomsRevenue with rooms having reservations in the specified date range
		for (Reservation reservation : reservations.values()) {
		    if (reservation.getCheckIn().compareTo(startDate) >= 0 && reservation.getCheckOut().compareTo(endDate) <= 0 && !reservation.isDeleted() && reservation.getStatus() == ReservationStatus.CHECKED_OUT) {
		        Room room = reservation.getRoom();
		        double revenue = reservation.getPrice(ManagerManager.getPriceListManager());
		        roomsRevenue.put(room, roomsRevenue.getOrDefault(room, 0.0) + revenue);
		        found = true;
		    }
		}

		// Populate roomsRevenue with rooms having no reservations in the specified date range
		for (Room room : ManagerManager.getRoomManager().getRooms().values()) {
		    if (!roomsRevenue.containsKey(room)) {
		        roomsRevenue.put(room, 0.0);
		    }
		}
		return roomsRevenue;

	}
	
	public void cancelExpiredReservations() {
		for (Reservation reservation : reservations.values()) {
			if (reservation.getCheckOut().isBefore(LocalDate.now()) && reservation.getStatus() == ReservationStatus.ON_HOLD) {
				reservation.cancel();
			}
		}
	}
	
	public double allReservationsExpenses(Guest guest) {
		double expenses = 0;
		for (Reservation reservation : reservations.values()) {
			if (reservation.getGuest().equals(guest)) {
				expenses += reservation.getPrice(ManagerManager.getPriceListManager());
			}
		}
		return expenses;
	}
	
	public void writeServices() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + fileServices));
			for (Reservation reservation : reservations.values()) {
				for (Service service : reservation.getServices()) {
					writer.write(reservation.getId() + "," + service.getType() + "\n");
				}
			}
			writer.flush();
		} catch (Exception e) {
			System.out.println("Error writing to file");
		}
	}
	
	public void writeData() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + fileName));
			this.writeServices();
			for (Reservation reservation : reservations.values()) {
				if (reservation.getRoom() == null) {
                    writer.write(String.valueOf(reservation.getCheckIn()) + "," + String.valueOf(reservation.getCheckOut()) + ",null," + reservation.getRoomType().getType() + "," + String.valueOf(reservation.getGuest().getUsername()) + "," 
                            + ReservationStatus.getStatus(reservation.getStatus()) + "," + String.valueOf(reservation.getId()) + "," + String.valueOf(reservation.isDeleted()) + "\n");
                } else {
				writer.write(String.valueOf(reservation.getCheckIn()) + "," + String.valueOf(reservation.getCheckOut()) + ","
						+ String.valueOf(reservation.getRoom().getNumber()) + "," + reservation.getRoomType().getType() + "," + String.valueOf(reservation.getGuest().getUsername()) + "," 
						+ ReservationStatus.getStatus(reservation.getStatus()) + "," + String.valueOf(reservation.getId()) + "," + String.valueOf(reservation.isDeleted()) + "\n");
                }
            }
			writer.flush();
		} catch (Exception e) {
			System.out.println("Error writing to file" + fileName);
		}
	}
	
	
	public ArrayList<Service> readServices(ServiceManager serviceManager, String id) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath + fileServices));
			String line;
			ArrayList<Service> services = new ArrayList<Service>();
			while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
				if (data[0].equals(id)) {
					services.add(serviceManager.find(data[1]));
				}
            }	
            return services;
		} catch (Exception e) {
			System.out.println("Error reading file" + fileServices);
		}
		return null;
	}
	
	public void readData(RoomManager roomManager, GuestManager guestManager, ServiceManager serviceManager, RoomTypeManager roomTypeManager) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath + fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				for (int i = 0; i < data.length; i++) {
					data[i] = data[i].trim();
				}
				ArrayList<Service> services = readServices(serviceManager, data[6]);
				if (data[2].equals("null")){
					this.add(LocalDate.parse(data[0]), LocalDate.parse(data[1]), null, roomTypeManager.getRoomType(data[3]),guestManager.find(data[4]), services, ReservationStatus.getStatus(data[5]), data[6],Boolean.parseBoolean(data[7]));
				} else {
					this.add(LocalDate.parse(data[0]), LocalDate.parse(data[1]), roomManager.find(Integer.parseInt(data[2])), roomTypeManager.get(data[3]), guestManager.find(data[4]), services, ReservationStatus.getStatus(data[5]), data[6], Boolean.parseBoolean(data[7]));
				}
			}
			this.cancelExpiredReservations();
			
		} catch (Exception e) {
			System.out.println("Error reading file" + fileName);
		}
	}
}
