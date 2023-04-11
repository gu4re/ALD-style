package es.codeurjc.controllers.rest;

import es.codeurjc.services.UserService;
import es.codeurjc.services.MailService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Controls the mapping of forgot page which contains all the requests related
 * to register and validate the user account. It is a controller based on 'POST' method so 'GET'
 * is not allowed
 * @author gu4re
 * @version 1.3
 */
@RestController
@RequestMapping("/auth")
public class RegisterRestController {
	/**
	 * Private field for javaMailSender that allow us to send an email
	 */
	@Autowired
	private JavaMailSender javaMailSender;
	
	/**
	 * Store the entry of the user that requested the register petition to use it in validation request,
	 * so it can be sent to the database. The password is saved in a char[] because it is a mutable object
	 * that can't leave data in memory after being deleted, in difference with String object
	 */
	private Map.Entry<String, String> userApplicant;
	
	/**
	 * Private constructor avoiding initialize of RegisterRestController
	 */
	private RegisterRestController(){}
	
	/**
	 * Treat the register requests that the controller receives from the frontend
	 * and sending a ResponseEntity back to the web page as a response
	 * @param jsonRequested The JSON Object as String sent from the frontend.
	 * <a style="color: #E89B6C; display: inline;">The parameter username inside jsonRequested
	 * is deprecated</a> it will be used in the future
	 * @return A ResponseEntity based of what happened in the Service
	 * returning <a style="color: #E89B6C; display: inline;">200 OK</a> if all goes good,
	 * <a style="color: #E89B6C; display: inline;">400 Bad Request</a> if not and
	 * otherwise <a style="color: #E89B6C; display: inline;">404 Not Found</a> if the resource is not able
	 */
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody String jsonRequested){
		try{
			JSONObject jsonObject = new JSONObject(jsonRequested);
			// Check if already exists or not and act in consequence
			if (UserService.userExists(jsonObject.getString("email")))
				return ResponseEntity.badRequest().build();
			userApplicant = Map.entry(jsonObject.getString("email"), jsonObject.getString("password"));
			return MailService.send("Validate Account", "guare4business@gmail.com",
					(this.userApplicant.getKey()),
					"src/main/resources/static/html/validateMailFormat.html", javaMailSender);
		} catch(JSONException e) {
			Logger.getLogger("Error has occurred during parsing JSON.");
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * Gets all the POST requests about validation of a user account adding it
	 * to the database
	 */
	@PostMapping("/validate")
	public void validate(){
		UserService.addUser(this.userApplicant.getKey(), this.userApplicant.getValue());
		// Security measures for temporal password saved in userApplicant attribute
		this.userApplicant = null;
	}
}
