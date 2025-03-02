package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.HashMap;

import hotel.PriceList;
import hotel.Service;
import rooms.RoomType;

public class PriceListManager {
	private static PriceListManager instance;
	protected HashMap<String, PriceList> priceLists;
	protected String fileName;
	protected String filePath;
	protected String fileServices;
	protected String fileRoomTypes;
	
	
	public static PriceListManager getInstance() {
		if (instance == null) {
			instance = new PriceListManager();
		}
		return instance;
	}
	
	public PriceListManager() {
		priceLists = new HashMap<String, PriceList>();
		fileName = "priceLists.csv";
		filePath = "data" + System.getProperty("file.separator");
		fileServices = "serviceServicePrice.csv";
		fileRoomTypes = "roomTypeRoomPrice.csv";
	}
	
	public PriceList find(LocalDate startDate, LocalDate endDate) {
		for (PriceList priceList : priceLists.values()) {
			if (priceList.getStartDate().equals(startDate) && priceList.getEndDate().equals(endDate) && !priceList.isDeleted()) {
				return priceList;
			}
		}
		return null;
	}
	
	public PriceList findRange(LocalDate startDate, LocalDate endDate) {
		for (PriceList priceList : priceLists.values()) {
			if (priceList.getStartDate().isBefore(endDate) && priceList.getEndDate().isAfter(startDate) && !priceList.isDeleted()) {
				return priceList;
			}
		}
		return null;
	}
	
	public int generateID() {
		if ( priceLists.size() == 0) {
			return 1;
		}
		else {
			return priceLists.size() + 1;
		}
	}
	
	public HashMap<String, PriceList> getPriceLists() {
		return priceLists;
	}
	
	public HashMap<String, PriceList> getActivePriceLists() {
		HashMap<String, PriceList> activePriceLists = new HashMap<String, PriceList>();
		for (PriceList priceList : priceLists.values()) {
			if (!priceList.isDeleted()) {
				activePriceLists.put(String.valueOf(priceList.getId()), priceList);
			}
		}
		return activePriceLists;
	}
	
	public void add(LocalDate startDate, LocalDate endDate, HashMap<Service, Double> services, HashMap<RoomType, Double> roomPrices) {
		int id = this.generateID();
		this.priceLists.put(String.valueOf(id), new PriceList(id ,startDate, endDate, services, roomPrices));
	}
	
	public void add(int id, LocalDate startDate, LocalDate endDate, HashMap<Service, Double> services, HashMap<RoomType, Double> roomPrices, boolean deleted) {
		this.priceLists.put(String.valueOf(id), new PriceList(id, startDate, endDate,  services, roomPrices, deleted));
	}
	
	public void remove(PriceList priceList) {
		for (PriceList p : priceLists.values()) {
			if (p.equals(priceList)) {
				p.delete();
			}
		}
	}
	
	public void writeServices() {
        try {
            FileWriter writer = new FileWriter(filePath + "serviceServicePrice.csv");
            for (PriceList priceList: priceLists.values()) {
            	for (Service service: priceList.getServices().keySet()) {
                    writer.write(String.valueOf(priceList.getId()) + "," + service.getType() + "," + String.valueOf(priceList.getServices().get(service)) + "\n");

            	}
            }
            writer.flush();
        } catch (Exception e) {
            System.out.println("Error writing to file");
        }
    }
	
	public void writeRoomTypes() {
		try {
			FileWriter writer = new FileWriter(filePath + fileRoomTypes);
			for (PriceList priceList : priceLists.values()) {
				for (RoomType roomType: priceList.getRoomPrices().keySet()) {
					writer.write(String.valueOf(priceList.getId()) + "," + roomType.getType() + "," + String.valueOf(priceList.getRoomPrices().get(roomType)) + "\n");
				}
			}			
			writer.flush();
		} catch (Exception e) {
			System.out.println("Error writing to file");
		}
	}
	
	public void writeData() {
		try {
			this.writeRoomTypes();
			this.writeServices();
			FileWriter writer = new FileWriter(filePath + fileName);
			for (PriceList priceList : priceLists.values()) {
				writer.write(String.valueOf(priceList.getId()) + "," + String.valueOf(priceList.getStartDate()) + "," + String.valueOf(priceList.getEndDate()) + "," + String.valueOf(priceList.isDeleted()) + "\n");
			}
			writer.flush();
		} catch (Exception e) {
			System.out.println("Error writing to file" + fileName);
		}
	}
	
	
	public HashMap<Service, Double> readServices(String id, ServiceManager serviceManager) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath + fileServices));
			String line;
			HashMap<Service, Double> services = new HashMap<Service, Double>();
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data[0].equals(id)) {
					services.put(serviceManager.find(data[1]), Double.parseDouble(data[2]));
				}
			}
			return services;
		} catch (Exception e) {
			System.out.println("Error reading from file" + serviceManager);
			return null;
		}
	}
	
	public HashMap<RoomType, Double> readRoomTypes(String id,RoomTypeManager roomTypeManager) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath + fileRoomTypes));
			String line;
			HashMap<RoomType, Double> roomTypes = new HashMap<RoomType, Double>();
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data[0].equals(id)) {
					roomTypes.put(roomTypeManager.get(data[1]), Double.parseDouble(data[2]));
				}
			}
			return roomTypes;
		} catch (Exception e) {
			System.out.println("Error reading file" + fileRoomTypes);
			return null;
		}
	}
	
	
	public void readData(ServiceManager serviceManager, RoomTypeManager roomTypeManager) {
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(filePath + fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				HashMap<Service, Double> services = this.readServices(data[0], ServiceManager.getInstance());
				HashMap<RoomType, Double> roomTypes = this.readRoomTypes(data[0], RoomTypeManager.getInstance());
				this.add(Integer.parseInt(data[0]), LocalDate.parse(data[1]), LocalDate.parse(data[2]), services, roomTypes, Boolean.parseBoolean(data[3]));
			}
		
		}catch(Exception e) {
			System.out.println("Error reading file" + fileName);
		}
		
	}
}
