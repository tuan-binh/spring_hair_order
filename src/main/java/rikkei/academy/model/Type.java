package rikkei.academy.model;

public class Type {
	private int id;
	private String typeName;
	private boolean status;
	
	public Type() {
	}
	
	public Type(int id, String typeName, boolean status) {
		this.id = id;
		this.typeName = typeName;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
}
