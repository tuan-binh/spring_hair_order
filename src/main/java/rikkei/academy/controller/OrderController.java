package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.model.Orders;
import rikkei.academy.model.UserNoAccount;
import rikkei.academy.model.Users;
import rikkei.academy.service.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@Controller
@RequestMapping("/handleOrder")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserNoAccountService userNoAccountService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private BarberService barberService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private TimeService timeService;
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/handleOrderHasAccount")
	public String handleOrderHasAccount(@RequestParam(value = "city", defaultValue = "") Long idCity,
													@RequestParam(value = "service", defaultValue = "") Long idService,
													@RequestParam("calender") String date,
													@RequestParam(value = "timeOrder", defaultValue = "") Long idTime,
													@RequestParam(value = "yourBarber", defaultValue = "") Long idBarber,
													Model model, HttpSession session) {
		Users user = (Users) session.getAttribute("data_user");
		if (idBarber == null || idService == null || idCity == null || idTime == null) {
			model.addAttribute("error", "Vui Lòng Lựa Chọn Đầy Đủ");
			return "redirect:/order?phone=" + user.getPhone();
		}
		// format date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		//	time hiện tại
		LocalDate now = LocalDate.now();
		
		//	System.out.println(now.isAfter(localDate));
		// check date
		if (localDate.isBefore(now)) {
			return "redirect:/order?phone=" + user.getPhone();
		}
		
		int barber = Integer.parseInt(String.valueOf(idBarber));
		int cityId = Integer.parseInt(String.valueOf(idCity));
		int time = Integer.parseInt(String.valueOf(idTime));
		int type = Integer.parseInt(String.valueOf(idService));
		String city = addressService.findById(cityId).getAddress();
		boolean check = checkTimeAndBarber(city, time, barber);
		
		if (!check) {
			return "redirect:/order?phone=" + user.getPhone();
		}
		
		Orders order = new Orders(0, user.getId(), barberService.findById(barber), typeService.findById(type), timeService.findById(time), String.valueOf(localDate), city, null, false);
		orderService.save(order);
		user.getOrders().add(order);
		session.setAttribute("data_user", user);
		model.addAttribute("success", "Đặt Lịch Thành Công");
		return "redirect:/history";
	}
	
	@PostMapping("/handleOrderNoAccount")
	public String handleOrderNoAccount(@RequestParam(value = "city", defaultValue = "") Long idCity,
												  @RequestParam(value = "service", defaultValue = "") Long idService,
												  @RequestParam("calender") String date,
												  @RequestParam(value = "timeOrder", defaultValue = "") Long idTime,
												  @RequestParam(value = "yourBarber", defaultValue = "") Long idBarber,
												  @RequestParam("phone") String phone,
												  Model model) {
		if (idBarber == null || idService == null || idCity == null || idTime == null) {
			model.addAttribute("error", "Vui Lòng Lựa Chọn Đầy Đủ");
			return "redirect:/order?phone=" + phone;
		}
		// format date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		//	time hiện tại
		LocalDate now = LocalDate.now();
		
		//	System.out.println(now.isAfter(localDate));
		// check date
		if (localDate.isBefore(now)) {
			return "redirect:/order?phone=" + phone;
		}
		
		int barber = Integer.parseInt(String.valueOf(idBarber));
		int cityId = Integer.parseInt(String.valueOf(idCity));
		int time = Integer.parseInt(String.valueOf(idTime));
		int type = Integer.parseInt(String.valueOf(idService));
		String city = addressService.findById(cityId).getAddress();
		boolean check = checkTimeAndBarber(city, time, barber);
		
		if (!check) {
			return "redirect:/order?phone=" + phone;
		}
		
		UserNoAccount userNoAccount = new UserNoAccount(0, phone, barberService.findById(barber), typeService.findById(type), timeService.findById(time), String.valueOf(date), city, false);
		userNoAccountService.save(userNoAccount);
		model.addAttribute("success", "Đặt Lịch Thành Công");
		return "user/index";
	}
	
	@GetMapping("/handleDelete/{id}")
	public String handleDelete(@PathVariable("id") Long id, HttpSession session) {
		orderService.delete(Integer.parseInt(String.valueOf(id)));
		Users user = (Users) session.getAttribute("data_user");
		Users users = userService.findById(user.getId());
		session.setAttribute("data_user", users);
		return "redirect:/history";
	}
	
	@GetMapping("/handleEdit/{id}")
	public String handleGetInformationForEdit(@PathVariable("id") Long id, Model model, HttpSession session) {
		Orders order = orderService.findById(Integer.parseInt(String.valueOf(id)));
		Users user = (Users) session.getAttribute("data_user");
		
		model.addAttribute("phone", user.getPhone());
		
		model.addAttribute("address", addressService.findAll());
		model.addAttribute("barbers", barberService.findAll());
		model.addAttribute("types", typeService.findAll());
		model.addAttribute("times", timeService.findAll());
		model.addAttribute("now", order.getDate());
		
		model.addAttribute("data_edit", order);
		return "user/editOrder";
	}
	
	@PostMapping("/handleUpdateOrder")
	public String handleUpdateOrder(@RequestParam("id") Long id,
											  @RequestParam(value = "city", defaultValue = "") Long idCity,
											  @RequestParam(value = "service", defaultValue = "") Long idService,
											  @RequestParam("calender") String date,
											  @RequestParam(value = "timeOrder", defaultValue = "") Long idTime,
											  @RequestParam(value = "yourBarber", defaultValue = "") Long idBarber,
											  Model model, HttpSession session) {
		Users user = (Users) session.getAttribute("data_user");
		if (idBarber == null || idService == null || idCity == null || idTime == null) {
			model.addAttribute("error", "Vui Lòng Lựa Chọn Đầy Đủ");
			return "redirect:/handleOrder/handleEdit/" + id;
		}
		// format date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		//	time hiện tại
		LocalDate now = LocalDate.now();
		
		//	System.out.println(now.isAfter(localDate));
		// check date
		if (localDate.isBefore(now)) {
			return "redirect:/handleOrder/handleEdit/" + id;
		}
		int myId = Integer.parseInt(String.valueOf(id));
		int barber = Integer.parseInt(String.valueOf(idBarber));
		int cityId = Integer.parseInt(String.valueOf(idCity));
		int time = Integer.parseInt(String.valueOf(idTime));
		int type = Integer.parseInt(String.valueOf(idService));
		String city = addressService.findById(cityId).getAddress();
		
		Orders oldOrder = orderService.findById(myId);
		
		Orders newOrder = new Orders(myId, user.getId(), barberService.findById(barber), typeService.findById(type), timeService.findById(time), String.valueOf(localDate), city, null, false);

		if(oldOrder.getBarber().getId() == newOrder.getBarber().getId() && )
		
		return null;
	}
	
	public boolean checkTimeAndBarber(String city, int idTime, int idBarber) {
		boolean check = true;
		for (Orders o : orderService.findAll()) {
			if (o.getBarber().getId() == idBarber) {
				if (o.getTime().getId() == idTime) {
					if (o.getAddress().equals(city)) {
						check = false;
					}
				}
			}
		}
		
		for (UserNoAccount u : userNoAccountService.findAll()) {
			if (u.getBarber().getId() == idBarber) {
				if (u.getTime().getId() == idTime) {
					if (u.getAddress().equals(city)) {
						check = false;
					}
				}
			}
		}
		
		return check;
	}
	
	
}
