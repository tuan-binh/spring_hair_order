package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
		model.addAttribute("message", "true");
		session.setAttribute("data_user", user);
		session.setAttribute("your_name", "<i class='fa-solid fa-crown'></i> " + user.getFullName());
		return "user/index";
		
	}
	
	@PostMapping("/handleRegister")
	public String handleRegister(@ModelAttribute("dataRegister") UserRegisterDTO userRegisterDTO) {
		return "user/login";
	}
	
}
