package es.codeurjc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controls the mapping of the error page to redirect everyone to the 404
 * error page when an invalid url is typed
 * @author gu4re
 * @version 1.1
 */
@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
	/**
	 * Private constructor avoiding initialize of ErrorController
	 */
	private ErrorController(){}
	
	/**
	 * Redirects all requests from /error to 404 Not Found page
	 * @return The redirection to 404 Not Found page
	 */
	@GetMapping("/error")
	public String getErrorPage() {
		return "redirect:/#404";
	}
}

