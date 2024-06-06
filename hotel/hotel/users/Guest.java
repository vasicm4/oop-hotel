package users;

import java.time.LocalDate;

public class Guest extends User{

	public Guest(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, Gender gender) {
		super(username, password, name, surname, dateOfBirth, phoneNumber,gender);
	}
	
	public Guest(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, Gender gender, boolean deleted) {
		super(username, password, name, surname, dateOfBirth, phoneNumber,gender, deleted);
	}
	
}
