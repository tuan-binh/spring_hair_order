package rikkei.academy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
	
	@GetMapping
	public String user() {
		return "user/index";
	}
	
	@GetMapping("/news")
	public String news() {
		return "user/news";
	}
	
	@GetMapping("/service")
	public String service() {
		return "user/service";
	}
	
	@GetMapping("/hair")
	public String hair() {
		return "user/hair";
	}
	
	@GetMapping("/location")
	public String location() {
		return "user/location";
	}
	
	@GetMapping("/order")
	public String order() {
		return "user/order";
	}
	
	@GetMapping("/information")
	public String information() {
		return "user/information";
	}
	
	@GetMapping("/favourite")
	public String favourite() {
		return "user/favourite";
	}
	
	@GetMapping("/history")
	public String history() {
		return "user/history";
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "redirect:/";
	}
}
