package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import hotel.Service;

public class ServiceManager {
	protected static ServiceManager instance;
	protected HashMap<String, Service> services;
	protected String fileName;
	protected String filePath;

	public static ServiceManager getInstance() {
		if (instance == null) {
			instance = new ServiceManager();
		}
		return instance;
	}
	
	public ServiceManager() {
		services = new HashMap<String, Service>();
		fileName = "services.csv";
		filePath = "data"+ System.getProperty("file.separator");
	}

	public Service find(String type) {
		if (services.containsKey(type)) {
			return services.get(type);
		} else {
			return null;
		}
	}

	public HashMap<String, Service> getServices() {
		return services;
	}

	public void add(String type) {
		this.services.put(type,new Service(type));
	}
	
	public void add(String type, boolean deleted) {
		this.services.put(type,new Service(type, deleted));
	}

	public void remove(Service service) {
		service.delete();
	}
	
	public void readData() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath + fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
                this.add(data[0], Boolean.parseBoolean(data[1]));
			}
			
		}catch (Exception e) {
			System.out.println("Error reading file" + this.fileName);
		}
	}
	
	public void writeData() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + fileName));
			for (Service service: services.values()) {
				writer.write(service.getType() + "," + service.isDeleted() + "\n");
			}
		}catch(Exception e) {
			System.out.println("Writer");
		}

	}
}
