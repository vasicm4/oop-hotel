package manager;

import java.util.ArrayList;

import hotel.Service;

public class ServiceManager {
	ArrayList<Service> services;
	String fileName;
	String filePath;

	public ServiceManager() {
		services = new ArrayList<Service>();
		fileName = "services.txt";
		filePath = "data"+ System.getProperty("file.separator");
	}

	public Service find(String type) {
		for (Service service : services) {
			if (service.getType().equals(type)) {
				return service;
			}
		}
		System.out.println("Service not found");
		return null;
	}

	public ArrayList<Service> getServices() {
		return services;
	}

	public void add(String type) {
		this.services.add(new Service(type));
	}

	public void remove(Service service) {
		service.delete();
	}
}
