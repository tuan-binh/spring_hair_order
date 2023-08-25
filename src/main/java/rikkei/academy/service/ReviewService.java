package rikkei.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Reviews;
import rikkei.academy.reponsitory.impl.ReviewDAO;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewDAO reviewDAO;
	
	public void save(Reviews reviews) {
		reviewDAO.save(reviews);
	}
	
}
