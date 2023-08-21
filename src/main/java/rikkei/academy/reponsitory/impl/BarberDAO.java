package rikkei.academy.reponsitory.impl;

import org.springframework.stereotype.Component;
import rikkei.academy.model.Barbers;
import rikkei.academy.reponsitory.IBaseDAO;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BarberDAO implements IBaseDAO<Barbers, Integer> {
	
	private DataSource dataSource;
	private Connection con;
	
	@Override
	public List<Barbers> findAll() {
		List<Barbers> list = new ArrayList<>();
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FIND_ALL_BARBER}");
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id_barber");
				String barberName = rs.getString("barber_name");
				String url = rs.getString("url_avatar");
				boolean status = rs.getBoolean("status");
				list.add(new Barbers(id, barberName,url, status));
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
	public void save(Barbers barbers) {
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			if (findById(barbers.getId()) == null) {
				CallableStatement callSt = con.prepareCall("{call INSERT_BARBER(?,?,?)}");
				callSt.setString(1, barbers.getBarberName());
				callSt.setString(2, barbers.getUrlAvatar());
				callSt.setBoolean(3, true);
				callSt.executeUpdate();
			} else {
				CallableStatement callSt = con.prepareCall("{call UPDATE_BARBER(?,?,?,?)}");
				callSt.setInt(1, barbers.getId());
				callSt.setString(2, barbers.getBarberName());
				callSt.setString(3, barbers.getUrlAvatar());
				callSt.setBoolean(4, barbers.isStatus());
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
	public Barbers findById(Integer id) {
		Barbers barbers = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FINDBYID_BARBER(?)}");
			callSt.setInt(1, id);
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				String barberName = rs.getString("barber_name");
				String url = rs.getString("url_avatar");
				boolean status = rs.getBoolean("status");
				barbers = new Barbers(id, barberName, url, status);
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
		return barbers;
	}
}
