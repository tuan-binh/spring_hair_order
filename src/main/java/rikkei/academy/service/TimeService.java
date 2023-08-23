package rikkei.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Times;
import rikkei.academy.reponsitory.impl.TimeDAO;

import java.util.List;

@Service
public class TimeService {
	
	@Autowired
	private TimeDAO timeDAO;
	
	public List<Times> findAll() {
		return timeDAO.findAll();
	}
	
	public void save(Times time) {
		timeDAO.save(time);
	}
	
	public Times findById(int id) {
		return timeDAO.findById(id);
	}
	
}
