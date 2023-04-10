package es.codeurjc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controls the mapping of the home page
 * @author gu4re
 * @version 1.2
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
		return "index.html";
	}
}