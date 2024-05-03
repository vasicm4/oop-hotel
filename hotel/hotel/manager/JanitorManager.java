package manager;

import java.time.LocalDate;
import java.util.ArrayList;

import users.Education;
import users.Janitor;

public class JanitorManager {
	ArrayList<Janitor> janitors;
	String fileName;
	String filePath;
	
	public JanitorManager() {
		janitors = new ArrayList<Janitor>();
		fileName = "janitors.txt";
		filePath = "data/";
	}
	
	public Janitor find(String username) {
		for (Janitor janitor : janitors) {
			if (janitor.get_username().equals(username)) {
				return janitor;
			}
		}
		System.out.println("Janitor not found");
		return null;
	}
	
	public ArrayList<Janitor> getJanitors() {
		return janitors;
	}
	
	public void add(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, boolean male, Education education, int experience, double baseSalary) {
		this.janitors.add(new Janitor(username, password, name, surname, dateOfBirth, phoneNumber, male, education, experience, baseSalary));
	}
	
	public void remove(Janitor janitor) {
		janitor.delete();
	}
	
}
