package rikkei.academy.reponsitory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rikkei.academy.model.Hair;
import rikkei.academy.model.Orders;
import rikkei.academy.model.Roles;
import rikkei.academy.model.Users;
import rikkei.academy.reponsitory.IBaseDAO;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserDAO implements IBaseDAO<Users, Integer> {
	
	@Autowired
	private HairDAO hairDAO;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private OrderDAO orderDAO;
	
	private DataSource dataSource;
	private Connection con;
	
	@Override
	public List<Users> findAll() {
		List<Users> list = new ArrayList<>();
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FIND_ALL_USER}");
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id_user");
				String fullName = rs.getString("full_name");
				String phone = rs.getString("phone");
				String password = rs.getString("password");
				String address = rs.getString("address");
				Set<Roles> roles = getRolesByIdUser(id);
				Set<Hair> hair = getHairByIdUser(id);
				List<Orders> orders = getOrderByIdUser(id);
				boolean status = rs.getBoolean("status");
				list.add(new Users(id, fullName, phone, password, address, roles, hair, orders, status));
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
	public void save(Users users) {
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			if (findById(users.getId()) == null) {
				CallableStatement callSt = con.prepareCall("{call INSERT_USER(?,?,?,?,?)}");
				callSt.setString(1, users.getFullName());
				callSt.setString(2, users.getPhone());
				callSt.setString(3, users.getPassword());
				callSt.setString(4, users.getAddress());
				callSt.setBoolean(5, users.isStatus());
				callSt.executeUpdate();
			} else {
				CallableStatement callSt = con.prepareCall("{call UPDATE_USER(?,?,?,?,?,?)}");
				callSt.setInt(1, users.getId());
				callSt.setString(2, users.getFullName());
				callSt.setString(3, users.getPhone());
				callSt.setString(4, users.getPassword());
				callSt.setString(5, users.getAddress());
				callSt.setBoolean(6, users.isStatus());
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
	public Users findById(Integer id) {
		Users user = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FINDBYID_USER(?)}");
			callSt.setInt(1, id);
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				String fullName = rs.getString("full_name");
				String phone = rs.getString("phone");
				String password = rs.getString("password");
				String address = rs.getString("address");
				Set<Roles> roles = getRolesByIdUser(id);
				Set<Hair> hair = getHairByIdUser(id);
				List<Orders> orders = getOrderByIdUser(id);
				boolean status = rs.getBoolean("status");
				user = new Users(id, fullName, phone, password, address, roles, hair, orders, status);
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
		
		return user;
	}
	
	public Set<Roles> getRolesByIdUser(int idUser) {
		Set<Roles> roles = new HashSet<>();
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FINDROLEDETAIL__BYIDUSER(?)}");
			callSt.setInt(1, idUser);
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				roles.add(roleDAO.findById(rs.getInt("id_role")));
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
		return roles;
	}
	
	public Set<Hair> getHairByIdUser(int idUser) {
		Set<Hair> hair = new HashSet<>();
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FINDFAVOURITE_BYIDUSER(?)}");
			callSt.setInt(1, idUser);
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				hair.add(hairDAO.findById(rs.getInt("id_hair")));
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
		return hair;
	}
	
	public List<Orders> getOrderByIdUser(int idUser) {
		List<Orders> orders = new ArrayList<>();
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FINDORDER_BYIDUSER(?)}");
			callSt.setInt(1, idUser);
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				orders.add(orderDAO.findById(rs.getInt("id_order")));
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
		return orders;
	}
	
	public void addNewFavourite(int idUser, int idHair) {
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call INSERT_FAVOURITE(?,?)}");
			callSt.setInt(1, idUser);
			callSt.setInt(2, idHair);
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
	
	public void deleteFavourite(int idDel) {
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call DELETE_FAVOURITE(?)}");
			callSt.setInt(1, idDel);
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
	
}
