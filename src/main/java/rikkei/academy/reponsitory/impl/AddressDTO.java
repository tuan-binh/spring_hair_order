package rikkei.academy.reponsitory.impl;

import rikkei.academy.model.Address;
import rikkei.academy.reponsitory.IBaseDAO;

import java.util.List;

public class AddressDTO implements IBaseDAO<Address,Integer> {
	@Override
	public List<Address> findAll() {
		return null;
	}
	
	@Override
	public void save(Address address) {
	
	}
	
	@Override
	public void delete(Integer id) {
	
	}
	
	@Override
	public Address findById(Integer id) {
		return null;
	}
}
