package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rikkei.academy.model.*;
import rikkei.academy.service.*;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/handleUpload")
@PropertySource("classpath:upload.properties")
public class HandleUploadAdminController {
	
	@Value("${path_hair}")
	public String pathUpload;
	@Value("${path_barber}")
	public String pathBarber;
	
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
	
	@PostMapping("/uploadHair")
	public String uploadHair(@RequestParam("column") String c, @RequestParam("image") MultipartFile m) {
		if (c == null || m.isEmpty()) {
			return "redirect:/admin/hair";
		}
		int column = Integer.parseInt(c);
		if (column <= 0 || column > 5) {
			return "redirect:/admin/hair";
		}
		
		File file = new File(pathUpload);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		String filename = m.getOriginalFilename();
		
		try {
			FileCopyUtils.copy(m.getBytes(), new File(pathUpload + filename));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		Hair hair = new Hair(0, filename, column, false);
		hairService.save(hair);
		return "redirect:/admin/hair";
	}
	
	@GetMapping("/deleteImg/{id}")
	public String deleteImg(@PathVariable("id") Long id) {
		hairService.delete(Integer.parseInt(String.valueOf(id)));
		return "redirect:/admin/hair";
	}
	
	@PostMapping("/uploadAddress")
	public String uploadAddress(@RequestParam("city") String city) {
		if(city == null) {
			return "redirect:/admin/address";
		}
		Address address = new Address(0, city, true);
		addressService.save(address);
		return "redirect:/admin/address";
	}
	
	@PostMapping("/uploadService")
	public String uploadService(@RequestParam("service") String service) {
		if (service == null) {
			return "redirect:/admin/service";
		}
		Type type = new Type(0, service, true);
		typeService.save(type);
		return "redirect:/admin/service";
	}
	
	@PostMapping("/uploadTime")
	public String uploadTime(@RequestParam("time") String time) {
		if (time == null) {
			return "redirect:/admin/time";
		}
		Times newTime = new Times(0, time, true);
		timeService.save(newTime);
		return "redirect:/admin/time";
	}
	
	@PostMapping("/uploadBarber")
	public String uploadBarber(@RequestParam("name") String name, @RequestParam("image") MultipartFile m) {
		if (name == null || m.isEmpty()) {
			return "redirect:/admin/barber";
		}
		
		File file = new File(pathBarber);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		String filename = m.getOriginalFilename();
		
		try {
			FileCopyUtils.copy(m.getBytes(), new File(pathBarber + filename));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		Barbers barber = new Barbers(0, name, filename, true);
		barberService.save(barber);
		return "redirect:/admin/barber";
	}
	
}
