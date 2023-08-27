package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.model.Orders;
import rikkei.academy.model.Type;
import rikkei.academy.model.UserNoAccount;
import rikkei.academy.model.Users;
import rikkei.academy.service.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
			model.addAttribute("phone", user.getPhone());
			LocalDate curr = LocalDate.now();
//		truyền đối tượng sang thymeleaf
			model.addAttribute("address", addressService.findAll());
			model.addAttribute("barbers", barberService.findAll());
			model.addAttribute("types", typeService.findAll());
			model.addAttribute("times", timeService.findAll());
			model.addAttribute("now", curr);
			model.addAttribute("error_order","Vui Lòng Nhập Đầy Đủ Thông Tin");
//			return "redirect:/order?phone=" + user.getPhone();
			return "user/order";
		}
		
		// format date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		//	time hiện tại
		LocalDate now = LocalDate.now();
		
		//	System.out.println(now.isAfter(localDate));
		// check date
		if (localDate.isBefore(now)) {
			model.addAttribute("phone", user.getPhone());
			LocalDate curr = LocalDate.now();
//		truyền đối tượng sang thymeleaf
			model.addAttribute("address", addressService.findAll());
			model.addAttribute("barbers", barberService.findAll());
			model.addAttribute("types", typeService.findAll());
			model.addAttribute("times", timeService.findAll());
			model.addAttribute("now", curr);
			model.addAttribute("error_order","Bạn Chọn Lịch Sai Rồi");
//			return "redirect:/order?phone=" + user.getPhone();
			return "user/order";
		}
		
		int barber = Integer.parseInt(String.valueOf(idBarber));
		int cityId = Integer.parseInt(String.valueOf(idCity));
		int time = Integer.parseInt(String.valueOf(idTime));
		int type = Integer.parseInt(String.valueOf(idService));
		String city = addressService.findById(cityId).getAddress();
		boolean check = checkTimeAndBarber(city, localDate,type,time, barber);
		
		if (!check) {
			model.addAttribute("phone", user.getPhone());
			LocalDate curr = LocalDate.now();
//		truyền đối tượng sang thymeleaf
			model.addAttribute("address", addressService.findAll());
			model.addAttribute("barbers", barberService.findAll());
			model.addAttribute("types", typeService.findAll());
			model.addAttribute("times", timeService.findAll());
			model.addAttribute("now", curr);
			model.addAttribute("error_order","Lịch Đã Bị Trùng Rồi");
//			return "redirect:/order?phone=" + user.getPhone();
			return "user/order";
		}
		
		Orders order = new Orders(0, user.getId(), barberService.findById(barber), typeService.findById(type), timeService.findById(time), String.valueOf(localDate), city, null, false);
		orderService.save(order);
		Users newUser = userService.findById(user.getId());
		session.setAttribute("data_user", newUser);
		return "redirect:/history";
	}
	
	@PostMapping("/handleOrderNoAccount")
	public String handleOrderNoAccount(@RequestParam(value = "city", defaultValue = "") Long idCity,
												  @RequestParam(value = "service", defaultValue = "") Long idService,
												  @RequestParam("calender") String date,
												  @RequestParam(value = "timeOrder", defaultValue = "") Long idTime,
												  @RequestParam(value = "yourBarber", defaultValue = "") Long idBarber,
												  @RequestParam("phone") String phone,
												  Model model,HttpSession session) {
		if (idBarber == null || idService == null || idCity == null || idTime == null) {
			model.addAttribute("phone", phone);
			LocalDate curr = LocalDate.now();
//		truyền đối tượng sang thymeleaf
			model.addAttribute("address", addressService.findAll());
			model.addAttribute("barbers", barberService.findAll());
			model.addAttribute("types", typeService.findAll());
			model.addAttribute("times", timeService.findAll());
			model.addAttribute("now", curr);
			model.addAttribute("error_order","Vui Lòng Nhập Đầy Đủ Thông Tin");
			return "user/order";
		}
		// format date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		//	time hiện tại
		LocalDate now = LocalDate.now();
		
		//	System.out.println(now.isAfter(localDate));
		// check date
		if (localDate.isBefore(now)) {
			model.addAttribute("phone", phone);
			LocalDate curr = LocalDate.now();
//		truyền đối tượng sang thymeleaf
			model.addAttribute("address", addressService.findAll());
			model.addAttribute("barbers", barberService.findAll());
			model.addAttribute("types", typeService.findAll());
			model.addAttribute("times", timeService.findAll());
			model.addAttribute("now", curr);
			model.addAttribute("error_order","Bạn Chọn Lịch Sai Rồi");
			return "user/order";
		}
		
		int barber = Integer.parseInt(String.valueOf(idBarber));
		int cityId = Integer.parseInt(String.valueOf(idCity));
		int time = Integer.parseInt(String.valueOf(idTime));
		int type = Integer.parseInt(String.valueOf(idService));
		String city = addressService.findById(cityId).getAddress();
		boolean check = checkTimeAndBarber(city,localDate,type, time, barber);
		
		if (!check) {
			model.addAttribute("phone", phone);
			LocalDate curr = LocalDate.now();
//		truyền đối tượng sang thymeleaf
			model.addAttribute("address", addressService.findAll());
			model.addAttribute("barbers", barberService.findAll());
			model.addAttribute("types", typeService.findAll());
			model.addAttribute("times", timeService.findAll());
			model.addAttribute("now", curr);
			model.addAttribute("error_order","Lịch Đã Bị Trùng Rồi");
			return "user/order";
		}
		
		UserNoAccount userNoAccount = new UserNoAccount(0, phone, barberService.findById(barber), typeService.findById(type), timeService.findById(time), String.valueOf(date), city, false);
		userNoAccountService.save(userNoAccount);
		model.addAttribute("success", "Đặt Lịch Thành Công");
		return "user/index";
	}
	
	@GetMapping("/handleDelete/{id}")
	public String handleDelete(@PathVariable("id") Long id, HttpSession session,Model model) {
		orderService.delete(Integer.parseInt(String.valueOf(id)));
		Users user = (Users) session.getAttribute("data_user");
		Users newUser = userService.findById(user.getId());
		model.addAttribute("delete_success","Hủy Kèo Thành Công");
		List<Orders> pending = new ArrayList<>();
		List<Orders> fulfilled = new ArrayList<>();
		List<Orders> list = userService.findById(user.getId()).getOrders();
		for (Orders o : list) {
			if (!o.isStatus()) {
				pending.add(o);
			} else {
				fulfilled.add(o);
			}
		}
		model.addAttribute("pending", pending);
		model.addAttribute("fulfilled", fulfilled);
		session.setAttribute("data_user", newUser);
		return "user/history";
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
			model.addAttribute("phone", user.getPhone());
			LocalDate curr = LocalDate.now();
//		truyền đối tượng sang thymeleaf
			model.addAttribute("address", addressService.findAll());
			model.addAttribute("barbers", barberService.findAll());
			model.addAttribute("types", typeService.findAll());
			model.addAttribute("times", timeService.findAll());
			model.addAttribute("now", curr);
			model.addAttribute("error_order","Vui Lòng Nhập Đầy Đủ Thông Tin");
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
			model.addAttribute("phone", user.getPhone());
			LocalDate curr = LocalDate.now();
//		truyền đối tượng sang thymeleaf
			model.addAttribute("address", addressService.findAll());
			model.addAttribute("barbers", barberService.findAll());
			model.addAttribute("types", typeService.findAll());
			model.addAttribute("times", timeService.findAll());
			model.addAttribute("now", curr);
			model.addAttribute("error_order","Bạn Chọn Lịch Sai Rồi");
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
		
		if (oldOrder.getBarber().getId() == newOrder.getBarber().getId() &&
				  oldOrder.getType().getId() == newOrder.getType().getId() &&
				  oldOrder.getTime().getId() == newOrder.getTime().getId() &&
				  oldOrder.getDate().equals(newOrder.getDate()) &&
				  oldOrder.getAddress().equals(newOrder.getAddress())) {
			orderService.save(newOrder);
			Users newUser = userService.findById(user.getId());
			session.setAttribute("data_user", newUser);
			return "redirect:/history";
		}
		
		boolean check = checkTimeAndBarber(city, localDate,type, time, barber);
		if (!check) {
			model.addAttribute("phone", user.getPhone());
			LocalDate curr = LocalDate.now();
//		truyền đối tượng sang thymeleaf
			model.addAttribute("address", addressService.findAll());
			model.addAttribute("barbers", barberService.findAll());
			model.addAttribute("types", typeService.findAll());
			model.addAttribute("times", timeService.findAll());
			model.addAttribute("now", curr);
			model.addAttribute("error_order","Lịch Đã Bị Trùng Rồi");
			return "redirect:/handleOrder/handleEdit/" + id;
		}
		
		Orders myOrder = new Orders(myId, user.getId(), barberService.findById(barber), typeService.findById(type), timeService.findById(time), String.valueOf(localDate), city, null, false);
		orderService.save(myOrder);
		Users newUser = userService.findById(user.getId());
		session.setAttribute("data_user", newUser);
		return "redirect:/history";
	}
	
	
	public boolean checkTimeAndBarber(String city, LocalDate localDate,int type, int idTime, int idBarber) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		boolean check = true;
		for (Orders o : orderService.findAll()) {
			if (o.getBarber().getId() == idBarber) {
				LocalDate myDate = LocalDate.parse(o.getDate(), formatter);
				if (myDate.isEqual(localDate)) {
					if(o.getType().getId() == type) {
						if (o.getTime().getId() == idTime) {
							if (o.getAddress().equals(city)) {
								if (!o.isStatus()) {
									check = false;
								}
							}
						}
					}
				}
			}
		}
		
		for (UserNoAccount u : userNoAccountService.findAll()) {
			if (u.getBarber().getId() == idBarber) {
				LocalDate myDate = LocalDate.parse(u.getDate(), formatter);
				if (myDate.isEqual(localDate)) {
					if(u.getType().getId() == type) {
						if (u.getTime().getId() == idTime) {
							if (u.getAddress().equals(city)) {
								if (!u.isStatus()) {
									check = false;
								}
							}
						}
					}
				}
			}
		}
		
		return check;
	}
	
	
}
