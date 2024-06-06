package users;

import java.time.LocalDate;

public class Janitor extends Employee{

	public Janitor(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, Gender gender, Education education, int experience, double baseSalary) {
		super(username, password, name, surname, dateOfBirth, phoneNumber, gender, education, experience, baseSalary);
	}
	
	public Janitor(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, Gender gender, Education education, int experience, double baseSalary, boolean deleted) {
		super(username, password, name, surname, dateOfBirth, phoneNumber, gender, education, experience, baseSalary, deleted);
	}
}
