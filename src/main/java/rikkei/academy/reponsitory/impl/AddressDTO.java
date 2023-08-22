package rikkei.academy.reponsitory.impl;

import org.springframework.stereotype.Component;
import rikkei.academy.model.Address;
import rikkei.academy.reponsitory.IBaseDAO;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AddressDTO implements IBaseDAO<Address, Integer> {
	
	private DataSource dataSource;
	private Connection con;
	
	@Override
	public List<Address> findAll() {
		List<Address> list = new ArrayList<>();
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FIND_ALL_ADDRESS}");
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id_address");
				String address = rs.getString("address_name");
				boolean status = rs.getBoolean("status");
				list.add(new Address(id, address, status));
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
	public void save(Address address) {
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			if (findById(address.getId()) == null) {
				CallableStatement callSt = con.prepareCall("{call INSERT_ADDRESS(?,?)}");
				callSt.setString(1, address.getAddress());
				callSt.setBoolean(2, true);
				callSt.executeUpdate();
			} else {
				CallableStatement callSt = con.prepareCall("{call UPDATE_ADDRESS(?,?,?)}");
				callSt.setInt(1, address.getId());
				callSt.setString(2, address.getAddress());
				callSt.setBoolean(3, address.isStatus());
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
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call DELETE_ADDRESS(?)}");
			callSt.setInt(1, id);
			callSt.executeUpdate();
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
	public Address findById(Integer id) {
		Address myAddress = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FINDBYID_ADDRESS(?)}");
			callSt.setInt(1, id);
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				String address = rs.getString("address_name");
				boolean status = rs.getBoolean("status");
				myAddress = new Address(id, address, status);
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
		return myAddress;
	}
}
