package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rikkei.academy.model.*;
import rikkei.academy.service.*;

@Controller
@RequestMapping("/handleAdmin")
public class HandleAdminController {
	
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
	private OrderService orderService;
	@Autowired
	private UserNoAccountService userNoAccountService;
	
	@GetMapping("/block/{id}")
	public String blockUser(@PathVariable("id") Long id) {
		Users user = userService.findById(Integer.parseInt(String.valueOf(id)));
		user.setStatus(false);
		userService.save(user);
		return "redirect:/admin/user";
	}
	
	@GetMapping("/unlock/{id}")
	public String unlockUser(@PathVariable("id") Long id) {
		Users user = userService.findById(Integer.parseInt(String.valueOf(id)));
		user.setStatus(true);
		userService.save(user);
		return "redirect:/admin/user";
	}
	
	
	@GetMapping("/layoffBarber/{id}")
	public String layoffBarber(@PathVariable("id") Long id) {
		Barbers barber = barberService.findById(Integer.parseInt(String.valueOf(id)));
		barber.setStatus(false);
		barberService.save(barber);
		return "redirect:/admin/barber";
	}
	
	@GetMapping("/inviteBarber/{id}")
	public String inviteBarber(@PathVariable("id") Long id) {
		Barbers barber = barberService.findById(Integer.parseInt(String.valueOf(id)));
		barber.setStatus(true);
		barberService.save(barber);
		return "redirect:/admin/barber";
	}
	
	@GetMapping("/blockTime/{id}")
	public String blockTime(@PathVariable("id") Long id) {
		Times time = timeService.findById(Integer.parseInt(String.valueOf(id)));
		time.setStatus(false);
		timeService.save(time);
		return "redirect:/admin/time";
	}
	
	@GetMapping("/unlockTime/{id}")
	public String unlockTime(@PathVariable("id") Long id) {
		Times time = timeService.findById(Integer.parseInt(String.valueOf(id)));
		time.setStatus(true);
		timeService.save(time);
		return "redirect:/admin/time";
	}
	
	@GetMapping("/blockService/{id}")
	public String blockService(@PathVariable("id") Long id) {
		Type type = typeService.findById(Integer.parseInt(String.valueOf(id)));
		type.setStatus(false);
		typeService.save(type);
		return "redirect:/admin/service";
	}
	
	@GetMapping("/unlockService/{id}")
	public String unlockService(@PathVariable("id") Long id) {
		Type type = typeService.findById(Integer.parseInt(String.valueOf(id)));
		type.setStatus(true);
		typeService.save(type);
		return "redirect:/admin/service";
	}
	
	@GetMapping("/deleteAddress/{id}")
	public String deleteAddress(@PathVariable("id") Long id) {
		addressService.delete(Integer.parseInt(String.valueOf(id)));
		return "redirect:/admin/address";
	}
	
	@GetMapping("/confirmHasAccount/{id}")
	public String confirmHasAccount(@PathVariable("id") Long id) {
		Orders order = orderService.findById(Integer.parseInt(String.valueOf(id)));
		order.setStatus(true);
		orderService.save(order);
		return "redirect:/admin/order";
	}
	
	@GetMapping("/confirmNoAccount/{id}")
	public String confirmNoAccount(@PathVariable("id") Long id) {
		UserNoAccount userNoAccount = userNoAccountService.findById(Integer.parseInt(String.valueOf(id)));
		userNoAccount.setStatus(true);
		userNoAccountService.save(userNoAccount);
		return "redirect:/admin/order";
	}
	
}

