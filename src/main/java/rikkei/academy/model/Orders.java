package rikkei.academy.model;

public class Orders {
	private int id;
	private int idUser;
	private Barbers barber;
	private Type type;
	private Times time;
	private String address;
	private Reviews reviews;
	private boolean status;
	
	public Orders() {
	}
	
	public Orders(int id, int idUser, Barbers barber, Type type, Times time, String address, Reviews reviews, boolean status) {
		this.id = id;
		this.idUser = idUser;
		this.barber = barber;
		this.type = type;
		this.time = time;
		this.address = address;
		this.reviews = reviews;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdUser() {
		return idUser;
	}
	
	public void setIdUser(int idUser) {
		this.idUser = idUser;
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
	
	public Reviews getReviews() {
		return reviews;
	}
	
	public void setReviews(Reviews reviews) {
		this.reviews = reviews;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
}
