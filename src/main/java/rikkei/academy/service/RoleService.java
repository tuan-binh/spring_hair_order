package rikkei.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Roles;
import rikkei.academy.reponsitory.impl.RoleDAO;

@Service
public class RoleService {
	
	@Autowired
	private RoleDAO roleDAO;
	
	public Roles findById(int id) {
		return roleDAO.findById(id);
	}
	
}
