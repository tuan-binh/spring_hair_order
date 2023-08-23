package rikkei.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.dto.request.UserLoginDTO;
import rikkei.academy.dto.request.UserRegisterDTO;
import rikkei.academy.model.Users;
import rikkei.academy.reponsitory.impl.UserDAO;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	public List<Users> findAll() {
		return userDAO.findAll();
	}
	
	public void save(Users user) {
		userDAO.save(user);
	}
	
	public Users findById(int id) {
		return userDAO.findById(id);
	}
	
	public void addNewFavourite(int idUser, int idHair) {
		userDAO.addNewFavourite(idUser, idHair);
	}
	
	public void deleteFavourite(int idUser,int idHair) {
		userDAO.deleteFavourite(idUser,idHair);
	}
	
	public Users login(UserLoginDTO userLoginDTO) {
		Users user = new Users(userLoginDTO.getPhone(), userLoginDTO.getPassword());
		return userDAO.login(user);
	}
	
	public void register(UserRegisterDTO userRegisterDTO) {
		Users user = new Users(userRegisterDTO.getFullName(), userRegisterDTO.getPhone(), userRegisterDTO.getPassword());
		userDAO.register(user);
	}
	
	public boolean checkExistPhone(String phone) {
		for (Users u : userDAO.findAll()) {
			if (u.getPhone().equals(phone)) {
				return false;
			}
		}
		return true;
	}
	
}
