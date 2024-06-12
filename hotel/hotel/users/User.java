package users;

import java.time.LocalDate;
import java.util.Objects;

abstract class User {
	protected String username;
	protected String password;
	protected String name;
	protected String surname;
	protected LocalDate dateOfBirth;
	protected int phoneNumber;
	protected Gender gender;
	protected boolean deleted = false;
	
	User(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber,Gender gender) {	
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
	}

	User(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, Gender gender, boolean deleted) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.deleted = deleted;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return phoneNumber == user.phoneNumber &&
               deleted == user.deleted &&
               Objects.equals(username, user.username) &&
               Objects.equals(password, user.password) &&
               Objects.equals(name, user.name) &&
               Objects.equals(surname, user.surname) &&
               Objects.equals(dateOfBirth, user.dateOfBirth) &&
               gender == user.gender;
    }
	 
	public String toString() {
		return this.username + "," + this.password + "," + this.name + "," + this.surname + "," + this.dateOfBirth + "," + this.phoneNumber + "," + this.gender + "," + this.deleted;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public void delete() {
		this.deleted = true;
	}
	
	

}
