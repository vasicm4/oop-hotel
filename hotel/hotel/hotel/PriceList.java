package hotel;

import java.time.LocalDate;
import java.util.ArrayList;

import manager.ServiceManager;
import rooms.RoomType;

public class PriceList {
	protected LocalDate startDate;
	protected LocalDate endDate;
	protected ArrayList<Service> services;
	protected double[] roomPrices;
	protected boolean deleted;
	
	public PriceList(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.services = new ArrayList<Service>();
		this.deleted = false;
		this.roomPrices = new double[4];
	}
	
	public void addRoomPrice(RoomType roomType,double price) {
		roomPrices[roomType.getType(roomType)-1] = price;
	}
	
	public double getRoomPrice(RoomType roomType) {
		return roomPrices[roomType.getType(roomType) - 1];
	}
	
	public void changeRoomPrice(RoomType roomType, double price) {
		roomPrices[roomType.getType(roomType) - 1] = price;
	}
	
	public void addServicePrice(Service service, double price) {
		service.setPrice(price);
		this.services.add(service);
	}
	
	public void changeServicePrice(Service service, double price) {
		service.setPrice(price);
	}
	
	public void removeService(Service service) {
		this.services.remove(service);
	}
	
	public void setPrice(ServiceManager serviceManager,String type, double price) {
		serviceManager.find(type).setPrice(price);
	}

	public LocalDate getStartDate() {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public ArrayList<Service> getServices() {
		return services;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void delete() {
		deleted = true;
	}
}
