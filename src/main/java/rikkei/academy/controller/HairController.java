package rikkei.academy.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rikkei.academy.model.Hair;
import rikkei.academy.model.Users;
import rikkei.academy.service.HairService;
import rikkei.academy.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/hair")
public class HairController {
	private static final Gson GSON = new GsonBuilder().create();
	
	@Autowired
	private UserService userService;
	@Autowired
	private HairService hairService;
	
	@GetMapping("/like/{id}")
	public void	 addFavourite(HttpServletResponse response, @PathVariable Long id, HttpSession session) {
		Users user = (Users) session.getAttribute("data_user");
		Hair myHair = hairService.findById(Integer.parseInt(String.valueOf(id)));
		System.out.println(user.getFavourite());
		boolean check = true;
		for (Hair h : user.getFavourite()) {
			if (h.getId() == myHair.getId()) {
				check = false;
				break;
			}
		}
		
		if (check) {
			userService.addNewFavourite(user.getId(), myHair.getId());
			Users newUser = userService.findById(user.getId());
			session.setAttribute("data_user", newUser);
		} else {
			userService.deleteFavourite(user.getId(), myHair.getId());
			Users newUser = userService.findById(user.getId());
			session.setAttribute("data_user", newUser);
		}
		String data = GSON.toJson("thanh cong");
		response.setHeader("Content-Type","application/json");
		response.setStatus(200);
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(data);
			out.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}finally {
			out.close();
		}
	}
	
	@GetMapping("/delete/{id}")
	public String deleteFavourite(@PathVariable Long id, HttpSession session) {
		Users user = (Users) session.getAttribute("data_user");
		Hair myHair = hairService.findById(Integer.parseInt(String.valueOf(id)));
		userService.deleteFavourite(user.getId(), myHair.getId());
		Users newUser = userService.findById(user.getId());
		session.setAttribute("data_user", newUser);
		return "redirect:/favourite";
	}
	
	
	
}
