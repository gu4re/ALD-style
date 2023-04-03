package es.codeurjc.controllers.rest;

import es.codeurjc.exceptions.UserNotFoundException;
import es.codeurjc.services.AuthService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(jsonObject.getString("email"));
			helper.setFrom(new InternetAddress("guare4business@gmail.com", "Zapatillas ALD"));
			helper.setSubject("Reset Password");
			String pathToMailHTML = "src/main/resources/static/html/mailFormat.html";
			BufferedReader reader = new BufferedReader(new FileReader(pathToMailHTML));
	        StringBuilder mailStringBuilder = new StringBuilder();
	        String linea;
	        while ((linea = reader.readLine()) != null) {
	            mailStringBuilder.append(linea);
	        }
	        reader.close();
			helper.setText(mailStringBuilder.toString(), true);
			javaMailSender.send(message);
			return ResponseEntity.ok().build();
		}catch (JSONException e) {
			Logger.getLogger("Error has occurred during parsing JSON.");
			return ResponseEntity.notFound().build();
		} catch (UserNotFoundException e){
			Logger.getLogger("There's no user associated with that email");
			return ResponseEntity.notFound().build();
		} catch (MessagingException | IOException e){
			Logger.getLogger("Error has occurred during sending the mail.");
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
}
