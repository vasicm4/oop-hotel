package hotel;

public class Service {
	protected String type;
	protected double price;
	protected boolean deleted;
	
	public Service(String type) {
		this.type = type;
		this.deleted = false;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void delete() {
		deleted = true;
	}
}
