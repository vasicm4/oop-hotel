package hotel;

import java.util.Objects;

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
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return deleted == service.deleted &&
               Objects.equals(type, service.type);
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
