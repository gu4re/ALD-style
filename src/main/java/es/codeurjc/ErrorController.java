package es.codeurjc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
	@GetMapping("/error")
	public String getErrorPage(Model model) {
		return "redirect:/#404";
	}
}

