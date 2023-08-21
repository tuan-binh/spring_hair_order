package rikkei.academy.model;

public class Orders {
	private int id;
	private Users user;
	private Barbers barber;
	private Type type;
	private Times time;
	private String address;
	private boolean status;
	
	public Orders() {
	}
	
	public Orders(int id, Users user, Barbers barber, Type type, Times time, String address, boolean status) {
		this.id = id;
		this.user = user;
		this.barber = barber;
		this.type = type;
		this.time = time;
		this.address = address;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Users getUser() {
		return user;
	}
	
	public void setUser(Users user) {
		this.user = user;
	}
	
	public Barbers getBarber() {
		return barber;
	}
	
	public void setBarber(Barbers barber) {
		this.barber = barber;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Times getTime() {
		return time;
	}
	
	public void setTime(Times time) {
		this.time = time;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
}
