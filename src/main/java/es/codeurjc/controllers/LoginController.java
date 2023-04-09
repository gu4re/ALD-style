package es.codeurjc.controllers;

import es.codeurjc.services.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

/**
 * Controls the mapping of auth page which contains all the requests related
 * to log in. It is a controller based on 'POST' method so 'GET'
 * is not allowed
 * @author gu4re
 * @version 1.7
 */
@Controller
@RequestMapping("/auth")
public class LoginController {
	/**
	 * Private constructor avoiding initialize of LoginController
	 */
	private LoginController(){}
	
	/**
	 * Treat the login requests that the controller receives from the frontend
	 * and sending a ResponseEntity back to the web page as a response
	 * @param jsonRequested The JSON Object as String sent from the frontend
	 * @return A ResponseEntity based of what happened in the Service
	 * returning <a style="color: #E89B6C; display: inline;">200 OK</a> if all goes good,
	 * <a style="color: #E89B6C; display: inline;">400 Bad Request</a> if not and
	 * otherwise <a style="color: #E89B6C; display: inline;">404 Not Found</a> if the resource is not able
	 */
	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody String jsonRequested) {
		try {
			JSONObject jsonObject = new JSONObject(jsonRequested);
			if (UserService.authenticate(
					jsonObject.getString("email"), jsonObject.getString("password")))
				return ResponseEntity.ok().build();
			return ResponseEntity.badRequest().build();
		}
		catch(JSONException e){
			Logger.getLogger("Error has occurred during parsing JSON.");
			return ResponseEntity.notFound().build();
		}
	}
}