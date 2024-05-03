package users;

import java.time.LocalDate;

public class Employee extends User{
	private Education _educationLevel;
	private int _experience;
	private double _baseSalary;
	
	Employee(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, boolean male, Education education, int experience, double baseSalary) {
		super(username, password, name, surname, dateOfBirth, phoneNumber, male);
		_educationLevel = education;
		_experience = experience;
		_baseSalary = baseSalary;
	}

	public double calculateSalary() {
		return _baseSalary + _experience * 100 + _educationLevel.salaryBonus(_educationLevel) * 200;
	}
	
	public int get_educationLevel() {
		return _educationLevel.ordinal();
	}

	public void set_educationLevel(Education _educationLevel) {
		this._educationLevel = _educationLevel;
	}

	public int get_experience() {
		return _experience;
	}

	public void set_experience(int _experience) {
		this._experience = _experience;
	}

	public double get_baseSalary() {
		return _baseSalary;
	}

	public void set_baseSalary(int _baseSalary) {
		this._baseSalary = _baseSalary;
	}
}