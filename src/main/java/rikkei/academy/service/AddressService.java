package rikkei.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Address;
import rikkei.academy.reponsitory.impl.AddressDAO;

import java.util.List;

@Service
public class AddressService {
	
	@Autowired
	private AddressDAO addressDAO;
	
	public List<Address> findAll() {
		return addressDAO.findAll();
	}
	
	public void save(Address address) {
		addressDAO.save(address);
	}
	
	public void delete(int id) {
		addressDAO.delete(id);
	}
	
	public Address findById(int id) {
		return addressDAO.findById(id);
	}
	
}
