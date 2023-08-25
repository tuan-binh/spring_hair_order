package rikkei.academy.reponsitory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rikkei.academy.model.Reviews;
import rikkei.academy.reponsitory.IBaseDAO;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Component
public class ReviewDAO implements IBaseDAO<Reviews,Integer> {
	
	@Autowired
	private DataSource dataSource;
	
	
	@Override
	public List<Reviews> findAll() {
		return null;
	}
	
	@Override
	public void save(Reviews reviews) {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		try {
			CallableStatement callSt = con.prepareCall("{call INSERT_REVIEW(?,?,?)}");
			callSt.setString(1,reviews.getComment());
			callSt.setInt(2,reviews.getRate());
			callSt.setInt(3,reviews.getIdOrder());
			callSt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
		
	}
	
	@Override
	public void delete(Integer id) {
	
	}
	
	@Override
	public Reviews findById(Integer id) {
		return null;
	}
}
