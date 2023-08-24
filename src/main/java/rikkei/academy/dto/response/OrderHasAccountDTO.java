package rikkei.academy.dto.response;

public class OrderHasAccountDTO {
	private int id;
	private String name;
	private String phone;
	private String address;
	private String service;
	private String time;
	private boolean status;
	
	public OrderHasAccountDTO() {
	}
	
	public OrderHasAccountDTO(int id, String name, String phone, String address, String service, String time, boolean status) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.service = service;
		this.time = time;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getService() {
		return service;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
}
