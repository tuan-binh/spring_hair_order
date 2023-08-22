package rikkei.academy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rikkei.academy.dto.request.UserLoginDTO;
import rikkei.academy.dto.request.UserRegisterDTO;
import rikkei.academy.model.Users;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class MainController {
	
	@GetMapping
	public String user(Model model) {
		model.addAttribute("dataLogin", new UserLoginDTO());
		return "user/login";
	}
	
	@GetMapping("/signUp")
	public String signUp(Model model) {
		model.addAttribute("dataRegister", new UserRegisterDTO());
		return "user/register";
	}
	
	@GetMapping("/signIn")
	public String signIn(Model model) {
		model.addAttribute("dataLogin", new UserLoginDTO());
		return "user/login";
	}
	
	@GetMapping("/index")
	public String index() {
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
	public String logout(HttpSession session) {
		session.removeAttribute("data_user");
		session.removeAttribute("your_name");
		return "redirect:/";
	}
}
