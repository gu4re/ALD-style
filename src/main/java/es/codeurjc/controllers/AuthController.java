package es.codeurjc.controllers;

import es.codeurjc.services.AuthService;
import es.codeurjc.classes.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.logging.Logger;

@Controller
public class AuthController {
	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody String jsonRequested) {
		try {
			JSONObject jsonObject = new JSONObject(jsonRequested);
			String email = jsonObject.getString("email");
			String password = jsonObject.getString("password");
			User userRequested = new User(email, password);
			if (AuthService.authenticate(userRequested))
				return ResponseEntity.ok().build();
			return ResponseEntity.badRequest().build();
		}
		catch(JSONException e){
			Logger.getLogger("Error has occurred during parsing JSONArray.");
		}
		return ResponseEntity.notFound().build();
	}
}