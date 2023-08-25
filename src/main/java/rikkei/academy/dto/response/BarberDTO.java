package rikkei.academy.dto.response;

public class BarberDTO {
	private int id;
	private String barberName;
	private String urlBarber;
	private int quantity;
	private boolean status;
	
	public BarberDTO() {
	}
	
	public BarberDTO(int id, String barberName, String urlBarber, int quantity, boolean status) {
		this.id = id;
		this.barberName = barberName;
		this.urlBarber = urlBarber;
		this.quantity = quantity;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getBarberName() {
		return barberName;
	}
	
	public void setBarberName(String barberName) {
		this.barberName = barberName;
	}
	
	public String getUrlBarber() {
		return urlBarber;
	}
	
	public void setUrlBarber(String urlBarber) {
		this.urlBarber = urlBarber;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
}
