package es.codeurjc;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {
	@PostMapping("#login")
	public ResponseEntity<Void> login(@RequestBody String userRequested) {
		//if (LoginService.authenticate(userRequested))
			//return ResponseEntity.ok().build();
		return ResponseEntity.ok().build();
	}
}
