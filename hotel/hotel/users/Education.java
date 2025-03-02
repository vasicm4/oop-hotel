package users;

public enum Education {
	HIGH_SCHOOL, BACHELOR, MASTER, PHD;
	
	public int salaryBonus(Education education) {
		switch(education) {
	        case HIGH_SCHOOL:
	            return 1;
	        case BACHELOR:
	            return 2;
	        case MASTER:
	            return 3;
	        case PHD:
	            return 4;
	        default:
	        	return 0;
	    }
	}
	
	public int getEducationLevel(Education education) {
		return this.salaryBonus(education);
	}
	
	public static String getEducation(Education education) {
		switch (education) {
		case HIGH_SCHOOL:
			return "HIGH_SCHOOL";
		case BACHELOR:
			return "BACHELOR";
		case MASTER:
			return "MASTER";
		case PHD:
			return "PHD";
		default:
			return null;
		}
	}
	
	public static Education getEducation(String education) {
		switch (education) {
		case "HIGH_SCHOOL":
			return HIGH_SCHOOL;
		case "BACHELOR":
			return BACHELOR;
		case "MASTER":
			return MASTER;
		case "PHD":
			return PHD;
		default:
			return null;
		}
	}
	
}
