package rikkei.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.UserNoAccount;
import rikkei.academy.reponsitory.impl.UserNoAccountDAO;

import java.util.List;

@Service
public class UserNoAccountService {
	
	@Autowired
	private UserNoAccountDAO userNoAccountDAO;
	
	public List<UserNoAccount> findAll() {
		return userNoAccountDAO.findAll();
	}
	
	public void save(UserNoAccount userNoAccount) {
		userNoAccountDAO.save(userNoAccount);
	}
	
	public void delete(int id) {
		userNoAccountDAO.delete(id);
	}
	
	public UserNoAccount findById(int id) {
		return userNoAccountDAO.findById(id);
	}
	
}
