package rikkei.academy.reponsitory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rikkei.academy.model.Orders;
import rikkei.academy.model.UserNoAccount;
import rikkei.academy.reponsitory.IBaseDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserNoAccountDAO implements IBaseDAO<UserNoAccount, Integer> {
	
	@Autowired
	private BarberDAO barberDAO;
	@Autowired
	private TypeDAO typeDAO;
	@Autowired
	private TimeDAO timeDAO;
	@Autowired
	private DataSource dataSource;
	private Connection con;
	
	@Override
	public List<UserNoAccount> findAll() {
		List<UserNoAccount> list = new ArrayList<>();
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return list;
	}
	
	@Override
	public void save(UserNoAccount userNoAccount) {
	
	}
	
	@Override
	public void delete(Integer id) {
	
	}
	
	@Override
	public UserNoAccount findById(Integer id) {
		return null;
	}
}
