package rikkei.academy.dto.request;

public class UserRegisterDTO {
	
	private String fullName;
	private String phone;
	private String password;
	
	public UserRegisterDTO() {
	}
	
	public UserRegisterDTO(String fullName, String phone, String password) {
		this.fullName = fullName;
		this.phone = phone;
		this.password = password;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
