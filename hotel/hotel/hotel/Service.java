package hotel;

public class Service {
	protected String type;
	protected boolean deleted;
	
	public Service(String type) {
		this.type = type;
		this.deleted = false;
	}
	
	public Service(String type, boolean deleted) {
		this.type = type;
		this.deleted = deleted;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String toString() {
		return type;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void delete() {
		deleted = true;
	}
}
