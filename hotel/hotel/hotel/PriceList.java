package hotel;

import java.time.LocalDate;
import java.util.HashMap;



import rooms.RoomType;

public class PriceList {
	protected LocalDate startDate;
	protected LocalDate endDate;
	protected HashMap<Service, Double> services;
	protected HashMap<RoomType, Double> roomPrices;
	protected boolean deleted;
	protected int id;
	
	public PriceList(int id, LocalDate startDate, LocalDate endDate, HashMap<Service, Double> services, HashMap<RoomType, Double> roomPrices) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.services = services;
		this.roomPrices = roomPrices;
		this.id = id;
		this.deleted = false;
	}
	
	public PriceList(int id, LocalDate startDate, LocalDate endDate, HashMap<Service, Double> services, HashMap<RoomType, Double> roomPrices, boolean deleted) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.services = services;
		this.deleted = false;
		this.roomPrices = roomPrices;
		this.id = id;
		this.deleted = deleted;
	}
	
	public String servicesToString() {
		String result = "";
		for (Service service : services.keySet()) {
			result += service.getType() + " " + services.get(service) + ",";
		}
		return result;
	}
	
	public String roomPricesToString() {
		String result = "";
		for (RoomType roomType : roomPrices.keySet()) {
			result += roomType.getType() + " " + roomPrices.get(roomType) + ",";
		}
		return result;
	}
	
	public boolean equals(Object o) {
		if (o instanceof PriceList) {
			PriceList priceList = (PriceList) o;
			return this.startDate.equals(priceList.getStartDate()) && this.endDate.equals(priceList.getEndDate()) && this.services.equals(priceList.getServices()) && this.roomPrices.equals(priceList.getRoomPrices()) && this.deleted == priceList.isDeleted() && this.id == priceList.getId();
		}
		return false;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addRoomPrice(RoomType roomType,double price) {
		this.roomPrices.put(roomType, price);
	}
	
	public double getRoomPrice(RoomType roomType) {
		return roomPrices.get(roomType);
	}
	
	public void changeRoomPrice(RoomType roomType, double price) {
		roomPrices.replace(roomType, price);
	}
	
	public void addServicePrice(Service service, double price) {
		this.services.put(service, price);
	}
	
	public void changeServicePrice(Service service, double price) {
		this.services.replace(service, price);
	}
	
	public double getServicePrice(Service service) {
		return services.get(service);
	}
	
	public void removeService(Service service) {
		this.services.remove(service);
	}

	public LocalDate getStartDate() {
		return this.startDate;
	}
	
	public LocalDate getEndDate() {
		return this.endDate;
	}
	
	public HashMap<Service, Double> getServices() {
		return this.services;
	}
	
	public HashMap<RoomType, Double> getRoomPrices() {
		return this.roomPrices;
	}
	
	public void setServices(HashMap<Service, Double> services) {
		this.services = services;
	}
	
	public void setRoomPrices(HashMap<RoomType, Double> roomPrices) {
		this.roomPrices = roomPrices;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public boolean isDeleted() {
		return this.deleted;
	}
	
	public void delete() {
		this.deleted = true;
	}
}
