package es.codeurjc.controllers.rest;

import es.codeurjc.exceptions.UserNotFoundException;
import es.codeurjc.services.UserService;
import es.codeurjc.services.MailService;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * Controls the mapping of forgot page which contains all the requests related
 * to forgot and recovery the password. It is a controller based on 'POST' method so 'GET'
 * is not allowed
 * @author gu4re
 * @version 1.3
 */
@RestController
@RequestMapping("/auth")
public class ForgotPasswordRestController {
	/**
	 * Private field for javaMailSender that allow us to send an email
	 */
	@Autowired
	private JavaMailSender javaMailSender;
	/**
	 * Store the mail of the user that requested the forgot petition to use it in restore petition
	 */
	private String mailApplicant;
	/**
	 * Private constructor avoiding initialize of AuthController
	 */
	private ForgotPasswordRestController(){}
	
	/**
	 * Controls when someone sends a petition of getting a password recover using javaMailSender. However, it only
	 * generates a petition when the email received as param is already registered in database
	 * @param jsonRequested The email where the petition is going to in JSON format
	 * @return A ResponseEntity based of what happened in the sending
	 * returning <a style="color: #E89B6C; display: inline;">200 OK</a> if all goes good,
	 * <a style="color: #E89B6C; display: inline;">400 Bad Request</a> if not and
	 * otherwise <a style="color: #E89B6C; display: inline;">404 Not Found</a> if the resource is not able
	 */
	@PostMapping("/forgot")
	public @NotNull ResponseEntity<Void> forgot(@RequestBody String jsonRequested){
		try{
			JSONObject jsonObject = new JSONObject(jsonRequested);
			this.mailApplicant = jsonObject.getString("email");
			if (!UserService.userExists(this.mailApplicant)){
				this.mailApplicant = "";
				throw new UserNotFoundException();
			}
			return MailService.send("Forgot Password", "guare4business@gmail.com",
					(this.mailApplicant),
					"src/main/resources/static/html/forgotMailFormat.html", javaMailSender);
		}catch (JSONException e) {
			Logger.getLogger("Error has occurred during parsing JSON.");
			return ResponseEntity.notFound().build();
		} catch (UserNotFoundException e) {
			Logger.getLogger("There's no user associated with that email");
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * Controls all mapping related to reset or change the password to a new one of the
	 * user that made a previous forgot petition
	 * @param jsonRequested The new password passed in JSON format
	 * @return A ResponseEntity based of what happened in the sending
	 * returning <a style="color: #E89B6C; display: inline;">200 OK</a> if all goes good,
	 * <a style="color: #E89B6C; display: inline;">400 Bad Request</a> if not and
	 * otherwise <a style="color: #E89B6C; display: inline;">404 Not Found</a> if any resource is not able
	 */
	@PostMapping("/reset")
	public @NotNull ResponseEntity<Void> reset(@RequestBody String jsonRequested){
		try{
			JSONObject jsonObject = new JSONObject(jsonRequested);
			return UserService.changePassword(this.mailApplicant, jsonObject.getString("newpassword"));
		} catch (JSONException e){
			Logger.getLogger("Error has occurred during parsing JSON.");
			return ResponseEntity.notFound().build();
		}
	}
}
