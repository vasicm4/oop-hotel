package users;

import java.time.LocalDate;

public class Employee extends User{
	private Education educationLevel;
	private int experience;
	private double baseSalary;
	
	Employee(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, Gender gender, Education education, int experience, double baseSalary) {
		super(username, password, name, surname, dateOfBirth, phoneNumber, gender);
		this.educationLevel = education;
		this.experience = experience;
		this.baseSalary = baseSalary;
	}

	Employee(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, Gender gender, Education education, int experience, double baseSalary, boolean deleted) {
		super(username, password, name, surname, dateOfBirth, phoneNumber, gender, deleted);
		this.educationLevel = education;
		this.experience = experience;
		this.baseSalary = baseSalary;
	}
	
	@Override
	public String toString() {
		return super.toString() + "," + this.educationLevel + "," + this.experience + "," + this.baseSalary;
	}
	
	public double calculateSalary() {
		return baseSalary + experience * 100 + educationLevel.salaryBonus(educationLevel) * 200;
	}
	
	public Education getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(Education _educationLevel) {
		this.educationLevel = _educationLevel;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int _experience) {
		this.experience = _experience;
	}

	public double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(int _baseSalary) {
		this.baseSalary = _baseSalary;
	}
}