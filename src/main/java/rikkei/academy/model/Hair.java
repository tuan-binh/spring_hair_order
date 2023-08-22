package rikkei.academy.model;

public class Hair {
	private int id;
	private String url;
	private int row;
	private boolean status;
	
	public Hair() {
	}
	
	public Hair(int id, String url, int row) {
		this.id = id;
		this.url = url;
		this.row = row;
	}
	
	public Hair(int id, String url, int row, boolean status) {
		this.id = id;
		this.url = url;
		this.row = row;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
}
