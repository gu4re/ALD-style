package es.codeurjc.controllers;

import es.codeurjc.services.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String getIndex(Model model) {
		AuthService.run();
		return "index.html";
	}
}