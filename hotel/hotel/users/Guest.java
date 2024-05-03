package users;

import java.time.LocalDate;

public class Guest extends User{

	public Guest(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, boolean male) {
		super(username, password, name, surname, dateOfBirth, phoneNumber, male);
	}
	
	
}
