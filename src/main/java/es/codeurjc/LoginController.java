package es.codeurjc;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	@PostMapping("/api/login")
	public ResponseEntity<Void> login(@RequestParam String user, @RequestParam String password) {
		if (LoginService.authenticate(new User(user, password)))
			return ResponseEntity.ok().build();
		return ResponseEntity.badRequest().build();
	}
}
