package es.codeurjc.controllers.rest;

import es.codeurjc.exceptions.UserNotFoundException;
import es.codeurjc.services.AuthService;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
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
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
public class ForgotPasswordRestController {
	/**
	 * Private static field for javaMailSender that allow us to send an mail
	 */
	@Autowired
	private JavaMailSender javaMailSender;
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
			if (!AuthService.userExists(jsonObject.getString("email"))){
				throw new UserNotFoundException();
			}
			String resetPasswordLink = "https://localhost:8080/#resetPasswd";
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(jsonObject.getString("email"));
			message.setFrom("dpg_2002@hotmail.com");
			message.setSubject("Reset Password");
			message.setText("Click in the following link to reset your password: " + resetPasswordLink);
			javaMailSender.send(message);
			return ResponseEntity.ok().build();
		}catch (JSONException e) {
			Logger.getLogger("Error has occurred during parsing JSON.");
			return ResponseEntity.notFound().build();
		} catch (UserNotFoundException e){
			Logger.getLogger("There's no user associated with that email");
			return ResponseEntity.notFound().build();
		} catch (MailException e){
			Logger.getLogger("Error has occurred during sending the mail.");
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
}
