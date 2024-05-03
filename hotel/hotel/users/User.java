package users;

import java.time.LocalDate;

abstract class User {
	protected String _username;
	protected String _password;
	protected String _name;
	protected String _surname;
	protected LocalDate _dateOfBirth;
	protected int _phoneNumber;
	protected boolean _male;
	protected boolean _deleted = false;
	
	User(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber,boolean male) {	
		_username = username;
        _password = password;
        _name = name;
        _surname = surname;
        _dateOfBirth = dateOfBirth;
        _phoneNumber = phoneNumber;
        _male = male;
	}
	
	public boolean is_deleted() {
		return _deleted;
	}
	public void delete() {
		this._deleted = true;
	}
	public String get_username() {
		return _username;
	}
	public void set_username(String _username) {
		this._username = _username;
	}
	public String get_password() {
		return _password;
	}
	public void set_password(String _password) {
		this._password = _password;
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public String get_surname() {
		return _surname;
	}
	public void set_surname(String _surname) {
		this._surname = _surname;
	}
	public LocalDate get_dateOfBirth() {
		return _dateOfBirth;
	}
	public void set_dateOfBirth(LocalDate _dateOfBirth) {
		this._dateOfBirth = _dateOfBirth;
	}
	public int get_phoneNumber() {
		return _phoneNumber;
	}
	public void set_phoneNumber(int _phoneNumber) {
		this._phoneNumber = _phoneNumber;
	}
	public boolean is_male() {
		return _male;
	}
	public void set_male(boolean _male) {
		this._male = _male;
	}
	public String get_address() {
		return _address;
	}
	public void set_address(String _address) {
		this._address = _address;
	}
	protected String _address;
	
	

}
