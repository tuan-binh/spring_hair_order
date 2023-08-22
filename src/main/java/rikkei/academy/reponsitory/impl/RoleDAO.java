package rikkei.academy.reponsitory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rikkei.academy.model.Roles;
import rikkei.academy.reponsitory.IBaseDAO;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RoleDAO implements IBaseDAO<Roles, Integer> {
	
	@Autowired
	private DataSource dataSource;
	private Connection con;
	
	@Override
	public List<Roles> findAll() {
		List<Roles> list = new ArrayList<>();
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FIND_ALL_ROLE}");
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id_role");
				String roleName = rs.getString("role_name");
				list.add(new Roles(id, roleName));
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
	public void save(Roles roles) {
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		try {
			CallableStatement callSt = con.prepareCall("{call INSERT_ROLE(?)}");
			callSt.setString(1, roles.getRoleName());
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
	public void delete(Integer id) {
	
	}

//	public void changeStatusRole(Integer id) {
//		try {
//			con = dataSource.getConnection();
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//		try {
//			CallableStatement callSt = con.prepareCall("{call CHANGE_STATUS_ROLE(?)}");
//			callSt.setInt(1,id);
//			callSt.executeUpdate();
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		} finally {
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					throw new RuntimeException(e);
//				}
//			}
//		}
//	}
	
	@Override
	public Roles findById(Integer id) {
		Roles role = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FINDBYID_ROLE(?)}");
			callSt.setInt(1, id);
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				String roleName = rs.getString("role_name");
				role = new Roles(id, roleName);
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
		return role;
	}
}
