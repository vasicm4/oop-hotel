package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import users.Agent;
import users.Education;
import users.Gender;
import users.Janitor;

public class JanitorManager {
	private static JanitorManager instance;
	protected HashMap<String, Janitor> janitors;
	protected String fileName;
	protected String filePath;
	
	public static JanitorManager getInstance() {
		if (instance == null) {
			instance = new JanitorManager();
		}
		return instance;
	}
	
	public JanitorManager() {
		janitors = new HashMap<String, Janitor>();
		fileName = "janitors.csv";
		filePath = "data/";
	}
	
	public Janitor find(String username) {
		if (janitors.containsKey(username)) {
			return janitors.get(username);
		}
		return null;
	}
	
	public HashMap<String, Janitor> getJanitors() {
		return janitors;
	}
	
	public Double calculateAllSalaries() {
		double sum = 0;
		for (Janitor janitor : janitors.values()) {
			if (!janitor.isDeleted()) {
				sum += janitor.calculateSalary();
			}
		}
		return sum;	
	}
	
	public HashMap<String, Janitor> getAvailableJanitors() {
		HashMap<String, Janitor> janitors = new HashMap<String, Janitor>();
		for (Janitor janitor : this.janitors.values()) {
			if (janitor.isDeleted() == false) {
				janitors.put(janitor.getUsername(), janitor);
			}
		}
		return janitors;
	}
	
	public void add(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, Gender gender, Education education, int experience, double baseSalary) {
		this.janitors.put(username ,new Janitor(username, password, name, surname, dateOfBirth, phoneNumber, gender, education, experience, baseSalary));
	}
	
	public void add(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, Gender gender, Education education, int experience, double baseSalary, boolean deleted) {
		this.janitors.put(username ,new Janitor(username, password, name, surname, dateOfBirth, phoneNumber, gender, education, experience, baseSalary, deleted));
	}
	
	public void remove(Janitor janitor) {
		janitor.delete();
	}
	
	public void readData() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath + fileName));
			String line;
			while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
				this.add(data[0], data[1], data[2], data[3], LocalDate.parse(data[4]), Integer.parseInt(data[5]), Gender.valueOf(data[6]), Education.valueOf(data[7]), Integer.parseInt(data[8]), Double.parseDouble(data[9]), Boolean.parseBoolean(data[10]));
			}
		}catch (Exception e) {
			System.out.println("Error reading file" + fileName);
		}
	
	}
	
	public void writeData() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + fileName));
			for (Janitor janitor : janitors.values()) {
				writer.write(janitor.getUsername() + "," + janitor.getPassword() + "," + janitor.getName() + "," + janitor.getSurname() + "," + janitor.getDateOfBirth().toString() + "," + String.valueOf(janitor.getPhoneNumber()) + "," + String.valueOf(janitor.getGender()) + "," + String.valueOf(janitor.getEducationLevel()) + "," + String.valueOf(janitor.getExperience()) + "," + String.valueOf(janitor.getBaseSalary()) + "," + String.valueOf(janitor.isDeleted()) + "\n");
			}
			writer.flush();
		} catch (Exception e) {
			System.out.println("Error writing to file" + fileName);
		}
	}
	

	
	
}
