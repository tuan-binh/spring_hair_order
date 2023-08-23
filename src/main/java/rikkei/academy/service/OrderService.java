package rikkei.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.*;
import rikkei.academy.reponsitory.impl.OrderDAO;

import java.util.List;

@Service
public class OrderService {
	
	@Autowired
	private OrderDAO orderDAO;
	
	public List<Orders> findAll() {
		return orderDAO.findAll();
	}
	
	public void save(Orders orders) {
		orderDAO.save(orders);
	}
	
	public void delete(Integer id) {
		orderDAO.delete(id);
	}
	
	public Orders findById(Integer id) {
		return orderDAO.findById(id);
	}
	
}
