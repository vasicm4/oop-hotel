package users;

import java.time.LocalDate;

public class Janitor extends Employee{

	public Janitor(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber,boolean male, Education education, int experience, double baseSalary) {
		super(username, password, name, surname, dateOfBirth, phoneNumber, male, education, experience, baseSalary);
	}
	
}
