package rikkei.academy.reponsitory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rikkei.academy.model.*;
import rikkei.academy.reponsitory.IBaseDAO;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDAO implements IBaseDAO<Orders, Integer> {
	
	@Autowired
	private BarberDAO barberDAO;
	@Autowired
	private TypeDAO typeDAO;
	@Autowired
	private TimeDAO timeDAO;
	@Autowired
	private DataSource dataSource;
	
	
	@Override
	public List<Orders> findAll() {
		Connection con = null;
		List<Orders> list = new ArrayList<>();
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FIND_ALL_ORDER}");
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id_order");
				int idUser = rs.getInt("id_user");
				Barbers barber = barberDAO.findById(rs.getInt("id_barber"));
				Type type = typeDAO.findById(rs.getInt("id_type"));
				Times time = timeDAO.findById(rs.getInt("id_time"));
				String date = rs.getString("date");
				String address = rs.getString("address");
				Reviews review = getReviewByIdOrder(rs.getInt("id_order"));
				boolean status = rs.getBoolean("status");
				list.add(new Orders(id, idUser, barber, type, time, date, address, review, status));
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
	
	public Reviews getReviewByIdOrder(int idOrder) {
		Reviews review = null;
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FIND_REVIEWS_BY_ID(?)}");
			callSt.setInt(1, idOrder);
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id_review");
				int idO = rs.getInt("id_order");
				String comment = rs.getString("comment");
				int rate = rs.getInt("rate");
				review = new Reviews(id,idO, comment, rate);
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
		return review;
	}
	
	@Override
	public void save(Orders orders) {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			if (findById(orders.getId()) == null) {
				CallableStatement callSt = con.prepareCall("{call INSERT_ORDER(?,?,?,?,?,?,?)}");
				callSt.setInt(1, orders.getIdUser());
				callSt.setInt(2, orders.getBarber().getId());
				callSt.setInt(3, orders.getType().getId());
				callSt.setInt(4, orders.getTime().getId());
				callSt.setString(5, orders.getDate());
				callSt.setString(6, orders.getAddress());
				callSt.setBoolean(7, orders.isStatus());
				callSt.executeUpdate();
			} else {
				CallableStatement callSt = con.prepareCall("{call UPDATE_ORDER(?,?,?,?,?,?,?,?)}");
				callSt.setInt(1, orders.getId());
				callSt.setInt(2, orders.getIdUser());
				callSt.setInt(3, orders.getBarber().getId());
				callSt.setInt(4, orders.getType().getId());
				callSt.setInt(5, orders.getTime().getId());
				callSt.setString(6, orders.getDate());
				callSt.setString(7, orders.getAddress());
				callSt.setBoolean(8, orders.isStatus());
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
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call DELETE_ORDER(?)}");
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
	public Orders findById(Integer id) {
		Connection con = null;
		Orders order = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FINDBYID_ORDER(?)}");
			callSt.setInt(1, id);
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				int idUser = rs.getInt("id_user");
				Barbers barber = barberDAO.findById(rs.getInt("id_barber"));
				Type type = typeDAO.findById(rs.getInt("id_type"));
				Times time = timeDAO.findById(rs.getInt("id_time"));
				String date = rs.getString("date");
				String address = rs.getString("address");
				Reviews review = getReviewByIdOrder(id);
				boolean status = rs.getBoolean("status");
				order = new Orders(id, idUser, barber, type, time, date, address, review, status);
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
		return order;
	}
}
