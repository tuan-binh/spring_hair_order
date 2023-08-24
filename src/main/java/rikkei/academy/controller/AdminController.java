package rikkei.academy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rikkei.academy.dto.response.OrderHasAccountDTO;
import rikkei.academy.model.Orders;
import rikkei.academy.model.Users;
import rikkei.academy.service.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private BarberService barberService;
	@Autowired
	private TimeService timeService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private HairService hairService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserNoAccountService userNoAccountService;
	
	
	@GetMapping
	public String dashboard(HttpSession session) {
		Users user = (Users) session.getAttribute("data_admin");
		if (user == null) {
			return "redirect:/login";
		}
		session.setAttribute("active_sidebar", "home");
		return "admin/index";
	}
	
	@GetMapping("/user")
	public String userManager(HttpSession session, Model model) {
		session.setAttribute("active_sidebar", "user");
		
		List<Users> users = new ArrayList<>();
		for (Users u : userService.findAll()) {
			if (!u.getRole().stream().anyMatch(role -> role.getId() == 2)) {
				users.add(u);
			}
		}
		
		model.addAttribute("listUser", users);
		
		return "admin/userManager";
	}
	
	@GetMapping("/barber")
	public String barberManager(HttpSession session, Model model) {
		session.setAttribute("active_sidebar", "barber");
		
		model.addAttribute("listBarber", barberService.findAll());
		
		return "admin/barberManager";
	}
	
	@GetMapping("/time")
	public String timeManager(HttpSession session, Model model) {
		session.setAttribute("active_sidebar", "time");
		model.addAttribute("listTime", timeService.findAll());
		return "admin/timeManager";
	}
	
	@GetMapping("/service")
	public String serviceManager(HttpSession session, Model model) {
		session.setAttribute("active_sidebar", "service");
		
		model.addAttribute("listService", typeService.findAll());
		
		return "admin/serviceManager";
	}
	
	@GetMapping("/address")
	public String addressManager(HttpSession session, Model model) {
		session.setAttribute("active_sidebar", "address");
		
		model.addAttribute("listAddress", addressService.findAll());
		
		return "admin/addressManager";
	}
	
	@GetMapping("/order")
	public String orderManager(HttpSession session, Model model) {
		session.setAttribute("active_sidebar", "order");
		
		List<OrderHasAccountDTO> list = new ArrayList<>();
		for (Orders o : orderService.findAll()) {
			list.add(new OrderHasAccountDTO(o.getId(), userService.findById(o.getIdUser()).getFullName(), userService.findById(o.getIdUser()).getPhone(), o.getAddress(), o.getType().getTypeName(), "Ngày: " + o.getDate() + " Giờ: " + o.getTime().getTime(), o.isStatus()));
		}
		
		model.addAttribute("hasAccount", list);
		model.addAttribute("noAccount", userNoAccountService.findAll());
		
		return "admin/orderManager";
	}
	
	@GetMapping("/hair")
	public String hairManager(HttpSession session) {
		session.setAttribute("active_sidebar", "hair");
		return "admin/hairManager";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("data_admin");
		return "user/index";
	}
	
}
