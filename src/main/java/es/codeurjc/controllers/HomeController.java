package es.codeurjc.controllers;

import es.codeurjc.services.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controls the mapping of the home page
 * @author gu4re
 * @version 1.1
 */
@Controller
public class HomeController {
	/**
	 * Private constructor avoiding initialize of HomeController
	 */
	private HomeController(){}
	
	/**
	 * Returns the index or home menu in html form
	 * @return The home page in .html form
	 */
	@GetMapping("/")
	public String getIndex() {
		AuthService.run();
		return "index.html";
	}
}