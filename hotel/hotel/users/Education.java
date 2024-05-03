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
}
