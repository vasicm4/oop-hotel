package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import users.Admin;
import users.Education;
import users.Gender;

public class AdminManager {
	private static AdminManager instance;
	protected HashMap<String, Admin> admins;
	protected String fileName;
	protected String filePath;
	
	public static AdminManager getInstance() {
		if (instance == null) {
			instance = new AdminManager();
		}
		return instance;
	}
	
	
	public AdminManager() {
		admins = new HashMap<String, Admin>();
		fileName = "admins.csv";
		filePath = "data"+ System.getProperty("file.separator");
	}
	
	public Admin find(String username) {
			if (admins.containsKey(username)) {
				return admins.get(username);
			} else {
				return null;
			}
	}
	
	public HashMap<String, Admin> getAdmins() {
		return admins;
	}
	
	public HashMap<String, Admin> getAvailableAdmins() {
        HashMap<String, Admin> availableAdmins = new HashMap<String, Admin>();
        for (Admin admin : this.admins.values()) {
            if (!admin.isDeleted()) {
            	availableAdmins.put(admin.getUsername(), admin);
            }
        }
        return availableAdmins;
    }

	public void add(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, Gender gender, Education education, int experience, double baseSalary) {
		this.admins.put(username, new Admin(username, password, name, surname, dateOfBirth, phoneNumber, gender, education, experience, baseSalary));
	}
	
	public void add(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, Gender gender, Education education, int experience, double baseSalary, Boolean deleted) {
		this.admins.put(username, new Admin(username, password, name, surname, dateOfBirth, phoneNumber, gender, education, experience, baseSalary, deleted));
	}
	
	public void remove(Admin admin) {
		for (Admin a : admins.values()) {
			if (a.equals(admin)) {
				a.delete();
			}
		}
	}
	
	
	public double calculateAllSalaries() {
		double sum = 0;
		for (Admin admin : admins.values()) {
			if (!admin.isDeleted()) {
				sum += admin.calculateSalary();
			}
		}
		return sum;
	}
	
	public void readData() {
		try {
			String line;
			BufferedReader reader = new BufferedReader(new FileReader(filePath + fileName));
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				for (String string : data) {
					this.add(data[0], data[1], data[2], data[3], LocalDate.parse(data[4]), Integer.parseInt(data[5]), Gender.valueOf(data[6]), Education.valueOf(data[7]), Integer.parseInt(data[8]), Double.parseDouble(data[9]), Boolean.parseBoolean(data[10]));
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		catch (Exception e) {
			System.out.println("Error reading file" + fileName);
		}
	}
	
	public void writeData() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + fileName));
			for (Admin admin : admins.values()) {
				writer.write(admin.getUsername() + "," + admin.getPassword() + "," + admin.getName() + "," + admin.getSurname() + "," + admin.getDateOfBirth().toString() + "," + String.valueOf(admin.getPhoneNumber()) + "," + String.valueOf(admin.getGender()) + "," + String.valueOf(admin.getEducationLevel()) + "," + String.valueOf(admin.getExperience()) + "," + String.valueOf(admin.getBaseSalary()) + "," + String.valueOf(admin.isDeleted()) + "\n");
			}
			writer.flush();
		} catch (Exception e) {
			System.out.println("Error writing to file" + fileName);
		}
	}
	
}
