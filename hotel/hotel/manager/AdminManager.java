package manager;

import java.time.LocalDate;
import java.util.ArrayList;

import users.Admin;
import users.Education;

public class AdminManager {
	protected ArrayList<Admin> admins;
	protected String fileName;
	protected String filePath;
	
	public AdminManager() {
		admins = new ArrayList<Admin>();
		fileName = "admins.txt";
		filePath = "data"+ System.getProperty("file.separator");
	}
	
	public Admin find(String username) {
		for (Admin admin : admins) {
			if (admin.get_username().equals(username)) {
				return admin;
			}
		}
		System.out.println("Admin not found");
		return null;
	}
	
	public ArrayList<Admin> getAdmins() {
		return admins;
	}
	
	public void add(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, boolean male, Education education, int experience, double baseSalary) {
		this.admins.add(new Admin(username, password, name, surname, dateOfBirth, phoneNumber, male, education, experience, baseSalary));
	}
	
	public void remove(Admin admin) {
		admin.delete();
	}
	
}
