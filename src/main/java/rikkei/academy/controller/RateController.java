package rikkei.academy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.service.ReviewService;
import rikkei.academy.model.Reviews;


@Controller
@RequestMapping("/handleReviews")
public class RateController {
	
	@Autowired
	private ReviewService reviewService;
	
	@PostMapping("/review")
	public String handleReviews(@RequestParam("star") Long rate, @RequestParam("idOrder") Long idOrder) {
//		System.out.println(rate);
//		System.out.println(Integer.parseInt(String.valueOf(idOrder)));
		
		Reviews review = new Reviews(0, Integer.parseInt(String.valueOf(idOrder)), "", Integer.parseInt(String.valueOf(rate)));
		reviewService.save(review);
		return "redirect:/history";
	}
	
	
}
