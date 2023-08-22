package rikkei.academy.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Users {
	private int id;
	private String fullName;
	private String phone;
	private String password;
	private String address;
	private Set<Roles> role;
	private Set<Hair> favourite;
	private List<Orders> orders;
	private boolean status;
	
	public Users() {
		role = new HashSet<>();
		favourite = new HashSet<>();
		orders = new ArrayList<>();
	}
	
	public Users(String phone, String password) {
		this.phone = phone;
		this.password = password;
	}
	
	public Users(String fullName, String phone, String password) {
		this.fullName = fullName;
		this.phone = phone;
		this.password = password;
	}
	
	public Users(int id, String fullName, String phone, String password, String address, Set<Roles> role, Set<Hair> favourite, List<Orders> orders, boolean status) {
		this.id = id;
		this.fullName = fullName;
		this.phone = phone;
		this.password = password;
		this.address = address;
		this.role = role;
		this.favourite = favourite;
		this.orders = orders;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Set<Roles> getRole() {
		return role;
	}
	
	public void setRole(Set<Roles> role) {
		this.role = role;
	}
	
	public Set<Hair> getFavourite() {
		return favourite;
	}
	
	public void setFavourite(Set<Hair> favourite) {
		this.favourite = favourite;
	}
	
	public List<Orders> getOrders() {
		return orders;
	}
	
	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
}
