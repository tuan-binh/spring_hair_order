package rikkei.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Hair;
import rikkei.academy.reponsitory.impl.HairDAO;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HairService {
	
	@Autowired
	private HairDAO hairDAO;
	
	public List<Hair> findAll() {
		return hairDAO.findAll();
	}
	
	
	public void save(Hair hair) {
		hairDAO.save(hair);
	}
	
	
	public void delete(Integer id) {
		hairDAO.delete(id);
	}
	
	
	public Hair findById(Integer id) {
		return hairDAO.findById(id);
	}
	
}
