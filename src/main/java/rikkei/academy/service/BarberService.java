package rikkei.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Barbers;
import rikkei.academy.reponsitory.impl.BarberDAO;

import java.util.List;

@Service
public class BarberService {
	
	@Autowired
	private BarberDAO barberDAO;
	
	public List<Barbers> findAll() {
		return barberDAO.findAll();
	}
	
	public void save(Barbers barber) {
		barberDAO.save(barber);
	}
	
	public Barbers findById(int id) {
		return barberDAO.findById(id);
	}
	
}
