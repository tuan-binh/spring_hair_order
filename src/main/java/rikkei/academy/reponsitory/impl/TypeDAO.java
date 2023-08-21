package rikkei.academy.reponsitory.impl;

import org.springframework.stereotype.Component;
import rikkei.academy.model.Type;
import rikkei.academy.reponsitory.IBaseDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TypeDAO implements IBaseDAO<Type,Integer> {
	
	private DataSource dataSource;
	private Connection con;
	
	@Override
	public List<Type> findAll() {
		List<Type> list = new ArrayList<>();
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return list;
	}
	
	@Override
	public void save(Type type) {
	
	}
	
	@Override
	public void delete(Integer id) {
	
	}
	
	@Override
	public Type findById(Integer id) {
		return null;
	}
}

