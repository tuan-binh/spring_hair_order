package rikkei.academy.reponsitory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rikkei.academy.model.*;
import rikkei.academy.reponsitory.IBaseDAO;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
		try {
			CallableStatement callSt = con.prepareCall("{call FIND_ALL_USERNOACCOUNT}");
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String phone = rs.getString("phone");
				Barbers barber = barberDAO.findById(rs.getInt("id_barber"));
				Type type = typeDAO.findById(rs.getInt("id_type"));
				Times time = timeDAO.findById(rs.getInt("id_time"));
				String address = rs.getString("address");
				boolean status = rs.getBoolean("status");
				list.add(new UserNoAccount(id, phone, barber, type, time, address, status));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return list;
	}
	
	@Override
	public void save(UserNoAccount userNoAccount) {
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			if (findById(userNoAccount.getId()) == null) {
				CallableStatement callSt = con.prepareCall("{call INSERT_USERNOACCOUNT(?,?,?,?,?,?)}");
				callSt.setString(1, userNoAccount.getPhone());
				callSt.setInt(2, userNoAccount.getBarber().getId());
				callSt.setInt(3, userNoAccount.getType().getId());
				callSt.setInt(4, userNoAccount.getTime().getId());
				callSt.setString(5, userNoAccount.getAddress());
				callSt.setBoolean(6, userNoAccount.isStatus());
				callSt.executeUpdate();
			} else {
				CallableStatement callSt = con.prepareCall("{call UPDATE_USERNOACCOUNT(?,?,?,?,?,?,?)}");
				callSt.setInt(1, userNoAccount.getId());
				callSt.setString(2, userNoAccount.getPhone());
				callSt.setInt(3, userNoAccount.getBarber().getId());
				callSt.setInt(4, userNoAccount.getType().getId());
				callSt.setInt(5, userNoAccount.getTime().getId());
				callSt.setString(6, userNoAccount.getAddress());
				callSt.setBoolean(7, userNoAccount.isStatus());
				callSt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
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
	public UserNoAccount findById(Integer id) {
		UserNoAccount userNoAccount = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FINDBYID_USERNOACCOUNT(?)}");
			callSt.setInt(1, id);
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				String phone = rs.getString("phone");
				Barbers barber = barberDAO.findById(rs.getInt("id_barber"));
				Type type = typeDAO.findById(rs.getInt("id_type"));
				Times time = timeDAO.findById(rs.getInt("id_time"));
				String address = rs.getString("address");
				boolean status = rs.getBoolean("status");
				userNoAccount = new UserNoAccount(id, phone, barber, type, time, address, status);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
		
		return userNoAccount;
	}
}
