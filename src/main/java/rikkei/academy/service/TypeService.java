package rikkei.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Type;
import rikkei.academy.reponsitory.impl.TypeDAO;

import java.util.List;

@Service
public class TypeService {
	
	@Autowired
	private TypeDAO typeDAO;
	
	public List<Type> findAll() {
		return typeDAO.findAll();
	}
	
	public void save(Type type) {
		typeDAO.save(type);
	}
	
	public Type findById(int id) {
		return typeDAO.findById(id);
	}
	
}
