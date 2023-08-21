package rikkei.academy.model;

public class Address {
	private int id;
	private String address;
	private boolean status;
	
	public Address() {
	}
	
	public Address(int id, String address, boolean status) {
		this.id = id;
		this.address = address;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
