package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rikkei.academy.config.Validate;
import rikkei.academy.dto.request.UserLoginDTO;
import rikkei.academy.dto.request.UserRegisterDTO;
import rikkei.academy.model.Address;
import rikkei.academy.model.Roles;
import rikkei.academy.model.Users;
import rikkei.academy.service.AddressService;
import rikkei.academy.service.RoleService;
import rikkei.academy.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AddressService addressService;
	
	@PostMapping("/handleLogin")
	public String handleLogin(@ModelAttribute("dataLogin") UserLoginDTO userLoginDTO, HttpSession session, Model model) {
//		System.out.println(userLoginDTO.getPassword());
		if (userLoginDTO.getPhone().length() > 11) {
			model.addAttribute("phone", "Số Điện Thoại Định Dạng");
			return "user/login";
		}
		Users user = userService.login(userLoginDTO);
		System.out.println(user);
		if (user == null) {
			model.addAttribute("message", "false");
			return "user/login";
		}
		
		if (user.getRole().stream().anyMatch(role -> role.getId() == 2)) {
			// admin
			session.setAttribute("active_sidebar","home");
			session.setAttribute("data_admin",user);
			return "admin/index";
		}
		//	user
		if (!user.isStatus()) {
			model.addAttribute("block", "Tài Khoản Của Bạn Đã Bị Khóa");
			return "user/login";
		}
		
		model.addAttribute("message", "true");
		session.setAttribute("data_user", user);
		session.setAttribute("your_name", "<i class='fa-solid fa-crown'></i> " + user.getFullName());
		return "user/index";
		
	}
	
	@PostMapping("/handleRegister")
	public String handleRegister(@ModelAttribute("dataRegister") UserRegisterDTO userRegisterDTO, Model model) {
		if(userRegisterDTO.getPhone().length() != 11) {
			model.addAttribute("message_error","Số Điện Thoại Không Đúng Định Dạng");
			return "user/register";
		}
		if (!userService.checkExistPhone(userRegisterDTO.getPhone())) {
			model.addAttribute("message_error", "phone");
			return "user/register";
		}
		if (!Validate.checkPassword(userRegisterDTO.getPassword())) {
			model.addAttribute("message_error", "password2");
			return "user/register";
		}
		if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
			model.addAttribute("message_error", "password1");
			return "user/register";
		}
		
		userService.register(userRegisterDTO);
		return "redirect:/";
	}
	
	@PostMapping("/handleChangeName")
	public String handleChangeName(@RequestParam("name") String newName, HttpSession session, Model model) {
		if (newName.trim().isEmpty()) {
			model.addAttribute("error_name", "Tên Không Được Để Trống");
			return "redirect:/information";
		}
		Users user = (Users) session.getAttribute("data_user");
		user.setFullName(newName);
		userService.updateFullName(user);
		session.setAttribute("data_user", user);
		session.setAttribute("your_name", "<i class='fa-solid fa-crown'></i> " + user.getFullName());
		return "redirect:/information";
	}
	
	@PostMapping("/handleChangePassword")
	public String handleChangePassword(@RequestParam("pass") String newPassword, @RequestParam("confirmPass") String confirmPassword, Model model, HttpSession session) {
		if (newPassword.trim().isEmpty()) {
			model.addAttribute("error_pass", "Mật Khẩu Không Được Để Trống");
			return "redirect:/information";
		}
		if (!Validate.checkPassword(newPassword)) {
			model.addAttribute("message_error", "Mật Khẩu Phải Ít Nhất 6 Kí Tự");
			return "redirect:/information";
		}
		if (!newPassword.equals(confirmPassword)) {
			model.addAttribute("message_error", "Mật Khẩu Không Trùng Nhau");
			return "redirect:/information";
		}
		Users user = (Users) session.getAttribute("data_user");
		user.setPassword(newPassword);
		session.setAttribute("data_user", user);
		userService.updatePassword(user);
		return "redirect:/information";
	}
	
	@PostMapping("/handleChangeAddress")
	public String handleChangeAddress(@RequestParam(value = "city", defaultValue = "") Long id, HttpSession session, Model model) {
		if (id == null) {
			model.addAttribute("error_empty", "Vui Lòng Lựa Chọn");
			return "redirect:/information";
		}
		Address address = addressService.findById(Integer.parseInt(String.valueOf(id)));
		Users user = (Users) session.getAttribute("data_user");
		
		user.setAddress(address.getAddress());
		session.setAttribute("data_user", user);
		userService.updateAddress(user);
		return "redirect:/information";
	}
	
}
