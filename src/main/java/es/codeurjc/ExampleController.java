package es.codeurjc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExampleController {
	@GetMapping("/")
	public String personal_data(Model model) {
		return "hello_world.html";
	}
}
