package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rikkei.academy.dto.request.UserLoginDTO;
import rikkei.academy.dto.request.UserRegisterDTO;
import rikkei.academy.model.Hair;
import rikkei.academy.model.Orders;
import rikkei.academy.model.Users;
import rikkei.academy.service.*;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private HairService hairService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private BarberService barberService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private TimeService timeService;
	
	@GetMapping
	public String user() {
		return "user/index";
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
	
	@GetMapping("/service")
	public String service() {
		return "user/service";
	}
	
	@GetMapping("/hair")
	public String hair(HttpSession session, Model model) {
		Users user = (Users) session.getAttribute("data_user");
		
		List<Hair> hairs = hairService.findAll();
		if (user != null) {
			for (Hair h : hairs) {
				for (Hair u : user.getFavourite()) {
					if (h.getId() == u.getId()) {
						h.setStatus(true);
					}
				}
			}
		}
		
		List<Hair> hair1 = new ArrayList<>();
		List<Hair> hair2 = new ArrayList<>();
		List<Hair> hair3 = new ArrayList<>();
		List<Hair> hair4 = new ArrayList<>();
		List<Hair> hair5 = new ArrayList<>();
		for (Hair h : hairs) {
			if (h.getRow() == 1) {
				hair1.add(h);
			}
			if (h.getRow() == 2) {
				hair2.add(h);
			}
			if (h.getRow() == 3) {
				hair3.add(h);
			}
			if (h.getRow() == 4) {
				hair4.add(h);
			}
			if (h.getRow() == 5) {
				hair5.add(h);
			}
		}
		model.addAttribute("hair1", hair1);
		model.addAttribute("hair2", hair2);
		model.addAttribute("hair3", hair3);
		model.addAttribute("hair4", hair4);
		model.addAttribute("hair5", hair5);
		return "user/hair";
	}
	
	@GetMapping("/location")
	public String location() {
		return "user/location";
	}
	
	@GetMapping("/order")
	public String order(@RequestParam("phone") String phone, Model model) {
		
		
		model.addAttribute("phone", phone);
		LocalDate curr = LocalDate.now();
//		truyền đối tượng sang thymeleaf
		model.addAttribute("address", addressService.findAll());
		model.addAttribute("barbers", barberService.findAll());
		model.addAttribute("types", typeService.findAll());
		model.addAttribute("times", timeService.findAll());
		model.addAttribute("now", curr);
		
		return "user/order";
	}
	
	@GetMapping("/information")
	public String information(Model model) {
		model.addAttribute("address", addressService.findAll());
		return "user/information";
	}
	
	@GetMapping("/favourite")
	public String favourite() {
		return "user/favourite";
	}
	
	@GetMapping("/history")
	public String history(HttpSession session, Model model) {
		Users user = (Users) session.getAttribute("data_user");
		List<Orders> pending = new ArrayList<>();
		List<Orders> fulfilled = new ArrayList<>();
		for (Orders o : user.getOrders()) {
			if (!o.isStatus()) {
				pending.add(o);
			} else {
				fulfilled.add(o);
			}
		}
		model.addAttribute("pending", pending);
		model.addAttribute("fulfilled", fulfilled);
		
		return "user/history";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("data_user");
		session.removeAttribute("your_name");
		return "redirect:/";
	}
}
