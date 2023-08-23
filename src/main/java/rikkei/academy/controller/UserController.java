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
import rikkei.academy.model.Roles;
import rikkei.academy.model.Users;
import rikkei.academy.service.RoleService;
import rikkei.academy.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
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
			model.addAttribute("message", "true");
			return null;
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
	public String handleChangeName(@RequestParam("name") String newName, HttpSession session) {
		Users user = (Users) session.getAttribute("data_user");
		user.setFullName(newName);
		userService.updateFullName(user);
		session.setAttribute("data_user", user);
		session.setAttribute("your_name", "<i class='fa-solid fa-crown'></i> " + user.getFullName());
		return "user/information";
	}
	
	@PostMapping("/handleChangePassword")
	public String handleChangePassword(@RequestParam("pass") String newPassword, @RequestParam("confirmPass") String confirmPassword, Model model, HttpSession session) {
		if (!Validate.checkPassword(newPassword)) {
			model.addAttribute("message_error", "Mật Khẩu Phải Ít Nhất 6 Kí Tự");
			return "user/information";
		}
		if (!newPassword.equals(confirmPassword)) {
			model.addAttribute("message_error", "Mật Khẩu Không Trùng Nhau");
			return "user/ìnformation";
		}
		Users user = (Users) session.getAttribute("data_user");
		user.setPassword(newPassword);
		session.setAttribute("data_user", user);
		userService.updatePassword(user);
		return "user/information";
	}
	
	@PostMapping("/handleChangeAddress")
	public String handleChangeAddress() {
		return null;
	}
	
}
