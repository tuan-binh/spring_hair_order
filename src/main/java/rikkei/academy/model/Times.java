package rikkei.academy.model;

public class Times {
	private int id;
	private String time;
	private boolean status;
	
	public Times() {
	}
	
	public Times(int id, String time, boolean status) {
		this.id = id;
		this.time = time;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
