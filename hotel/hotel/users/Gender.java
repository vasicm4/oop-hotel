package users;

public enum Gender {
	MALE, FEMALE;
	
	public static Gender getGender(String gender) {
		switch (gender) {
		case "MALE":
			return MALE;
		case "FEMALe":
			return FEMALE;
		default:
			return null;
		}
	}
	
}
