package rikkei.academy.model;

public class Barbers {
	private int id;
	private String barberName;
	private String urlAvatar;
	private boolean status;
	
	public Barbers() {
	}
	
	public Barbers(int id, String barberName, String urlAvatar, boolean status) {
		this.id = id;
		this.barberName = barberName;
		this.urlAvatar = urlAvatar;
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
	
	public String getUrlAvatar() {
		return urlAvatar;
	}
	
	public void setUrlAvatar(String urlAvatar) {
		this.urlAvatar = urlAvatar;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
}
